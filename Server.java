//Simple server that takes in some ints. Simple because it can be fleshed out
//further once the client is written and made to work with it.
//
//After much frustration I ended up just ripping off much of the code from the Chat
//program
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;


    public class Server implements ActionListener
    {
        private static int clientId, total_gold;
        private ArrayList<ClientThread> al;
        private ArrayList<Player> pl;
        private ArrayList<Gold> golds;
        private int battle_scene = 4;
        boolean [] gold_ids = { false, false, false, false, false};
        int MAX_GOLD = 5;
        //private ServerGUI serverGui;

        private int port;
        private boolean keepRunning;

        Random random = new Random();
        private Timer timer;
        private GameState gameState;

        public Server(int port)
        {
            this.port = port;
            al = new ArrayList<ClientThread>();
            //pl = new ArrayList<Player>();
            total_gold = 0;
            try
            {
            	loadGameState();
			} catch (Exception e)
			{
				gameState = new GameState();
			}
            clientId = gameState.getId();
            if (clientId > 0)
            {
				pl = gameState.getPlayers();
				//System.out.println("Size of pl: " + pl.size());
				if (pl.size() == 0)
				{
					pl = new ArrayList<Player>();
				}
				for (int i = 0; i < pl.size(); i++)
				{
					pl.get(i).online = false;
					pl.get(i).setATK(6);
					pl.get(i).setDEF(6);
					pl.get(i).setAGI(6);
					pl.get(i).setHP(20);
					if (pl.get(i).getTile() > 3)
					{
						pl.get(i).setTile(0);

					}
				}
				/*
				golds = gameState.getGolds();
				if (golds.size() == 0)
				{
					golds = new ArrayList<Gold>();
				} else
				{
					total_gold = gameState.getTotalGold();
					for (int i = 0; i < MAX_GOLD; i++)
					{
						gold_ids[i] = gameState.goldIds[i];
					}
				}*/
				//pl = new ArrayList<Player>();
				//golds = new ArrayList<Gold>();
				//golds = gameState.getGolds();
			} else
			{
				pl = new ArrayList<Player>();
				golds = new ArrayList<Gold>();
			}
			golds = new ArrayList<Gold>();
        }

        public void loadGameState() throws Exception
        {
			GameState gs;
			try
			{
				FileInputStream fin = new FileInputStream("State.dat");
				ObjectInputStream ois = new ObjectInputStream(fin);
				gs = (GameState)ois.readObject();
            	ois.close();
			} catch( Exception e)
			{
				//gameState = new GameState();
				throw e;
			}
			gameState = gs;
		}

 	   public static void saveGameState(GameState gs) throws FileNotFoundException
 	   {
 	       try
 	       {
 	           FileOutputStream fout = new FileOutputStream("State.dat");
 	           ObjectOutputStream oos = new ObjectOutputStream(fout);
 	           oos.writeUnshared(gs);
 	           fout.close();
       	   }catch(Exception e){
               e.printStackTrace();
           }
    	}

        public void start()
        {
            timer = new Timer(10000, this);
            timer.start();
            keepRunning = true;
            try
            {
                ServerSocket ss = new ServerSocket(port);
                while(keepRunning)
                {
                    Socket socket = ss.accept();
                    if (!keepRunning)
                    {
                        break;
                    }
                    ClientThread t = new ClientThread(socket);
                    al.add(t);
                    t.start();
                }
            try
            {
                ss.close();
                for(int i = 0; i < al.size(); i++)
                {
                    ClientThread ct = al.get(i);
                    try
                    {
                        ct.ois.close();
                        ct.oos.close();
                        ct.sock.close();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    } // end of start


    public static void main(String[] args)
    {
        int portNum = 3484;
        switch(args.length)
        {
            case 1:
                try
                {
                    portNum = Integer.parseInt(args[0]);
                } catch (Exception e)
                {
                    System.out.println("Invalid port num");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Invalid usage");
                return;
        }
        Server server = new Server(portNum);
        server.start();
    }

    private void stop()
    {
        keepRunning = false;
    }

    private synchronized void sendData(GameEvent event)
    {
		for (int i = al.size() - 1; i >= 0; i--)
		{
			ClientThread ct = al.get(i);
			if (!ct.writeData(event))
			{
				al.remove(i);
				display("A user has disconnected");
			}
		}

	}

    private synchronized void sendData(Player player)
    {
		for (int i = al.size() - 1; i >= 0; i--)
		{
			ClientThread ct = al.get(i);
			if (!ct.writeData(player))
			{
				al.remove(i);
				display("A user has disconnected");
			}
		}

	}


    private void display(String message)
    {
		System.out.println(message);
	}

	synchronized void remove(int id)
	{
		for (int i = 0; i < al.size(); i++)
		{
			ClientThread ct = al.get(i);
			if (ct.id == id)
			{
				al.remove(i);
				return;
			}
		}
	}

	public int checkPlayers(String name)
	{
		boolean found = false;
		int id = 0;
		for (int i = 0; i < pl.size(); i++)
		{
			if (pl.get(i).getName().equals(name))
			{
				System.out.println("Name = " + name);
				id = pl.get(i).getId();
				pl.get(i).online = true;
				found = true;
			}

		}
		if (!found)
		{
			id = gameState.getId();
			if (id == 0)
			{
				id = 1;
			}
			Player player = new Player(name, id);
			player.online = true;
			pl.add(player);
			gameState.setId(id + 1);
			gameState.addToPlayers(player);
			//gameState.setPlayers(pl);
		}
		return id;
	}

    class ClientThread extends Thread
    {
        Socket sock;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        int id;

        String userName;
        String date;

        ClientThread(Socket sock)
        {
            //clientId++;
            //id = clientId;
            this.sock = sock;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                ois = new ObjectInputStream(sock.getInputStream());
                userName = (String) ois.readObject();

                id = checkPlayers(userName);
                gameState.setClientId(id);
                System.out.println("Id = " + id);

                display(userName + " has connected");

                // Game state here
                oos.writeUnshared(gameState);
                saveGameState(gameState);

                GameEvent event = new GameEvent(50, 50);
                event.setId(id);
                sendData(event);
            } catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }

        public void updatePlayer(GameEvent event)
        {
			for (int i = 0; i < pl.size(); i++)
			{
				if (pl.get(i).getId() == event.getId())
				{
					pl.get(i).setX(event.getX());
					pl.get(i).setY(event.getY());
					pl.get(i).xDestination = event.getX();
					pl.get(i).yDestination = event.getY();
					pl.get(i).setTile(event.getTile());
				}
			}
			try
			{
				saveGameState(gameState);
			} catch (Exception e)
			{
				System.out.println("Error saving game state on movement update");
			}

		}

		public int getNextBattleScene()
		{
			return battle_scene;
		}


        public void run()
        {
            boolean updated = false;
            Player player2;
            int thisId = 0;
            while(true)
            {
				try
				{
					//player2 = (Player) ois.readObject();
					//player2.setId(id);
					//sendData(player2);
					Object object = ois.readObject();
					if (object instanceof Player)
					{
						Player play = (Player)object;
						play.setId(id);
						sendData(play);
					} else if (object instanceof GameEvent)
					{
						GameEvent event = (GameEvent)object;
						//event.setId(id);
						if (event.getEventType() == GameEvent.EventType.GOLD_TAKEN)
						{
							removeGold(event);
						} else if (event.getEventType() == GameEvent.EventType.PLAYER_MOVEMENT)
						{
							thisId = event.getId();
							updatePlayer(event);
						} else if (event.getEventType() == GameEvent.EventType.INITIATE_COMBAT)
						{
							int new_tile = getNextBattleScene();
							event.setTile(new_tile);
						}
						sendData(event);
					}
				} catch (Exception e)
				{
					//display("A player has Disconnected!");
					for (int i = 0; i < pl.size(); i++)
					{
						if (pl.get(i).getId() == thisId)
						{
							pl.get(i).online = false;
							display(pl.get(i).getName() + " has gone offline");
							GameEvent offline_event = new GameEvent(GameEvent.EventType.OFFLINE);
							offline_event.setId( pl.get(i).getId());
							sendData(offline_event);
						}
					}
					break;
				}


			}
			remove(id);
			close();
        }

        private void close()
        {
            try
            {
                if (oos != null) oos.close();
                if (ois != null) ois.close();
                if (sock != null) sock.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        private boolean writeData(GameEvent e)
        {
			if (!sock.isConnected())
			{
				close();
				return false;
			}
			try
			{
				oos.writeUnshared(e);
			} catch (Exception ex)
			{
				display("Error sending event");
				//return false;
			}
			return true;

		}

        private boolean writeData(Player player)
        {
			if (!sock.isConnected())
			{
				close();
				return false;
			}
			try
			{
				// Write unshared so it sends the correct object, without
				// unshared it see's that it's already send that object once
				// and doesn't send it again
				oos.writeUnshared(player);
			} catch (Exception e)
			{
				display("Error sending info");
			}
			return true;
		}
    }

    public void removeGold(GameEvent e)
    {
		int remove_id = e.getGoldId();
		int player_id = e.getId();
		for (int i = 0; i < pl.size(); i++)
		{
			if (pl.get(i).getId() == player_id)
			{
				pl.get(i).setGold(pl.get(i).getGold()+1);
			}
		}
		total_gold--;
		gold_ids[remove_id-1] = false;
		for (int i = 0; i < golds.size(); i++)
		{
			if (e.getGoldId() == golds.get(i).getId())
			{
				gameState.removeFromGolds(golds.get(i));
				golds.remove(i);
				break;
			}
		}
		try
		{
			saveGameState(gameState);
		} catch (Exception ex)
		{
		}
	}

    public void actionPerformed(ActionEvent e)
    {
		if (total_gold < 5)
		{
        	int x = random.nextInt(501);
        	int y = random.nextInt(501);
        	int p = random.nextInt(3);
        	p += 2;
        	GameEvent goldSpawn = new GameEvent(x, y, GameEvent.EventType.GOLD_SPAWN, p);
        	total_gold++;
        	for (int i = 0; i < MAX_GOLD; i++)
        	{
				if (!gold_ids[i])
				{
					goldSpawn.setId(i+1);
					gold_ids[i] = true;
					Gold new_gold = new Gold(x, y);
					new_gold.setTile(p);
					new_gold.setId(i+1);
					golds.add(new_gold);
					gameState.addToGolds(new_gold);
					break;
				}
			}
        	//goldSpawn.setId(++total_gold);
        	sendData(goldSpawn);
		}
    }
}


