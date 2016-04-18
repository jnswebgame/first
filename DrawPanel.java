
import java.awt.*;
import javax.swing.*;

public class DrawPanel
{
	MoveToMouse panel;
	JButton button;

	public DrawPanel(MoveToMouse panel)
	{
		this.panel = panel;
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
		panel.setBackground(Color.BLUE);
	}

	private void drawPanelFour(Graphics g)
	{
		panel.setBackground(Color.GREEN);
	}




}