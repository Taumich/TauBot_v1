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
		
		GWBot gwbot = new GWBot(c);
		
		log( c.pixelScan(2000, 249, 670) );
		if(true)
			for (int i = 0; i < 10; i++)
				log( c.pixelScan(2000, 1) );
		else
			log( "gwBot completed = "+ gwbot.bot() );
	}
	
	public static void log(String message) { System.out.println(message); }
	public static void log(String message, boolean bcase) { if (bcase) System.out.println(message); }
	public static void log(int[] val, boolean bcase) { if (bcase) for (int v : val) System.out.print(" ("+v+")"); }
}
