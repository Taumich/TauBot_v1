package General;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GWBot implements Bot
{
	static Color colScan;
	static int[] t = new int[2];
	static Command c = null;
	
	public GWBot(Command com) {
		c = com;
	}
	
	public static void target(int x, int y) {
		t[0] = x;
		t[1] = y;
	}
	
	public boolean bot()
	{
		Color loginButtonColor = new Color(210,76,13);
		
		c.mouseMove(500, 500);
		
		tabDisturbingCheck(loginButtonColor);
		
		boolean openSuccess = c.openApp("Guild Wars 2");
		log( "Open success: "+openSuccess );
		if(!openSuccess)
			return false;
		
//launched the game-launcher, waiting for log-in button to click log-in
		c.wait(5000);
		
		loginButton(loginButtonColor);
		
		c.wait(1000);
		
		//target(550, 700);
		//Window window = new Window(c.screenCaptureRange(t[0],t[1], t[0]+200, t[1]+150));
		//window.render();
		
		Color playButtonColor = new Color(225,109,21);
		if (!playButton(playButtonColor, 20) )
		{
			log( "testing other way");
		
			//target(400, 577);
			Window window = new Window(c.screenCaptureRange(t[0],t[1], t[0]-200, t[1]+150));
			window.render();
			
			Color remindLaterButton = new Color(169, 110, 118);
			remindLater(remindLaterButton, 30);
			c.wait(1000);
			c.closeWindow();
			c.wait(2000);
			
			playButton(playButtonColor, 25);
		}
		
		
//checking if countdown toggle is on.
		//countdownButton();
		
//checking if countdown is counting
		/*t[0] = 521; t[1] = 761; colScan = c.getColor(t[0], t[1]);
		if(colScan.getRed() < 50) { //if black, we know countdown is not on
			t[0] = 327; t[1] = 617;
			log( c.mouseAct(t[0], t[1]) );
		}
		
		for (int i=0; (i < 5) && c.getColor(t[0], t[1]).getRed() > colScan.getRed(); i++)
			log( c.wait(1000) + colScan.equals(c.getColor(t[0], t[1])));
		log( c.mouseAct(t[0], t[1]) );
		log( c.wait(1000) );
		
		//to close Arch 283, 100
		
		t[0] = 10; t[1] = 10;
		colScan = c.getColor(t[0], t[1]);
		log( c.mouseMove(11,11) );
		for (int i=0; (i < 20) && colScan.equals(c.getColor(t[0], t[1])); i++)
			log( c.wait(1000) + colScan.equals(c.getColor(t[0], t[1])));
		log( c.wait(4000) );
		
		colScan = c.getColor(t[0], t[1]);
		for (int i=0; (i < 20) && colScan.equals(c.getColor(t[0], t[1])); i++)
			log( c.wait(1000) + colScan.equals(c.getColor(t[0], t[1])));
		log( c.wait(4000) );
		
		log( c.mouseMove(10,80) );
		log( c.mouseMove(15,93) );
		log( c.mouseClick(InputEvent.BUTTON1_DOWN_MASK,2) );
		log( c.wait(1000) );
		
		t[0] = 25; t[1] = 25;
		colScan = c.getColor(t[0], t[1]);
		for (int i=0; (i < 20) && colScan.equals(c.getColor(t[0], t[1])); i++) log( c.wait(1000) + colScan.equals(c.getColor(25, 25)));
		log( c.wait(1000) );
		
		log( c.type('V'));
		log( c.wait(4000) );
		log( c.type(KeyEvent.VK_LEFT,5));//*/
		return true;
	}
	
	
	public boolean countdownButton ()
	{
		target(418,577);
		colScan = c.getColor(t[0], t[1]);
		
		if(!c.colorCompare(colScan, 28, 1, 49, 5)) { //if false, we know countdown is not on
			log( c.mouseAct(t[0], t[1]) );
		}
		c.wait(1000);
		return true;
	}
	
	public boolean tabDisturbingCheck (Color color)
	{
		//closes any tab if tab is disturbing
		colScan = c.getColor(t[0], t[1]);
		if(colScan.getRed() > color.getRed()) {
			log( "Closed window = "+c.closeWindow() );
			colScan = c.getColor(t[0], t[1]);
			return true;
		}
		return false;
	}
	
	public boolean loginButton (Color buttonColor)
	{
		log("___Entered Login Code___");
		
		//target(249, 670);
		target(418, 577);
		
		log("   Checks if logged in");
		for (int i=0; (i < 10) && !c.colCompRangeCheck(t[0],t[1], t[0]-200, t[1]+100, buttonColor.getRGB(), 30 ); i++)
			log( c.wait(1000) );
		int loginButtonLoc[] = c.colCompareRange(t[0],t[1], t[0]-200, t[1]+100, buttonColor.getRGB(), 30);
		c.mouseAct(loginButtonLoc[0], loginButtonLoc[1]);
		
		log("___Exit Login Code___");
		return true;
	}
	
	public boolean playButton (Color buttonColor, int diff)
	{
		log("___Entered Play Code___");
		
		target(550, 700);
		for (int i=0; (i < 2) && !c.colCompRangeCheck(t[0],t[1], t[0]+200, t[1]+150, buttonColor.getRGB(), diff ); i++)
			log( c.wait(1000) );
		
		if (c.colCompRangeCheck(t[0],t[1], t[0]+200, t[1]+150, buttonColor.getRGB(), diff ))
		{
			c.mouseMove(500, 500);
			int playButtonLoc[] = c.colCompareRange(t[0],t[1], t[0]+200, t[1]+150, buttonColor.getRGB(), diff);
			log( c.mouseAct(playButtonLoc[0], playButtonLoc[1]) );
		}
		else
		{
			log( "failed to find play button");
			return false;
		}
		
		log("___Exit Play Code___");
		return true;
	}
	
	public boolean remindLater (Color buttonColor, int diff)
	{
		log("___Entered Remind Later Code___");
		
		target(400, 577);
		for (int i=0; (i < 2) && !c.colCompRangeCheck(t[0],t[1], t[0]-200, t[1]+150, buttonColor.getRGB(), diff ); i++)
			log( c.wait(1000) );
		
		if (c.colCompRangeCheck(t[0],t[1], t[0]-200, t[1]+150, buttonColor.getRGB(), diff ))
		{
			c.mouseMove(500, 500);
			int remindButtonLoc[] = c.colCompareRange(t[0],t[1], t[0]-200, t[1]+150, buttonColor.getRGB(), diff);
			log( c.mouseAct(remindButtonLoc[0], remindButtonLoc[1]+1) );
		}
		
		log("___Exit Play Code___");
		return true;
	}
	
	public static void log(String message) { System.out.println(message); }
	public static void log(String message, boolean bcase) { if (bcase) System.out.println(message); }
	public static void log(int[] val, boolean bcase) { if (bcase) for (int v : val) System.out.print(" ("+v+")"); }

}
