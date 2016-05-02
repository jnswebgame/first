 /*"bar" and "bottombar" refer to a panel on the bottom that contains info about the
 * player and their possible actions. It should take up a minority of the screen.
 * Something like:
 *  ________
 * |        | <-players and their animations
 * |        |
 * |________|
 * |________| <-the bar
 */

import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class CombatPanel implements ActionListener
{
	private JButton attack;
	private JButton bribe;
	private JLabel hpDisplay;
	private JLabel goldAmt;
	public JLabel message;
	private Combat combat;
	//private Player play;
	private GridLayout barLayout = new GridLayout(1, 5);
	private GridLayout mainLayout = new GridLayout(1,2);
	private JPanel bar = new JPanel();
	private JPanel mainScene = new JPanel();
	private JPanel entireThing = new JPanel();
	private String hpString;

	//private [thing for player 1]
	//private [thing for player 2]
	//private [background image]

	public static void main(String[] args)
	{
		CombatPanel pan = new CombatPanel();

	}

	public CombatPanel()
	{
		combat = new Combat(new Player(), new Player(), this);
		attack = new JButton("Attack");
		bribe = new JButton("Bribe");
		hpDisplay = new JLabel("hp");
		goldAmt = new JLabel("Gold");
		message = new JLabel("message");
		hpString = "HP is";
	}

	public void initiate(Player p1, Player p2)
	{
		bottomBar(p1);
		mainScene();

		entireThing.add(mainScene);
		entireThing.add(bar);

		combat.newBattle(p1, p2);

	}

	private void bottomBar(Player play)
	{
		hpString=play.getHP() + "/30";
		String g = play.getGold()+" Gold";
		goldAmt = new JLabel(g);
		hpDisplay=new JLabel(hpString);
		attack = new JButton("Attack");
		bribe = new JButton("Bribe");
		attack.addActionListener(this);
		bribe.addActionListener(this);

		bar.setSize(500,100);
		bar.setLayout(barLayout);
		bar.add(bribe);
		bar.add(attack);
		bar.add(goldAmt);
		bar.add(hpDisplay);
		bar.add(message);
	}
	private void mainScene()
	{
	//	the player images are both nonexistent, just some placeholders in the comments here
		mainScene.setLayout(mainLayout);
		mainScene.setSize(500,400);
		mainScene.setBackground(null); //some image
		//add player1image
		//add player2image
	}
	protected void updateHP(int hit)
	{

		bar.remove(hpDisplay);
		bar.remove(message);
		String hitpoints=hit + "/30";
		hpString=hitpoints;
		hpDisplay=new JLabel(hitpoints);
		bar.add(hpDisplay);
		bar.add(message);
		if(combat.getEnd()==true)
		{
			//go to latest non-combat panel
		}

	}
	protected void updateGold(int g)
	{
		bar.remove(goldAmt);
		bar.remove(hpDisplay);
		bar.remove(message);
		String newGold = g + " Gold";
		goldAmt = new JLabel(newGold);
		bar.add(goldAmt);
		bar.add(hpDisplay);
		bar.add(message);
		if(combat.getEnd()==true)
		{
			//go to latest non-combat panel
		}
	}
	protected void updateMessage(String msg)
	{
		bar.remove(message);
		message=new JLabel(msg);
		bar.add(message);
	}
	public JPanel getCombatPanel()
	{
		return entireThing;
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==attack)
		{
			combat.action("attack");
		}
		if(e.getSource()==bribe)
		{
			combat.action("cash");
		}
	}
}

