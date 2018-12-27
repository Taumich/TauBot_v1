package General;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InstaBot implements Bot
{
	static Color colScan;
	static int[] t = new int[2];
	static Command c = null;
	static String message = "not initialized";
	
	public InstaBot(Command com) {
		c = com;
		message = "initialized";
	}
	
	private static void target(int x, int y) {
		t[0] = x;
		t[1] = y;
	}
	
	public String getMessage() { return message; }
	
	public boolean bot(String hashtag, int repeats)
	{
		if(!c.openWebApp("Instagram") )
			return false;
		
		colScan = new Color(58, 58, 58);
		Main.log("Window = "+c.colorCompare(colScan, c.getColor(500, 1)));
		Main.log(colScan+" vs "+c.getColor(500, 1));
		if(!c.colorCompare(colScan, c.getColor(500, 1))) {
			if(!fillWindow())
				return false;
		}
			
		c.wait(2000);
		
		if(!instaSearch(hashtag))
			return false;
		
		c.wait(3000);
		
		if (!openPosts())
			return false;
		
		c.wait((int) (500+300*Math.random()) );
		
		for(int i=0; i < repeats; i++)
			if (!postAction())
				break;
		
		message = "Instagram bot successfully completed";
		return true;
	}
	
	private boolean postAction ()
	{
		Main.log("___Entered Post Action____");
		c.mouseClick(MouseEvent.BUTTON1_DOWN_MASK, 2);
		c.mouseMove((int) (400+100*Math.random()), (int) (450+100*Math.random()));
		c.wait((int) (30+300*Math.random()) );
		c.type(KeyEvent.VK_RIGHT);
		c.wait((int) (500+1000*Math.random()) );
		
		Main.log(" Post Action Success");
		return true;
	}
	
	private boolean openPosts ()
	{
		Main.log("___Entered Open Posts____");
		c.mouseAct(1000, 500);
		c.mouseAct(1000, 700);
		
		c.mouseMove(300, 500);
		c.mouseTo(300, 500);
		c.mouseClick();
		c.wait(1000);
		
		c.mouseMove(500, 500);
		c.mouseTo(500, 500);
		
		Main.log(" Open Post Success");
		return true;
	}
	
	private boolean fillWindow ()
	{
		Main.log("___Entered Fill Window____");
		if (!c.colCompRangeCheck(450, 3, 550, 500, colScan.getRGB(), 3)) {
			message = "Error: Searching for window:\n Did not find color in range";
			return false;
		}
		int[] loc = c.colCompareRange(450, 3, 550, 500, colScan.getRGB(), 3);
		c.mouseTo(loc[0], loc[1]);
		c.wait(300);
		c.mouseClick();
		c.wait(300);
		c.mouseClick();
		Main.log(" Fill Window Success");
		return true;
	}
	
	private boolean instaSearch (String searchText)
	{
		Main.log("___Entered insta search____");
		// First check if  finding the white area in instagram header
		colScan = new Color(255, 255, 255);
		if (!c.colCompRangeCheck(100, 20, 101, 500, colScan.getRGB(), 1)) {
			message = "Error: Searching for window white area:\n Did not find color in range";
			return false;
		}
		int[] loc = c.colCompareRange(100, 20, 101, 500, colScan.getRGB(), 1);
		c.mouseMove(loc[0], loc[1]);
		
		c.wait(500);
		
		//then check if finding the end of the white are in instagram header
		colScan = new Color(250, 250, 250);
		if (!c.colCompRangeCheck(loc[0], loc[1]+1, loc[0]+1, 500, colScan.getRGB(), 2)) {
			message = "Error: Searching for window insta area:\n Did not find color in range";
			return false;
		}
		int[] locEnd = c.colCompareRange(loc[0], loc[1]+1, loc[0]+10, 500, colScan.getRGB(), 2);
		c.mouseMove(locEnd[0], locEnd[1]);
		
		//c.mouseTo(500, (loc[1]+locEnd[1])/2);
		c.mouseAct(500, (loc[1]+locEnd[1])/2);
		c.mouseTo(500, (loc[1]+locEnd[1])/2);
		c.mouseClick();
		
		c.paste(searchText);
		
		c.wait(300);
		c.type(KeyEvent.VK_ENTER);
		c.wait(300);
		c.type(KeyEvent.VK_ENTER);
		c.wait(300);
		c.type(KeyEvent.VK_ENTER);
		
		Main.log(" Insta Search Success");
		return true;
	}
}
