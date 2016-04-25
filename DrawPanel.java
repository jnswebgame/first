
import java.awt.*;
import javax.swing.*;
import javax.imageio;

public class DrawPanel
{
	MoveToMouse panel;
	JButton button;
	BufferedImage background1;
	BufferedImage background2;
	BufferedImage background3;
	BufferedImage background4;


	public DrawPanel(MoveToMouse panel)
	{
		this.panel = panel;
		try
		{
			background1 = ImageIO.read(new File("level1.jpg"));
			background2 = ImageIO.read(new File("level2.jpg"));
			background3 = ImageIO.read(new File("level3.jpg"));
			background4 = ImageIO.read(new File("level4.jpg"));
		}
		catch(IOException e){}
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
		g.drawImage(background1, 0, 0, MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT);
	}

	private void drawPanelFour(Graphics g)
	{
		g.drawImage(background2, 0, 0, MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT);
	}

	private void drawPanelFive(Graphics g)
	{
		g.drawImage(background3, 0, 0, MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT); 
	}

	private void drawPanelSix(Graphics g)
	{
		g.drawImage(background4, 0, 0, MoveToMouse.PREF_WIDTH, MoveToMouse.PREF_HEIGHT);
	}	


}