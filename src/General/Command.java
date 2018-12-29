package General;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Command 
{
	Robot bot;
	int mouseX = 0,
		mouseY = 0;
	
	double 	screen[] = {0,0},
			ratio[] = {1,1}; //monitor ratio for when screen resolution and cursor location don't match
	
	Rectangle screenRect;
	
	public Command() throws AWTException {
		bot = new Robot();
		//check screenRange
		getMouseLoc();
		bot.mouseMove(10000,10000);
		bot.delay(10);
		screen[0] = MouseInfo.getPointerInfo().getLocation().getX();
		screen[1] = MouseInfo.getPointerInfo().getLocation().getY();
		setRes(1000,1000);
		System.out.println("Screen: ("+screen[0]+" "+screen[1]+") space: "+screen[0]/ratio[0]+" , "+screen[1]/ratio[1]);
		mouseTo(mouseX, mouseY);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		screenRect = new Rectangle(screenSize);
	}
	
	public double[] getScreenSpaceResolution () { return screen; }
	public double[] getScreenSpaceRatio () { return ratio; }
	
	private int[] getMouseLoc() {
		mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		int[] res = {mouseX, mouseY};
		//System.out.println("mouse: "+res[0]+" "+res[1]);
		return res;
	}
	
	public int[] getMouseLocConverted() {
		mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		int[] res = { (int) (mouseX/ratio[0]), (int) (mouseY/ratio[1])};
		System.out.println("mouse: "+res[0]+" "+res[1]);
		return res;
	}
	
	private double[] getMouseLocDouble() {
		getMouseLoc(); //to update mouseX and mouseY
		double[] res = {MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY()};
		return res;
	}
	
	public String setRes (int x, int y) { ratio[0]= screen[0]/x; ratio[1]=screen[1]/y; return ratio[0]+" "+ratio[1]; }
	
	//public Color getColor (int x, int y) { return bot.getPixelColor(x, y); }
	
	public Color getColor (int x, int y) {
		Rectangle bounds = new Rectangle(x, y, 1, 1);
		BufferedImage img = bot.createScreenCapture(bounds);
		int clr = img.getRGB(0,0);
		return new Color(clr);
	}//*/

//Click tools
	
	public String mouseClick () { return mouseClick(MouseEvent.BUTTON1_DOWN_MASK); }
	
	//One mouse button click
	public String mouseClick (int input) {
		bot.mousePress(input);
		bot.delay(10);
		bot.delay(15+ (int)(50*Math.random()));
		bot.mouseRelease(input);
		bot.delay(10);
		bot.delay(15+ (int)(100*Math.random()));
		
		return "mouse clicked";
	}
	
	//Consecutive mouse button clicks
	public String mouseClick (int input, int reps) {
		for(int i=0; i < reps; i++) { mouseClick(input); }
		return "mouse clicked "+reps+" times";
	}

//Type Tools
	
	//Clicks a special action key that cannot be sent as a character.
	public String type (int specKey) {
		bot.keyPress(specKey);
		bot.delay(100+ (int)(100*Math.random()) );
		bot.keyRelease(specKey);
		bot.delay(10);
		return "Hit key: "+specKey;
	}
	
	public String type (int specKey, boolean shiftElseCtrl) {
		if (shiftElseCtrl) {
			bot.keyPress(KeyEvent.SHIFT_DOWN_MASK);
			bot.keyPress(specKey);
			bot.keyRelease(specKey);
			bot.keyRelease(KeyEvent.SHIFT_DOWN_MASK);
			bot.delay(10);
		} else {
			bot.keyPress(KeyEvent.CTRL_DOWN_MASK);
			bot.keyPress(specKey);
			bot.keyRelease(specKey);
			bot.keyRelease(KeyEvent.CTRL_DOWN_MASK);
			bot.delay(10);
		}
		return "Hit key: "+specKey+" and shift="+shiftElseCtrl;
	}
	
	//Clicks a special action key that cannot be sent as a character with a key release delay.
	public String type (int specKey, int delay) {
		bot.keyPress(specKey);
		bot.delay(10);
		bot.delay(delay);
		bot.keyRelease(specKey);
		bot.delay(10);
		bot.delay(10);
		return "Hit key: "+specKey;
	}

	//types a character with an assigned button release delay
	public String type (char letter, double delay) {
		boolean capital = Character.isUpperCase(letter);
		if (capital) bot.keyPress(KeyEvent.VK_SHIFT);
		else letter = Character.toUpperCase(letter);
		bot.keyPress(letter);
		bot.delay(10);
		bot.delay((int) delay);
		bot.keyRelease(letter);
		bot.delay(10);
		bot.delay((int) delay);
		if (capital) bot.keyRelease(KeyEvent.VK_SHIFT);
		return "Typed: "+letter;
	}
	
	//repeats a typed key with a random interval
	public String type (char letter, int reps) { for(int i=0; i < reps; i++) type(letter, 20.0+(Math.random()*10)); return "Typed: "+letter; }
	
	//types a sequence of characters with a random interval
	public String type (char[] letter)
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
	
//Mouse Tools
	
	//Converts mouse from client-side coordinates to screen-space coordinates, sends to teleport mouse method.
	public String mouseTo (int x, int y) {
		return mousePort((int) (x*ratio[0]), (int) (y*ratio[1]));
	}
	
	//Teleports mouse to screen-space coordinates.
	private String mousePort (int x, int y) {
		getMouseLoc();
		for (int i=0; i < 10 && mouseX != x; i++) {
			bot.mouseMove(x, y);
			getMouseLoc();
		}
		return "mouse teleported to x="+x+" y="+y;
	}
	
	public String mouseAct (int x, int y) {
		mouseMove(x,y);
		mouseClick(InputEvent.BUTTON1_DOWN_MASK);
		return "Mouse acted to and at "+x+" "+y;
	}
	
	//moves mouse like a human using initial speed and deceleration.
	public String mouseMove (int x, int y) {
		double 	initial_loc[] = getMouseLocDouble(),
				target_loc[] = {	x*ratio[0], y*ratio[1] },
				diff[] = {			target_loc[0] - initial_loc[0], target_loc[1] - initial_loc[1] };
		
		int max = 0,
			interval = 40;
		
		while ( Math.abs(diff[0]) > 1 && Math.abs(diff[1]) > 1) {
			if( Math.abs(diff[0]) <= 2) mouseX = (int) target_loc[0];
			else mouseX = mouseX + (int) ((Math.abs(diff[0]) > interval)? diff[0]/interval : Math.signum(diff[0]));
				
			if( Math.abs(diff[1]) <= 2) mouseY = (int) target_loc[1];
			else mouseY = mouseY + (int) ((Math.abs(diff[1]) > interval)? diff[1]/interval : Math.signum(diff[1]));
			
				diff[0] = target_loc[0] - mouseX;
				diff[1] = target_loc[1] - mouseY;
				
			bot.mouseMove(mouseX, mouseY);
			bot.delay(10);
			bot.delay(1000/120);
			max++;
			if(max > 1000 || diff[0] > screen[0] || diff[1] > screen[1])
				break;
		}
		
		if(mouseX != target_loc[0] && mouseY != target_loc[1]) {
			mouseMoveTowards(x,y);
		}
		
		return "mouse moved from ("+initial_loc[0]+","+initial_loc[1]+") to ("+mouseX+","+mouseY+") ";
	}
	
	private boolean mouseMoveTowards(int x, int y) {
		int 	target_loc[] = {(int) (x*ratio[0]), (int) (y*ratio[1]) };
		int 	diff[] 		 = {(int) (Math.signum(target_loc[0]-mouseX)),
								(int) (Math.signum(target_loc[1]-mouseY)) };
		
		for (int i=0; i < 40 && (mouseX != target_loc[0] || mouseY != target_loc[1]); i++) {
			bot.mouseMove(mouseX+ ( (mouseX==target_loc[0])? 0:diff[0]), mouseY+ ( (mouseY==target_loc[1])? 0:diff[1]));
			getMouseLoc();
			bot.delay(10);
			bot.delay(1000/120);
		}//*/
		return mouseX != x && mouseY != y;
	}
	
	public String paste (String message) {
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
	
//Window/app Tools
	
	//full command chain that allows the pc to open an app acting like a human
	public boolean openApp (String appName) {
		int coords[] = {0,1000};
		Color colScan = getColor(coords[0], coords[1]);
		mouseMove(0,1000);
		type(KeyEvent.VK_WINDOWS);
		wait(1000);
		for (int i=0; i < 3 && colScan == getColor(coords[0], coords[1]); i++)
			wait(1000);
		if(colScan != getColor(coords[0], coords[1])) {
			paste(appName);
			wait(1000);
			type(KeyEvent.VK_ENTER);
			return true;
		}
		return false;
	}
	
	public boolean openWebApp (String appName) {
		if (!openApp("Google Chrome"))	return false;
		wait(2000);
		if (!colCompRangeCheck (320, 300, 720, 700, new Color(241, 243, 244).getRGB(), 2)) {
			wait(2000);
			if (!colCompRangeCheck (320, 300, 720, 700, new Color(241, 243, 244).getRGB(), 2))
				return false;
		}
		
		int loc[] = colCompareRange (320, 300, 720, 700, new Color(241, 243, 244).getRGB(), 2);
		mouseAct(loc[0], loc[1]+3);
		paste(appName);
		wait(500);
		type(KeyEvent.VK_ENTER);
		wait(2000);
		type(KeyEvent.VK_TAB);
		bot.delay(10);
		type(KeyEvent.VK_ENTER);
		wait(500);
		type(KeyEvent.VK_ENTER);
		wait(500);
		type(KeyEvent.VK_ENTER);
		return true;
	}
	
	public boolean closeWindow() {
		Color colScan = getColor(500, 500);
		mouseAct(930,2);
		return colorCompare(colScan,getColor(500, 500));
	}
	
//Scan Tools
	
	public String pixelScan (int milliseconds, int reps) {
		Color colScan = getColor(0, 0);
		int[] cursor = new int[2];
		StringBuilder s = new StringBuilder("");
		for(int i=0; i < reps; i++) {
			wait(milliseconds);
			cursor = getMouseLoc();
			mouseTo(0,0);
			colScan = getColor(cursor[0], cursor[1]);
			wait(100);
			System.out.println( "The Color = "+colRecComp(3,3,cursor[0], cursor[1]) );
			mousePort(cursor[0], cursor[1]);
			s.append("col=").append(colScan).append(" mX=").append(cursor[0]/ratio[0])
				.append(" mY=").append(cursor[1]/ratio[1]).append("\n");
		}
		return s.toString();
	}
	
	public String pixelScan (int milliseconds, int x, int y) {
		int mX = (int) (x*ratio[0]);
		int mY = (int) (y*ratio[1]);
		Color colScan = getColor(mX, mY);
		wait(milliseconds);
		colScan = getColor(mX, mY);
		wait(100);
		System.out.println( "The Color = "+colRecComp(3,3,x,y) );
		mousePort(mX, mY);
		return "col="+colScan+" mX="+x+" mY="+y;
	}
	//BufferedImage image = bot.createScreenCapture(screenRect);
//Buffered Screen Image Tools
	
	public int colRecComp (int height, int width, int x, int y)
	{
		BufferedImage image = bot.createScreenCapture(screenRect);
		
		x = (int) (x*ratio[0]);
		y = (int) (y*ratio[1]);
		
		int clr = image.getRGB( x, y);
		int  red   = (clr & 0x00ff0000) >> 16;
		int  green = (clr & 0x0000ff00) >> 8;
		int  blue  =  clr & 0x000000ff;
		
		System.out.println("Red Color value = "+ red);
		System.out.println("Green Color value = "+ green);
		System.out.println("Blue Color value = "+ blue);
		
		return image.getRGB(0, 0);
	}
	
	public int[] colCompareRange (int x0, int y0, int x, int y, int rgb, int rgbMargin) {
		
		int xmin = (int) (((x0<x)? x0 : x)*ratio[0]);
		int ymin = (int) (((y0<y)? y0 : y)*ratio[1]);
		
		BufferedImage image = screenCaptureRange(x0, y0, x, y);
		int abs[] = {x-x0, y-y0};
		double diagonalRate = (abs[0]/abs[1]);
		int maxSteps = (int) (abs[1]) -1;
		int start[] = 	{ (abs[0]<0)? Math.abs(abs[0])-1:0, (abs[1]<0)? Math.abs(abs[1])-1:0 };
		int cur[] = new int[2];
		int[] location = new int[2];
		//System.out.println("abs="+abs[0]+", "+abs[1]+" dr="+diagonalRate+"\nmaxSteps="+maxSteps+"\nStart="+start[0]+", "+start[1]);
		for(int i=1; i < maxSteps; i++)
		{
			cur[0] = Math.abs((int) (start[0]+i*diagonalRate));
			cur[1] = Math.abs((int) (start[1]+i));
			
			//location[0] = (int) xmin+cur[0];
			//location[1] = (int) ymin+cur[1];
			//bot.mouseMove(location[0],location[1]);
			//bot.mouseMove(location[0],location[1]);
			//wait(100);
			//mouseTo(500,500);
			
			if (colorCompareMargin(image.getRGB(cur[0], cur[1]), rgb, rgbMargin))
			{
				
				location[0] = (int) xmin+cur[0];
				location[1] = (int) ymin+cur[1];
				//bot.mouseMove(location[0],location[1]);
				location[0] = (int) (location[0]/ratio[0]);
				location[1] = (int) (location[1]/ratio[1]);
				//mouseMove(location[0],location[1]);
				//mouseMove(location[0],location[1]);
				break;
			}
			//System.out.println("i="+i+" locX="+location[0]+" locY="+location[1]);
			//wait(100);
		}
		return location;
	}
		
	public boolean colCompRangeCheck (int x0, int y0, int x, int y, int rgb, int rgbMargin) {
			int[] match = colCompareRange(x0, y0, x, y, rgb, rgbMargin);
		return !(match[0]==0 && match[1]==0);
	}
	
	public BufferedImage screenCapture (int x, int y) {
		BufferedImage image = bot.createScreenCapture(screenRect);
		return image;
	}
	
	public BufferedImage screenCaptureRange (int x0, int y0, int x, int y) {
		int xmin = (int) (((x0<x)? x0 : x)*ratio[0]);
		int xmax = (int) (((x0>x)? x0 : x)*ratio[0]);
		int ymin = (int) (((y0<y)? y0 : y)*ratio[1]);
		int ymax = (int) (((y0>y)? y0 : y)*ratio[1]);
		//System.out.println("xmin=" + xmin+" ymin="+ymin);
		Rectangle bounds = new Rectangle( xmin, ymin, xmax-xmin, ymax-ymin);
		BufferedImage image = bot.createScreenCapture(bounds);
		return image;
	}
	
	public BufferedImage subScreenCapture (int x, int y, int size) {
		x = (int) (x*ratio[0]);
		y = (int) (y*ratio[1]);
		Rectangle bounds = new Rectangle(x, y, size, size);
		BufferedImage image = bot.createScreenCapture(bounds);
		return image;
	}
	
//Color Tools
	
	public boolean[] rgbCompare(Color inputCol, int redMax, int redMin,
								int greenMax, int greenMin, int blueMax, int blueMin)
	{
		boolean[] res = { (inputCol.getRed()  < redMax 		|| inputCol.getRed()  > redMin ),
						(  inputCol.getGreen()< greenMax 	|| inputCol.getGreen()> greenMin ),
						(  inputCol.getBlue() < blueMax 	|| inputCol.getBlue() > blueMin )};
		return res;
	}
	
	public boolean[] rgbCompare(Color inputCol, int red, int green, int blue, int diff) {
	return rgbCompare(inputCol, red+diff, red-diff, green+diff, green-diff, blue+diff, blue-diff);
	}
	
	public boolean colorCompareMargin(int inputRGB, int compareRGB, int diff)
	{
		int  r[]  = { (inputRGB & 0x00ff0000) >> 16	, (compareRGB & 0x00ff0000) >> 16	};
		int  g[]  = { (inputRGB & 0x0000ff00) >> 8	, (compareRGB & 0x0000ff00) >> 8	};
		int  b[]  = {  inputRGB & 0x000000ff		,  compareRGB & 0x000000ff			};
		
		//System.out.println("Margin "+diff);
		//System.out.println("comparing r "+r[0]+" vs "+r[1]);
		//System.out.println("comparing g "+g[0]+" vs "+g[1]);
		//System.out.println("comparing b "+b[0]+" vs "+b[1]);
		
		return ( Math.abs(r[0]-r[1]) < diff && Math.abs(g[0]-g[1]) < diff && Math.abs(b[0]-b[1]) < diff );
	}
	
	public boolean colorCompare(Color inputCol, int red, int green, int blue, int diff) {
		return (Math.abs(inputCol.getRed()-red) <diff && Math.abs(inputCol.getGreen()-green) <diff &&
				Math.abs(inputCol.getBlue()-blue) <diff );
	}
	
	public boolean colorCompare(Color inputCol, int redMax, int redMin,
			int greenMax, int greenMin, int blueMax, int blueMin)
	{
		boolean[] colors = rgbCompare(inputCol, redMax, redMin, greenMax, greenMin, blueMax, blueMin);
		return colors[0] && colors[1] && colors[2];
	}
	
	public boolean colorCompare(Color inputCol, Color compareCol) {
	return inputCol.equals(compareCol) ;
	}
}
