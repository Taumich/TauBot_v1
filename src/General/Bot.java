package General;

import java.awt.Color;

public interface Bot
{
	Color colScan = null;
	int[] t = new int[2];
	Command c = null;
	String message = "Message to return";
	
	public static void target(int x, int y) {}
	public default boolean bot() { return false; }
}
