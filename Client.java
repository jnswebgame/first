
import java.net.*;
import java.io.*;
import java.util.*;


public class Client
{
	Scanner scan = new Scanner(System.in);
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;
	private ArrayList<Player> pl;

	private GameGUI gg;
	private String server, userName;
	private int port;
	private int clientId;
	private GameState gameState;

	public String getUsername() { return userName; }

	Client(String server, int port, String userName)
	{
		this(server, port, userName, null);
	}

	Client(String server, int port, String userName, GameGUI gg)
	{
		this.server = server;
		this.port = port;
		this.userName = userName;
		this.gg = gg;
	}

	public boolean start()
	{
		try
		{
			socket = new Socket(server, port);
		} catch (Exception e)
		{
			display("Error connecting");
			return false;
		}
		display("Connection accepted");
		try
		{
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e)
		{
			display("Exception creating io streams");
			return false;
		}



		try
		{
			// Send the server your username, it will send back your client id
			oos.writeObject(userName);
			// Game state here
			//scan.nextInt();
			gameState = (GameState) ois.readObject();
			clientId = gameState.getClientId();
			Player new_player = new Player();
			new_player.setId(clientId);
			gg.setId(clientId);



			gg.append(gameState);
		} catch (Exception e)
		{
			display("Error writing name");
			disconnect();
			return false;
		}
		new ListenToServer().start();
		return true;
	}

	private void display(String message)
	{
		if (gg == null)
		{
			System.out.println(message);
		} else
		{
			gg.append(message + '\n');
		}
	}

	void sendData(Player player)
	{
		try
		{
			player.setId(clientId);

			oos.writeUnshared(player);

		} catch (Exception e)
		{
			display("Error writing to server");
		}
	}



	void sendData(GameEvent event)
	{
		try
		{
			if (event.getId() <= 0)
			{
				event.setId(clientId);
			}
			oos.writeUnshared(event);
		} catch (Exception ex)
		{
			display("Error writing event to server");
		}
	}

	private void disconnect()
	{
		try
		{
			if (ois != null) ois.close();
			if (oos != null) oos.close();
			if (socket != null) socket.close();
		} catch (Exception e) {}

		if (gg != null)
		{
			gg.connectionFailed();
		}
	}

	public int getId()
	{
		return clientId;
	}

	class ListenToServer extends Thread
	{
		public void run()
		{
			while (true)
			{
				try
				{
					Object o = ois.readObject();
					//Player newPlayer = (Player) ois.readObject();
					//gg.append(newPlayer);
					if (o instanceof Player)
					{
						Player newPlayer = (Player)o;
						gg.append(newPlayer);
					} else if (o instanceof GameEvent)
					{
						GameEvent event = (GameEvent)o;
						gg.append(event);
					} else if (o instanceof Integer)
					{
						System.out.println("We got an int!");
					}
				} catch (Exception e)
				{
					display("Server has to close connection");
					if (gg != null)
					{
						gg.connectionFailed();
					}
					break;
				}
			}
		}
	}
}