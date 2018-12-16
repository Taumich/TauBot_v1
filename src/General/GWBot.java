package General;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GWBot
{
	static Color colScan;
	static int[] t = new int[2];
	static Command c = null;
	
	public static void target(int x, int y) {
		t[0] = x;
		t[1] = y;
	}
	
	public GWBot(Command com) {
		c = com;
	}
	
	public boolean bot()
	{
		boolean openSuccess = c.openApp("Guild Wars 2");
		log( "Open success: "+openSuccess );
		if(!openSuccess)
			return false;
		
//launched the game-launcher, waiting for log-in button to click log-in
		loginButton();
		
//checking if countdown toggle is on.
		t[0] = 564; t[1] = 760; colScan = c.getColor(t[0], t[1]);
		if(colScan.getRed() < 80) { //if black, we know countdown is not on
			log( c.mouseAct(t[0], t[1]) );
		}
		c.wait(1000);
		
//checking if countdown is counting
		t[0] = 521; t[1] = 761; colScan = c.getColor(t[0], t[1]);
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
		log( c.type(KeyEvent.VK_LEFT,5));
		return true;
	}
	
	public boolean loginButton() {
		log("___Entered Login Code___");
		int redLim = 230;
		target(249, 670);
		
		//closes any tab if tab is disturbing
		colScan = c.getColor(t[0], t[1]);
		if(colScan.getRed() > redLim) {
			c.mouseAct(950, 1);
			colScan = c.getColor(t[0], t[1]);
		}
		
		log("   Checks if logged in");
		for (int i=0; (i < 10) && c.getColor(t[0], t[1]).getRed() < redLim; i++)
			log( c.wait(1000) +" "+ (c.getColor(t[0], t[1]).getRed()) );
		
		if(c.getColor(t[0], t[1]).getRed() > redLim) {
			log("   Was not logged in so acts now");
			
			log( c.mouseAct(t[0], t[1]) );
			
			c.wait(1000);
			log("___Exit Login Code___");
			return true;
		}
		return false;
	}
	
	public static void log(String message) { System.out.println(message); }
	public static void log(String message, boolean bcase) { if (bcase) System.out.println(message); }
	public static void log(int[] val, boolean bcase) { if (bcase) for (int v : val) System.out.print(" ("+v+")"); }

}
