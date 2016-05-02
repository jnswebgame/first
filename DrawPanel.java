
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;


public class DrawPanel implements ActionListener
{
	MoveToMouse panel;
	JButton button;
	JButton attack;
	JButton coin;
	JLabel label;

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
		attack = new JButton("Attack");
		coin = new JButton("Coin");
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
		//panel.setBackground(Color.WHITE);
		g.drawImage(background4, 0, 0, panel);
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
		g.drawImage(background3, 0, 0, panel);
		//panel.setBackground(Color.RED);
		//panel.setLayout(null);
		//button = new JButton("Attack");
		//button.setBounds(50, 250, 50, 50);
		//panel.add(button);

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
		Player play = panel.getLocalPlayer();
		g.drawImage(background1, 0, 0, panel);
		g.setColor(Color.WHITE);
		panel.setLayout(null);
		attack.setBounds(100, 250, 90, 40);
		attack.addActionListener(this);
		panel.add(attack);
		//coin.setBounds(100, 300, 90, 40);
		//panel.add(coin);
	}

	public void updateBattle(GameEvent e, Player local, Player enemy)
	{
		panel.setLayout(null);
		label = new JLabel();
		label.setText("You attacked!");
		label.setBounds(150, 200, 50, 50);
		panel.add(label);
	}

	private void drawPanelSix(Graphics g)
	{
		g.drawImage(background4, 0, 0, panel);
	}
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == attack)
		{
			panel.sendBattleCommand("attack");
			//System.out.println("Attacked");
		}
	}


}