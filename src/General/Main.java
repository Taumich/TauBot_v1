package General;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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
		
		log( "gwBot completed = "+gwBot(c) );
	}
	
	public static boolean gwBot(Command c) {
		Color colScan = c.getColor(48, 50);
		boolean openSuccess = c.openApp("Guild Wars 2");
		if(!openSuccess)
			return false;
		colScan = c.getColor(48, 50);
		for (int i=0; (i < 5) && colScan.equals(c.getColor(48, 50)); i++) log( c.wait(1000) + colScan.equals(c.getColor(48, 50)));
		colScan = c.getColor(48, 50);
		for (int i=0; (i < 5) && colScan.equals(c.getColor(48, 50)); i++) log( c.wait(1000) + colScan.equals(c.getColor(48, 50)));
		log( c.wait(1000) );
		log( c.mouseMove(50,50) );
		log( c.mouseMove(64,75) );
		log( c.wait(2000) );
		log( c.mouseClick(InputEvent.BUTTON1_DOWN_MASK) );
		log( c.type(KeyEvent.VK_ENTER));
		log( c.wait(2000) );
		
		log( c.mouseMove(32,62) );
		log( c.wait(5000) );
		log( c.mouseClick(InputEvent.BUTTON1_DOWN_MASK) );
		log( c.wait(1000) );
		log( c.type(KeyEvent.VK_ENTER));
		
		colScan = c.getColor(25, 25);
		log( c.mouseMove(10,10) );
		for (int i=0; (i < 20) && colScan.equals(c.getColor(25, 25)); i++) log( c.wait(1000) + colScan.equals(c.getColor(25, 25)));
		log( c.wait(4000) );
		
		colScan = c.getColor(25, 25);
		for (int i=0; (i < 20) && colScan.equals(c.getColor(25, 25)); i++) log( c.wait(1000) + colScan.equals(c.getColor(25, 25)));
		log( c.wait(4000) );
		
		log( c.mouseMove(10,80) );
		log( c.mouseMove(15,92) );
		log( c.mouseClick(InputEvent.BUTTON1_DOWN_MASK) );
		
		log( c.wait(1000) );
		log( c.mouseClick(InputEvent.BUTTON1_DOWN_MASK,2) );
		log( c.wait(1000) );
		colScan = c.getColor(25, 25);
		for (int i=0; (i < 20) && colScan.equals(c.getColor(25, 25)); i++) log( c.wait(1000) + colScan.equals(c.getColor(25, 25)));
		log( c.wait(2000) );
		
		log( c.type('V'));
		log( c.wait(4000) );
		log( c.type(KeyEvent.VK_LEFT,5));
		return true;
	}
	
	public static void log(String message) { System.out.println(message); }
	public static void log(String message, boolean bcase) { if (bcase) System.out.println(message); }
	public static void log(int[] val, boolean bcase) { if (bcase) for (int v : val) System.out.print(" ("+v+")"); }
}
