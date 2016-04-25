
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;


public class DrawPanel
{
	MoveToMouse panel;
	JButton button;

	private ImageIcon b1;
	private ImageIcon b2;
	private ImageIcon b3;
	private ImageIcon b4;

	private Image background1;
	private Image background2;
	private Image background3;
	private Image background4;	

	public DrawPanel(MoveToMouse panel)
	{
		this.panel = panel;
		b1 = new ImageIcon("level1.jpg");
		b2 = new ImageIcon("level2.jpg");
		b3 = new ImageIcon("level3.jpg");
		b4 = new ImageIcon("level4.jpg");

		background1 = b1.getImage().getScaledInstance(MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT, Image.SCALE_DEFAULT);
		background2 = b2.getImage().getScaledInstance(MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT, Image.SCALE_DEFAULT);
		background3 = b3.getImage().getScaledInstance(MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT, Image.SCALE_DEFAULT);
		background4 = b4.getImage().getScaledInstance(MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT, Image.SCALE_DEFAULT);
	}

	public void drawPanel(int panel, Graphics g)
	{
		switch (panel)
		{
			case 0: drawPanelOne(g);
				break;
			case 1: drawPanelTwo(g);
				break;
			case 2: drawPanelThree(g);
				break;
			case 3: drawPanelFour(g);
				break;
			case 4: drawPanelFive(g);
				break;
			case 5: drawPanelSix(g);
				break;								
		}

	}

	private void drawPanelOne(Graphics g)
	{
		panel.removeAll();
		panel.setBackground(Color.WHITE);
		/*Component c = panel.getComponentAt(50, 250);
		if (c != null && c != panel)
		{
			panel.remove(button);
			panel.validate();
		}*/
		if (button != null)
		{

			panel.remove(button);
			panel.invalidate();
			button = null;
		}

	}

	private void drawPanelTwo(Graphics g)
	{
		panel.setBackground(Color.RED);
		panel.setLayout(null);
		button = new JButton("Attack");
		button.setBounds(50, 250, 50, 50);
		panel.add(button);

	}

	private void drawPanelThree(Graphics g)
	{
		g.drawImage(background1, 0, 0, panel);
	}

	private void drawPanelFour(Graphics g)
	{
		g.drawImage(background2, 0, 0, panel);
	}

	private void drawPanelFive(Graphics g)
	{
		g.drawImage(background3, 0, 0, panel); 
	}

	private void drawPanelSix(Graphics g)
	{
		g.drawImage(background4, 0, 0, panel);
	}	


}