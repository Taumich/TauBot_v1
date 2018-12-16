package General;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
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
	
	//One mouse button click
	public String mouseClick(int input) {
		bot.mousePress(input);
		bot.waitForIdle();
		bot.delay(10);
		bot.mouseRelease(input);
		bot.waitForIdle();
		bot.delay(10);
		
		return "mouse clicked";
	}
	
	//Consecutive mouse button clicks
	public String mouseClick(int input, int reps) {
		for(int i=0; i < reps; i++) { mouseClick(input); }
		return "mouse clicked "+reps+" times";
	}
	
	//Clicks a special action key that cannot be sent as a character.
	public String type(int specKey) {
		bot.keyPress(specKey);
		bot.waitForIdle();
		bot.keyRelease(specKey);
		bot.waitForIdle();
		return "Hit key: "+specKey;
	}
	
	//Clicks a special action key that cannot be sent as a character with a key release delay.
	public String type(int specKey, int delay) {
		bot.keyPress(specKey);
		bot.waitForIdle();
		bot.delay(delay);
		bot.keyRelease(specKey);
		bot.waitForIdle();
		bot.delay(delay);
		return "Hit key: "+specKey;
	}

	//types a character with an assigned button release delay
	public String type(char letter, double delay) {
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
	
	//repeats a typed key with a random interval
	public String type(char letter, int reps) { for(int i=0; i < reps; i++) type(letter, 20.0+(Math.random()*10)); return "Typed: "+letter; }
	
	//types a sequence of characters with a random interval
	public String type(char[] letter)
	{
		StringBuilder res = new StringBuilder("");
		for( char i : letter) {
			res.append(type(i, 20.0+(Math.random()*10)));
		}
		return "Typed: "+res.toString();
	}
	
	//converts string to characters and sends character list to type(character sequence)
	public String type(String text)
	{
		return type(text.toCharArray());
	}
	
	//Converts mouse from client-side coordinates to screen-space coordinates, sends to teleport mouse method.
	public String mouseTo(int x, int y) { return mousePort((int) (x*ratio[0]), (int) (y*ratio[1])); }
	
	//Teleports mouse to screen-space coordinates.
	private String mousePort(int x, int y) {
		mouseX = x;
		mouseY = y;
		bot.mouseMove(mouseX, mouseY);
		return "mouse teleported to x="+x+" y="+y;
	}
	
	//moves mouse like a human using initial speed and deceleration.
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
	
	public boolean openApp(String appName) {
		Color colScan = getColor(5, 90);
		System.out.println("color before: "+colScan);
		type(KeyEvent.VK_WINDOWS);
		wait(2000);
		for (int i=0; i < 3 && colScan == getColor(5, 90); i++)
			wait(1000);
		System.out.println("color after: "+getColor(5, 90));
		if(colScan == getColor(5, 90)) {
			paste(appName);
			type(KeyEvent.VK_ENTER);
			return true;
		}
		return false;
	}
	
	public String pixelScan(int milliseconds, int reps) {
		Color colScan = getColor(5, 90);
		int[] cursor = new int[2];
		StringBuilder s = new StringBuilder("");
		for(int i=0; i < reps; i++) {
		wait(milliseconds);
		cursor[0] = (int) MouseInfo.getPointerInfo().getLocation().getX();
		cursor[1] = (int) MouseInfo.getPointerInfo().getLocation().getY();
		mouseTo(50,50);
		wait(500);
		mouseTo(cursor[0], cursor[1]);
		s.append("col=").append(colScan).append(" mX=").append(cursor[0]).append(" mY=").append(cursor[1]).append("\n");
		
		}
		return s.toString();
	}
}
