import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MoveToMouse extends JPanel implements ActionListener //, MouseListener
{
	private ArrayList<Player> players;
	private ArrayList<Gold> golds;
	private int localId = -1;
	Player localPlayer;

	public int velocity = 7;

	public Timer timer;

	public void setId(int id)
	{
		localId = id;
	}

	public MoveToMouse()
	{
		localPlayer = new Player();
		players = new ArrayList<Player>();
		golds = new ArrayList<Gold>();
		//addMouseListener(this);
		timer = new Timer(50, this);
		timer.start();
	}

	public void updateLocal(Player play)
	{
		localPlayer = play;
	}

	public void update(GameEvent event)
	{
		boolean found = false;

		// Gold
		if (event.getEventType() == GameEvent.EventType.GOLD_SPAWN)
		{
			for (int i = 0; i < golds.size(); i++)
			{
				if (event.getId() == golds.get(i).getId())
				{
					golds.get(i).setX(event.getX());
					golds.get(i).setY(event.getY());
					found = true;
				}

			}
			if (!found)
			{
				Gold new_gold = new Gold(event.getX(), event.getY());
				new_gold.setId(event.getId());
				golds.add(new_gold);
			}
		} else // Player
		{
			for (int i = 0; i < players.size(); i++)
			{
				if (event.getId() == players.get(i).getId())
				{
					players.get(i).xDestination = event.getX();
					players.get(i).yDestination = event.getY();
					found = true;
				}
			}
			if (!found)
			{
				Player new_player = new Player(event.getX(), event.getY());
				new_player.setId(event.getId());
				players.add(new_player);
			}
		}

	}

	public void update(Player player)
	{
		boolean updated = false;
		for (int i = 0; i < players.size(); i++)
		{
			if (player.getId() == players.get(i).getId())
			{
				//System.out.println("i = " + i + "has equal ids of " + pl.get(i).getId());
				//Player updatePlayer = new Player(player2);
				Player current_player = players.get(i);

				if (current_player.xDestination != player.xDestination ||
					current_player.yDestination != player.yDestination)
				{
					players.get(i).xDestination = player.xDestination;
					players.get(i).yDestination = player.yDestination;

				}
				updated = true;
				//players.set(i, new Player(player));
				//System.out.println("Setting: " + pl.get(i));
			}
		}
		if (!updated)
		{
			players.add(player);
		}

	}

	public void update(ArrayList<Player> pl)
	{
		//Collections.copy(players, pl);
		//System.out.println("PLayers now has " + pl.size() + " elements");
		ArrayList<Player> playList = new ArrayList<Player>(pl);
		System.out.println("PlayList in m2m has " + playList.size() + "elements");
		players = pl;
		System.out.println(pl.get(0));
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,500,500);

		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).paintUnit(g);
		}
		for (int i = 0; i < golds.size(); i++)
		{
			golds.get(i).paintUnit(g);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}

	/*
	public void mousePressed(MouseEvent e)
	{
		//int x = e.getX();
		//int y = e.getY();
	}

	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	*/
}