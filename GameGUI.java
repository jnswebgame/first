
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameGUI
{
	JFrame frame;
	private boolean connected;
	private Client client;
	private int defaultPort;
	private String defaultHost;


	GameGUI(String host, int port)
	{
		String userName = "Default";

		frame = new JFrame("Game client");
		defaultPort = port;
		defaultHost = host;
		client = new Client(defaultHost, defaultPort, userName, this);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				int x = e.getX();
				int y = e.getY();
				client.sendData(new Player(x, y));
			}
		});


		frame.add(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setVisible(true);

		client.start();
	}

	public static void main(String[] args)
	{
		GameGUI game = new GameGUI("localhost", 3484);
	}

	public void append(Player play)
	{

	}

	public void append(String str)
	{
		System.out.println(str);
	}

	public void connectionFailed()
	{

	}


}