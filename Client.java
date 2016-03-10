
import java.net.*;
import java.io.*;
import java.util.*;


public class Client
{
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket socket;

	private GameGUI gg;
	private String server, userName;
	private int port;

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

		new ListenToServer().start();

		try
		{
			oos.writeObject(userName);
		} catch (Exception e)
		{
			display("Error writing name");
			disconnect();
			return false;
		}
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
			oos.writeObject(player);
		} catch (Exception e)
		{
			display("Error writing to server");
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

	class ListenToServer extends Thread
	{
		public void run()
		{
			while (true)
			{
				try
				{
					Player play = (Player) ois.readObject();

					if (gg == null)
					{
						String message = "X = " + play.getX() + " Y = " + play.getY();
					} else
					{
						gg.append(play);
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