package General;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InstaBot implements Bot
{
	static Color colScan;
	static int[] t = new int[2];
	static Command c = null;
	
	public InstaBot(Command com) {
		c = com;
	}
	
	public static void target(int x, int y) {
		t[0] = x;
		t[1] = y;
	}
	
	public String bot(String hashtag, int repeats)
	{
		if(!c.openWebApp("Instagram") )
			return "Failed to open app";
		
		colScan = new Color(58, 58, 58);
		if(c.colorCompare(colScan, c.getColor(500, 3))) {
			if(!fillWindow())
				return "Failed to fill window";
		}
			
		c.wait(2000);
		
		if(!instaSearch(hashtag))
			return "Failed to search hashtag";
		
		c.wait(3000);
		
		if (!openPosts())
			return "Failed to open post";
		
		c.wait((int) (500+300*Math.random()) );
		
		for(int i=0; i < repeats; i++)
			if (!postAction())
				break;
		
		return "Success";
	}
	
	private boolean postAction ()
	{
		Main.log("___Entered Post Action____");
		c.mouseClick(MouseEvent.BUTTON1_DOWN_MASK, 2);
		c.mouseMove((int) (400+100*Math.random()), (int) (450+100*Math.random()));
		c.wait((int) (300+300*Math.random()) );
		c.type(KeyEvent.VK_RIGHT);
		c.wait((int) (1000+1000*Math.random()) );
		
		Main.log("___Exit Post Action____");
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
		
		Main.log("___Exit Open Posts____");
		return true;
	}
	
	private boolean fillWindow ()
	{
		Main.log("___Entered Fill Window____");
		if (!c.colCompRangeCheck(450, 3, 550, 500, colScan.getRGB(), 3))
			return false;
		int[] loc = c.colCompareRange(450, 3, 550, 500, colScan.getRGB(), 3);
		c.mouseTo(loc[0], loc[1]);
		c.wait(300);
		c.mouseClick();
		c.wait(300);
		c.mouseClick();
		Main.log("___Exit Fill Window____");
		return true;
	}
	
	private boolean instaSearch (String searchText)
	{
		Main.log("___Entered insta search____");
		// First check if  finding the white area in instagram header
		colScan = new Color(255, 255, 255);
		if (!c.colCompRangeCheck(100, 20, 101, 500, colScan.getRGB(), 1))
			return false;
		int[] loc = c.colCompareRange(100, 20, 101, 500, colScan.getRGB(), 1);
		c.mouseMove(loc[0], loc[1]);
		
		c.wait(500);
		
		//then check if finding the end of the white are in instagram header
		colScan = new Color(250, 250, 250);
		if (!c.colCompRangeCheck(loc[0], loc[1]+1, loc[0]+1, 500, colScan.getRGB(), 2))
			return false;
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
		
		Main.log("___Exit insta search____");
		return true;
	}
}
