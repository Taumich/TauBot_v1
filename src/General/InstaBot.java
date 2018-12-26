package General;

import java.awt.Color;

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
	
	public boolean bot()
	{
		boolean b = c.openWebApp("Instagram");
		Window window = new Window(c.screenCaptureRange(400, 400, 600, 600));
		window.render();
		return b;
	}
}
