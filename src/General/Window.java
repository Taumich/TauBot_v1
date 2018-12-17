package General;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window
{
	JFrame frame;
	BufferedImage image;
	
	public Window (BufferedImage image)
	{
		this.image = image;
		frame = new JFrame();
	}
	
	public void setImage (BufferedImage image) {
		this.image = image;
	}
	
	public void render() {
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void pixelColor(int x, int y)
	{
		x = (int) (x*image.getHeight()/1000);
		y = (int) (y*image.getWidth()/1000);
		int clr = image.getRGB(x, y);
		int  red   = (clr & 0x00ff0000) >> 16;
		int  green = (clr & 0x0000ff00) >> 8;
		int  blue  =  clr & 0x000000ff;
		
		System.out.println("R = "+ red);
		System.out.println("G = "+ green);
		System.out.println("B = "+ blue);
	}
	
	public int imageResolution(boolean height) {
		return (height)? image.getHeight() : image.getWidth();
	}
}
