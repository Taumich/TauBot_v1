package General;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Command 
{
	Robot bot;
	int mouseX = 100,
		mouseY = 100;
	
	double 	screen[] = {0,0},
			ratio[] = {1,1}; //monitor ratio for when screen resolution and cursor location don't match
	
	public Command () throws AWTException {
		bot = new Robot();
		//check screenRange
		bot.mouseMove(10000,10000);
		bot.waitForIdle();
		screen[0] = MouseInfo.getPointerInfo().getLocation().getX();
		screen[1] = MouseInfo.getPointerInfo().getLocation().getY();
		System.out.println("Screen: "+screen[0]+" "+screen[1]);
		bot.mouseMove(mouseX, mouseY);
	}
	
	public String setRes(int x, int y) { ratio[0]= screen[0]/x; ratio[1]=screen[1]/y; return ratio[0]+" "+ratio[1]; }
	
	public Color getColor(int x, int y) { return bot.getPixelColor(x, y); }
	
	public String mouseClick(int input) {
		bot.mousePress(input);
		bot.waitForIdle();
		bot.delay(10);
		bot.mouseRelease(input);
		bot.waitForIdle();
		bot.delay(10);
		
		return "mouse clicked";
	}
	
	public String mouseClick(char input) {
		bot.mousePress(input);
		bot.waitForIdle();
		bot.delay(10);
		bot.mouseRelease(input);
		bot.waitForIdle();
		bot.delay(10);
		
		return "mouse clicked";
	}
	
	public String mouseClick(int input, int reps) {
		for(int i=0; i < reps; i++) { mouseClick(input); }
		return "mouse clicked "+reps+" times";
	}
	
	public String type(int letter, double delay) {
		boolean capital = Character.isUpperCase(letter);
		if (capital) bot.keyPress(KeyEvent.VK_SHIFT);
		else letter = Character.toUpperCase(letter);
		bot.keyPress(letter);
		bot.waitForIdle();
		bot.delay((int) delay);
		bot.keyRelease(letter);
		bot.waitForIdle();
		bot.delay((int) delay);
		if (capital) bot.keyRelease(KeyEvent.VK_SHIFT);
		return "Typed: "+letter;
	}
	
	public String type(int letter) { return type(letter, 10.0); };
	public String type(char letter) { type(letter); return "Typed: "+letter; }
	public String type(int letter, int reps) { for(int i=0; i < reps; i++) type(letter); return "Typed: "+letter; }
	
	public String type(char[] letter)
	{
		StringBuilder res = new StringBuilder("");
		for( char i : letter) {
			res.append(type(i));
		}
		return "Typed: "+res.toString();
	}
	
	public String type(String text)
	{
		return type(text.toCharArray());
	}
	
	public String mouseMove(int x, int y) {
		double 	initial_loc[] = { 	MouseInfo.getPointerInfo().getLocation().getX(),
									MouseInfo.getPointerInfo().getLocation().getY() },
				target_loc[] = {	x*ratio[0], y*ratio[1] },
				diff[] = {			target_loc[0] - initial_loc[0], target_loc[1] - initial_loc[1] };
		
		int max = 0,
			interval = 40;
		
		while ( Math.abs(diff[0]) > 1 && Math.abs(diff[1]) > 1) {
			if( Math.abs(diff[0]) <= 3) mouseX = (int) target_loc[0];
			else mouseX = mouseX + (int) ((Math.abs(diff[0]) > interval)? diff[0]/interval : Math.signum(diff[0]));
				
			if( Math.abs(diff[1]) <= 3) mouseY = (int) target_loc[1];
			else mouseY = mouseY + (int) ((Math.abs(diff[1]) > interval)? diff[1]/interval : Math.signum(diff[1]));
			
				diff[0] = target_loc[0] - mouseX;
				diff[1] = target_loc[1] - mouseY;
				
			bot.mouseMove(mouseX, mouseY);
			bot.waitForIdle();
			bot.delay(1000/120);
			max++;
			if(max > 1000 || diff[0] > screen[0] || diff[1] > screen[1])
				break;
		}
		
		return "mouse moved from ("+initial_loc[0]+","+initial_loc[1]+") to ("+mouseX+","+mouseY+") ";
	}
	
	public String paste(String message) {
		StringSelection stringSelection = new StringSelection(message);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_V);
		bot.keyRelease(KeyEvent.VK_V);
		bot.keyRelease(KeyEvent.VK_CONTROL);
		
		return ("mouse types '"+message+"'");
	}
	
	public String wait(int milliseconds) {
		bot.delay(milliseconds);
		return ("Waited for "+milliseconds+" ms");
	}
}
