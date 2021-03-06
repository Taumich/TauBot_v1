package General;

import java.awt.AWTException;

public class Taubot
{
	static GWBot gwbot;
	static InstaBot instabot;
	static Window win;
	static Command c = null;
	
	public static void taubot()
	{
		try { c = new Command(); }
		catch (AWTException e) { e.printStackTrace(); }
		gwbot = new GWBot();
		instabot = new InstaBot();
		win = new Window();
		
		win.start();
	}
	
	public static Command getCommand() {
		return c;
	}
	
	public static Bot[] getBots () {
		Bot[] bots = {gwbot, instabot};
		return bots;
	}
	
	public static Window getWindow () {
		return win;
	}
	
	public static void launch (int i) {
		switch(i)
		{
			case 0:
				instabot.bot(win.getModifier().replaceAll(" ","").split(","));
				log( instabot.getMessage());
				break;
			case 1:
				log( "gwBot completed = "+ gwbot.bot() );
				break;//*/	
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
			case 5:
				instabot.bot("#warhammer40k", 100);
				log( instabot.getMessage());
				instabot.bot("#blender3d", 100);
				log( instabot.getMessage());
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
