package General;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InstaBot implements Bot
{
	static Color colScan;
	static int[] t = new int[2];
	static Command c = null;
	static String message = "Message to return";
	
	public InstaBot() {
		c = Taubot.getCommand();
		message = "initialized";
	}
	
	private static void target(int x, int y) {
		t[0] = x;
		t[1] = y;
	}
	
	public String getMessage() { return message; }
	
	public boolean bot(String[] hashtag, int repeats)
	{
		if (!botInitialWork())
			return false;
		
		for (String htag : hashtag)
		{
			if (!botTagWork(htag, repeats))
				return false;
		}
		message = "Instagram bot successfully completed";
		return true;
	}
	
	public boolean bot(String hashtag, int repeats)
	{
		if (!botInitialWork())
			return false;
		
		if (!botTagWork(hashtag, repeats))
				return false;
		
		message = "Instagram bot successfully completed";
		return true;
	}
	
	private boolean botInitialWork ()
	{
		if(!c.openWebApp("Instagram") )
			return false;
		
		colScan = new Color(58, 58, 58);
		Taubot.log("Window = "+c.colorCompare(colScan, c.getColor(500, 1)));
		Taubot.log(colScan+" vs "+c.getColor(500, 1));
		if(!c.colorCompareMargin(colScan.getRGB(), c.getColor(500, 1).getRGB(), 5)) {
			if(!fillWindow())
				return false;
		}
		
		c.wait(2000);
		return true;
	}
	
	private boolean botTagWork(String htag, int repeats)
	{
		if(!instaSearch(htag))
			return false;
		
		c.wait(3000);
		
		if (!openPosts())
			return false;
		
		c.wait((int) (500+300*Math.random()) );
		
		colScan = new Color(125, 125, 125);
		for(int i=0; i < repeats; i++)
			if (!postAction()) {
				message += " after liking "+i+" posts";
				return false;
			}
		Taubot.log("Post in "+htag+" liked x"+repeats);
		if(!postReset())
			return false;
		
		return true;
	}
	
	private boolean postAction ()
	{
		c.wait((int) (500+1000*Math.random()) );
		if (!c.colorCompareMargin(colScan.getRGB(), c.getColor(125, 500).getRGB(), 5))
		{
			message = "Post not identified";
			return false;
		}
		c.mouseClick(MouseEvent.BUTTON1_DOWN_MASK, 2);
		c.mouseMove((int) (400+100*Math.random()), (int) (450+100*Math.random()));
		c.wait((int) (30+300*Math.random()) );
		c.type(KeyEvent.VK_RIGHT);
		return true;
	}
	
	private boolean postReset ()
	{
		Taubot.log("___Entered Post Reset____");
		c.type(KeyEvent.VK_ESCAPE);
		c.wait(300);
		c.type(KeyEvent.VK_PAGE_UP, 800);
		Taubot.log(" Post Reset Success");
		return true;
	}
	
	private boolean openPosts ()
	{
		Taubot.log("___Entered Open Posts____");
		c.mouseAct(1000, 500);
		c.mouseAct(1000, 700);
		
		c.mouseMove(300, 500);
		c.mouseTo(300, 500);
		c.mouseClick();
		c.wait(1000);
		
		c.mouseMove(500, 500);
		c.mouseTo(500, 500);
		
		Taubot.log(" Open Post Success");
		return true;
	}
	
	private boolean fillWindow ()
	{
		Taubot.log("___Entered Fill Window____");
		if (!c.colCompRangeCheck(450, 3, 550, 500, colScan.getRGB(), 5)) {
			message = "Error: Searching for window:\n Did not find color in range";
			return false;
		}
		int[] loc = c.colCompareRange(450, 3, 550, 500, colScan.getRGB(), 5);
		c.mouseTo(loc[0], loc[1]);
		c.wait(300);
		c.mouseClick();
		c.wait(300);
		c.mouseClick();
		Taubot.log(" Fill Window Success");
		return true;
	}
	
	private boolean instaSearch (String searchText)
	{
		Taubot.log("___Entered insta search____");
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
		
		Taubot.log(" Insta Search Success");
		return true;
	}
}
