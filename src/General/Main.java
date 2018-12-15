package General;

import java.awt.AWTException;
import java.awt.event.InputEvent;

public class Main
{
	public static void main (String[] args)
	{
		taubot();
	}
	
	public static void taubot()
	{
		Command c = null;
		try { c = new Command(); }
		catch (AWTException e) { e.printStackTrace(); }
		c.setRes(100, 100);
		
		gwBot(c);
	}
	
	public static void gwBot(Command c) {
		log( c.mouseMove(15,90) );
		log( c.mouseClick(InputEvent.BUTTON2_DOWN_MASK) );
		log( c.mouseClick(InputEvent.BUTTON2_DOWN_MASK) );
		log( c.mouseClick(InputEvent.BUTTON2_DOWN_MASK) );
		log( c.mouseClick('V'));
	}
	
	public static void log(String message) { System.out.println(message); }
	public static void log(String message, boolean bcase) { if (bcase) System.out.println(message); }
	public static void log(int[] val, boolean bcase) { if (bcase) for (int v : val) System.out.print(" ("+v+")"); }
}
