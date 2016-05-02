import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MoveToMouse extends JPanel implements ActionListener //, MouseListener
{
	public static final int PREF_HEIGHT = 500, PREF_WIDTH = 500;
	private static final int MARGIN = 20;
	private static final int EDGE_X = PREF_WIDTH - MARGIN, EDGE_Y = PREF_HEIGHT - MARGIN;
	private ArrayList<Player> players;
	private ArrayList<Gold> golds;
	private int localId = -1;
	Player localPlayer;
	GameGUI gui;
	DrawPanel draw_panel;
	int local_tile;
	Combat combat;

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
	public Player getLocalPlayer()
	{
		return localPlayer;
	}

	public MoveToMouse(GameGUI gui)
	{
		this.gui = gui;
		localPlayer = new Player();
		//players = new ArrayList<Player>();
		//golds = new ArrayList<Gold>();
		draw_panel = new DrawPanel(this);
		//addMouseListener(this);
		timer = new Timer(25, this);
		timer.start();
	}

	public void updateLocal(Player play)
	{
		localPlayer = play;
	}

	public void update(GameState gameState)
	{
		players = gameState.getPlayers();
		golds = gameState.getGolds();
		System.out.println("Golds size: " + golds.size());
		//repaint();
		setId(gameState.getClientId());
		for (int i = 0; i < players.size(); i++)
		{
			if (gameState.getClientId() == players.get(i).getId())
			{
				updateLocal(players.get(i));
				local_tile = players.get(i).getTile();
			}
			if (gameState.getPlayers().get(i).online)
			{
				players.get(i).online = true;
			}
		}
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
		} else if (event.getEventType() == GameEvent.EventType.INITIATE_COMBAT)
		{
			int player_one_id = event.getId();
			int player_two_id = event.enemy_id;
			Player p1 = new Player(), p2 = new Player();
			for( int i = 0; i < players.size(); i++)
			{
				if (players.get(i).getId() == player_one_id)
				{
					players.get(i).setTile(event.getTile());
					p1 = players.get(i);
				}
				if (players.get(i).getId() == player_two_id)
				{
					players.get(i).setTile(event.getTile());
					p2 = players.get(i);
				}
			}
			//combat = new Combat(p1, p2);
		} else if (event.getEventType() == GameEvent.EventType.BATTLE)
		{
			if (localPlayer.getTile() < 4)
			{
				return;
			}

			Player enemy_player;

			for (int i = 0; i < players.size(); i++)
			{
				if (players.get(i).getId() == event.enemy_id)
				{
					 enemy_player = players.get(i);
					 if (event.command.equals("attack"))
					 {
					 	draw_panel.updateBattle(event, localPlayer, enemy_player);
					 }
				}


			}

		} else if (event.getEventType() == GameEvent.EventType.OFFLINE)
		{
			for (int i = 0; i < players.size(); i++)
			{
				if (event.getId() == players.get(i).getId())
				{
					players.get(i).online = false;
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
					players.get(i).online = true;
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
		local_tile = localPlayer.getTile();

		//g.setColor(Color.white);
		//g.fillRect(0,0,500,500);


		// This is where we will call panels to draw themselves,
		// based on where current player is.
		//setBackground(Color.WHITE);
		draw_panel.drawPanel(local_tile, g);
		g.setColor(Color.YELLOW);
		g.drawString("Gold: " + localPlayer.getGold(), 230, 480);

		g.drawString("HP: " + localPlayer.getHP(), 400, 380);
		g.drawString("ATK: " + localPlayer.getAtk(), 400, 395);
		g.drawString("DEF: " + localPlayer.getDef(), 400, 410);
		g.drawString("AGI: " + localPlayer.getAgi(), 400, 425);
		g.drawString("Name: " + localPlayer.getName(), 50, 450);
		if (local_tile > 3)
		{
			return;
		}

		for (int i = 0; i < players.size(); i++)
		{
			Player temp_player = players.get(i);
			if (temp_player.getTile() == local_tile && players.get(i).online)
			{
				temp_player.paintUnit(g, this);
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

	public void sendBattleCommand(String command)
	{
		gui.sendBattleCommand(command);

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

	public void checkCombat()
	{
		Rectangle current_player = localPlayer.getBounds();
		if (localPlayer.getTile() == 0)
		{
			return;
		}
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getId() == localId || !players.get(i).online || players.get(i).tile == 0)
			{
				continue;
			}


			Rectangle enemy_player = players.get(i).getBounds();
			if (current_player.intersects(enemy_player))
			{
				GameEvent initiate_combat = new GameEvent(GameEvent.EventType.INITIATE_COMBAT);
				initiate_combat.setId(localId);
				initiate_combat.enemy_id = players.get(i).getId();
				gui.sendCombat(initiate_combat);
			}


		}
	}

	public void actionPerformed(ActionEvent e)
	{
		checkGold();
		checkEdges();
		checkCombat();
		repaint();
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(PREF_WIDTH, PREF_HEIGHT);
	}
}
