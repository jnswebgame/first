
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class GameGUI
{
	JFrame frame;
	private boolean connected;
	private Client client;
	private int defaultPort;
	private String defaultHost;
	private MoveToMouse panel;
	private Player thisPlayer;
	private int clientId = 0;


	GameGUI(String host, int port)
	{
		String userName = "Default";
		thisPlayer = new Player();

		frame = new JFrame("Game client");
		defaultPort = port;
		defaultHost = host;
		client = new Client(defaultHost, defaultPort, userName, this);

		//JPanel panel = new JPanel();
		panel = new MoveToMouse(this);


		//panel.setBackground(Color.BLUE);
		panel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				int x = e.getX();
				int y = e.getY();
				thisPlayer.setDestination(x, y);
				//client.sendData(thisPlayer);
				client.sendData(new GameEvent(x, y));
			}
		});


		frame.add(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);

		//frame.pack();
		client.start();
		panel.setId(client.getId());
		thisPlayer.setId(client.getId());
	}

	public void sendGoldTaken(Gold gold)
	{
		GameEvent gold_taken = new GameEvent(gold.getX(), gold.getY(), GameEvent.EventType.GOLD_TAKEN);
		gold_taken.setId(clientId);
		gold_taken.setGoldId(gold.getId());
		client.sendData(gold_taken);
	}

	public void setId(int id)
	{
		clientId = id;
		panel.setId(id);
	}

	public static void main(String[] args)
	{
		GameGUI game = new GameGUI("localhost", 3484);
	}

	public void append(Player player)
	{
		panel.update(player);
	}

	public void append(GameEvent event)
	{
		panel.update(event);
	}

	public void append(ArrayList<Player> pl)
	{
		ArrayList<Player> playList = new ArrayList<Player>(pl);
		panel.update(playList);
	}

	public void append(String str)
	{
		System.out.println(str);
	}

	public void connectionFailed()
	{
		connected = false;
	}


}