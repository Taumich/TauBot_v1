package General;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class Window
{
	JFrame 		frame 	 = new JFrame();
	JTextArea 	console  = new JTextArea("Console log:"),
				modifier = new JTextArea("#warhammer40k,#blender3D,#3dmodeling,#w40k,#b3d,#cyberpunk,#gothic");
	Container 	box_Top  = new Container(),
				box_L 	 = new Container(),
				box_R 	 = new Container();
	JButton[] 	button;
	BufferedImage image;
	
	public Window () {}
	
	public Window (BufferedImage image)
	{
		this.image = image;
	}
	
	public void start()
	{
		String[] buttonNames = {"Insta Modifier Liker","GW-Launcher","Insta W40k Liker 30","Insta W40k Liker 100","Insta Blender Liker 100"};
		JTextArea 	welcomeText = new JTextArea("Welcome! \nI am TauBot MKI. This is my interface with you. Here are some available commands:");
		JTextArea 	modTitle = new JTextArea("Command Modifier:");
		button = new JButton[buttonNames.length];
		
		int dim[] = {612, 350};
		
		box_Top.setBounds(0, 0, dim[0], 60);
		
		//Setup welcome text:
		welcomeText.setBounds(10,10,dim[0],80);
		welcomeText.setBackground(new Color(250,250,250));
		welcomeText.setWrapStyleWord(true);
		welcomeText.setLineWrap(true);
		welcomeText.setOpaque(false);
		welcomeText.setEditable(false);
		
		//Setup tag modifier:
		modifier.setBounds(2,75,190,33);
		modifier.setBackground(new Color(200,200,200));
		modifier.setWrapStyleWord(true);
		modifier.setLineWrap(true);
		modifier.setEditable(true);
		
		//Setup Tag Modifier Title:
		modTitle.setBounds(2,60,190,30);
		modTitle.setBackground(new Color(250,250,250));
		modTitle.setWrapStyleWord(true);
		modTitle.setEditable(false);
		
		//Setup Console:
		console.setBounds(200, 60, (dim[0]*2/3)-20, 1000);
		console.setBackground(new Color(200,200,200));
		console.setWrapStyleWord(true);
		console.setLineWrap(true);
		//console.setEditable(false);
		DefaultCaret caret = (DefaultCaret) console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//Tiling boxes alignment
		box_Top.add(welcomeText);
		box_R.add(modifier);
		box_R.add(modTitle);
		box_L.setBounds(0, 60, dim[0]/3, dim[1]-60);
		box_R.add(console);
		box_R.setBounds(200, 60, (dim[0]*2/3)-20, 1000);
		
		//Button array production
		for (int i=0; i < buttonNames.length; i++)
		{
			button[i] = createArrayButton(i,buttonNames[i]);
			button[i].setBounds(2, 55+i*45, 190, 40);
			button[i].setBackground(Color.WHITE);
			box_L.add(button[i]);
		}
		
		frame.setMinimumSize(new Dimension(dim[0], dim[1]));
		frame.add(box_Top);
		frame.add(box_L);
		frame.add(box_R);
		frame.getContentPane().setBackground(new Color(250,250,250));
		frame.setTitle("TauBot MKI");
		//frame.setLayout(null);
		frame.setSize(dim[0],dim[1]);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JButton createArrayButton(final int i, String name) {
        final JButton b = new JButton(name);
        
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Taubot.launch(i);
            }
        });
        return b;
    }
	
	public void buttonEvent() {
		Taubot.log("hello");
	}
	
	public void setImage (BufferedImage image) {
		this.image = image;
	}
	
	public void render() {
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void text(String text) {
		String oldText = console.getText();
		console.append(text);
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
	
	public String getModifier () {
		return modifier.getText();
	}
}
