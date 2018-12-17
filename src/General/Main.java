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
		
		//c.screenCaptureRange(418,577, 518, 77);
		
		c.mouseTo(418, 577);
		c.wait(3000);
		
		//log( c.pixelScan(2000, 418,577) );
		//log( "color = "+c.colRecComp(3,3,418,577) );
		//Window window = new Window(c.subScreenCapture(418,577,100));
		Window window = new Window(c.screenCaptureRange(418,577, 418-200, 577+100));
		
		//window.pixelColor(0, 0);
		
		//window.setImage(c.screenCaptureRange(418,577, 418-200, 577+100));
		
		window.render();
		window.pixelColor(0, 0);
		
		Color col = new Color(210,76,13);
		
		int res[] = c.colCompareRange(418,577, 418-200, 577+100, col.getRGB(), 30);
		log("matching coords: "+res[0]+" "+res[1]);
		
		c.mouseMove(res[0],res[1]);
		
		/*
		if(true)
			for (int i = 0; i < 10; i++)
				log( c.pixelScan(2000, 1) );
		else
			log( "gwBot completed = "+ gwbot.bot() ); //*/
	}
	
	public static void log(String message) { System.out.println(message); }
	public static void log(String message, boolean bcase) { if (bcase) System.out.println(message); }
	public static void log(int[] val, boolean bcase) { if (bcase) for (int v : val) System.out.print(" ("+v+")"); }
}
