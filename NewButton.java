import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewButton extends JButton
{
	private String path;
	private int sizex;
	private int sizey;

	public NewButton(String p,int x,int y)
	{
		super(p);
		this.sizex = x;
		this.sizey = y;
		setContentAreaFilled(false);
	}

	protected void paintComponent(Graphics g)
	{

		super.paintComponent(g);
	}
	
}