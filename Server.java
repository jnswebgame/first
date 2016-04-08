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
        //private ServerGUI serverGui;

        private int port;
        private boolean keepRunning;

        Random random = new Random();
        private Timer timer;

        public Server(int port)
        {
            this.port = port;
            al = new ArrayList<ClientThread>();
            pl = new ArrayList<Player>();
            clientId = 0;
            total_gold = 0;
        }

        public void start()
        {
            timer = new Timer(30000, this);
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
            clientId++;
            id = clientId;
            this.sock = sock;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                ois = new ObjectInputStream(sock.getInputStream());
                userName = (String) ois.readObject();
                display(userName + " has connected");
                oos.writeObject(id);
                GameEvent event = new GameEvent(50, 50);
                event.setId(id);
                sendData(event);
            } catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }

        public void run()
        {
            boolean updated = false;
            Player player2;
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
						sendData(event);
					}
				} catch (Exception e)
				{
					display("A player has Disconnected!");
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
    public void actionPerformed(ActionEvent e)
    {
		if (total_gold < 5)
		{
        	int x = random.nextInt(501);
        	int y = random.nextInt(501);
        	GameEvent goldSpawn = new GameEvent(x, y, GameEvent.EventType.GOLD_SPAWN);
        	goldSpawn.setId(++total_gold);
        	sendData(goldSpawn);
		}
    }
}


