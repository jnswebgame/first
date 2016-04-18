import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MoveToMouse extends JPanel implements ActionListener //, MouseListener
{
	private static final int PREF_HEIGHT = 500, PREF_WIDTH = 500;
	private static final int MARGIN = 20;
	private static final int EDGE_X = PREF_WIDTH - MARGIN, EDGE_Y = PREF_HEIGHT - MARGIN;
	private ArrayList<Player> players;
	private ArrayList<Gold> golds;
	private int localId = -1;
	Player localPlayer;
	GameGUI gui;
	DrawPanel draw_panel;

	public int velocity = 7;

	public Timer timer;

	public void setId(int id)
	{
		localId = id;
	}
	public int getLocalTile()
	{
		return localPlayer.getTile();
	}

	public MoveToMouse(GameGUI gui)
	{
		this.gui = gui;
		localPlayer = new Player();
		players = new ArrayList<Player>();
		golds = new ArrayList<Gold>();
		draw_panel = new DrawPanel(this);
		//addMouseListener(this);
		timer = new Timer(25, this);
		timer.start();
	}

	public void updateLocal(Player play)
	{
		localPlayer = play;
	}

	public void update(GameEvent event)
	{
		boolean found = false;

		if (event.getEventType() == GameEvent.EventType.GOLD_TAKEN)
		{
			for (int i = 0; i < golds.size(); i++)
			{
				if (event.getGoldId() == golds.get(i).getId())
				{
					golds.remove(i);
					break;
				}
			}
		} else if (event.getEventType() == GameEvent.EventType.GOLD_SPAWN)
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
					if (players.get(i).getId() == localId)
					{
						localPlayer = players.get(i);
					}
					players.get(i).xDestination = event.getX();
					players.get(i).yDestination = event.getY();
					int old_tile = players.get(i).getTile();
					int new_tile = event.getTile();
					if (old_tile != new_tile)
					{
						players.get(i).setX(event.getX());
						players.get(i).setY(event.getY());
						players.get(i).setTile(event.getTile());
					}

					found = true;
				}
			}
			if (!found)
			{
				Player new_player = new Player(event.getX(), event.getY());
				new_player.setId(event.getId());
				new_player.setTile(0);
				if (event.getId() == localId)
				{
					localPlayer = new_player;
				}
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

				if (current_player.getId() == localId)
				{
					localPlayer = current_player;
				}

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
		super.paintComponent(g);
		int local_tile = localPlayer.getTile();

		//g.setColor(Color.white);
		//g.fillRect(0,0,500,500);


		// This is where we will call panels to draw themselves,
		// based on where current player is.
		//setBackground(Color.WHITE);
		draw_panel.drawPanel(local_tile, g);


		for (int i = 0; i < players.size(); i++)
		{
			Player temp_player = players.get(i);
			if (temp_player.getTile() == local_tile)
			{
				temp_player.paintUnit(g);
			}
		}
		for (int i = 0; i < golds.size(); i++)
		{
			Gold temp_gold = golds.get(i);
			if (temp_gold.getTile() == local_tile)
			{
				temp_gold.paintUnit(g);
			}
		}
	}

	public void checkGold()
	{
		Rectangle current_player = localPlayer.getBounds();

		for (int i = 0; i < golds.size(); i++)
		{
			Rectangle gold_rect = golds.get(i).getBounds();
			if (current_player.intersects(gold_rect))
			{
				localPlayer.addGold(1);
				gui.sendGoldTaken(golds.get(i));
			}
		}
	}

	public void checkEdges()
	{
		int local_x = localPlayer.getX();
		int local_y = localPlayer.getY();
		int local_tile = localPlayer.getTile();
		Player temp_player = localPlayer;

		if (local_x > EDGE_X)
		{
			if (local_tile == 0)
			{
				temp_player.setX(22);
				gui.sendNewTile(temp_player, 1);
			} else if (local_tile == 2)
			{
				temp_player.setX(22);
				gui.sendNewTile(temp_player, 3);
			}

		}
		if (local_x < MARGIN)
		{
			if (local_tile == 1)
			{
				temp_player.setX(478);
				gui.sendNewTile(temp_player, 0);
			} else if (local_tile == 3)
			{
				temp_player.setX(478);
				gui.sendNewTile(temp_player, 2);
			}
		}
		if (local_y > EDGE_Y)
		{
			if (local_tile == 0)
			{
				temp_player.setY(22);
				gui.sendNewTile(temp_player, 2);
			} else if (local_tile == 1)
			{
				temp_player.setY(22);
				gui.sendNewTile(temp_player, 3);
			}
		}
		if (local_y < MARGIN)
		{
			if (local_tile == 2)
			{
				temp_player.setY(478);
				gui.sendNewTile(temp_player, 0);
			} else if (local_tile == 3)
			{
				temp_player.setY(478);
				gui.sendNewTile(temp_player, 1);
			}
		}


	}

	public void actionPerformed(ActionEvent e)
	{
		checkGold();
		checkEdges();
		repaint();
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(PREF_WIDTH, PREF_HEIGHT);
	}
}
