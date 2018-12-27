package General;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Main
{
	static GWBot gwbot;
	static InstaBot instabot;
	static Window win;
	
	public static void main (String[] args)
	{
		taubot();
	}
	
	public static void taubot()
	{
		Command c = null;
		try { c = new Command(); }
		catch (AWTException e) { e.printStackTrace(); }
		gwbot = new GWBot(c);
		instabot = new InstaBot(c);
		win = new Window();
		
		win.start();
	}
	
	public static void launch (int i) {
		switch(i)
		{
			case 0:	log( "idle test"); break;
			case 1:	log( "gwBot completed = "+ gwbot.bot() ); break;//*/	
			case 2:
				instabot.bot("#warhammer40k", 30);
				log( instabot.getMessage());
				break;
			case 3:
				instabot.bot("#warhammer40k", 100);
				log( instabot.getMessage());
				break;
			case 4:
				instabot.bot("#blender3d", 100);
				log( instabot.getMessage());
				break;
		}
	}
	
	public static void log(String message)
	{
		System.out.println(message);
		win.text("\n"+message);
	}
	public static void log(String message, boolean bcase)
	{
		if (bcase) log(message);
	}
	public static void log(int[] val, boolean bcase)
	{
		if (bcase) for (int v : val) log(" ("+v+")");
	}
}
