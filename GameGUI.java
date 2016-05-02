
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
	private JPanel btnPanel;


	GameGUI(String host, int port)
	{
		String userName = "Default";
		GameGUI gui = this;
		thisPlayer = new Player();

		frame = new JFrame("Game client");
		defaultPort = port;
		defaultHost = host;
		//client = new Client(defaultHost, defaultPort, userName, this);
		btnPanel = new JPanel();



		JButton btnCreate = new JButton("Create account");
		JButton btnLogin = new JButton("Click to login");


		btnLogin.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				LoginDialog loginDlg = new LoginDialog(frame, host, port, gui);
				loginDlg.setVisible(true);
				// if logon successfully
			    if(loginDlg.isSucceeded()){
				//if(true){
				    //btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
				    //btnLogin.remove();
				    frame.getContentPane().remove(btnPanel);
				    client = loginDlg.getClient();
				    panel = new MoveToMouse(gui);

				    panel.addMouseListener(new MouseAdapter()
					{
						public void mouseClicked(MouseEvent e)
						{
							if (connected)
							{
								int x = e.getX();
								int y = e.getY();
								thisPlayer.setDestination(x, y);
								//client.sendData(thisPlayer);
								int tile = panel.getLocalTile();
								GameEvent event = new GameEvent(x, y, GameEvent.EventType.PLAYER_MOVEMENT, tile);
								client.sendData(event);
							}
						}
					});
					connected = client.start();
					panel.setId(client.getId());
					thisPlayer.setId(client.getId());
				    //panel = new MoveToMouse(gui);
				    frame.add(panel);
				    if (!connected)
				    {
						System.exit(0);
					}
				    frame.pack();
			    }
            }
		});

		btnCreate.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				CreateDialog createDlg = new CreateDialog(frame, host, port, gui);
				createDlg.setVisible(true);
				// if logon successfully
			    if(createDlg.isSucceeded()){
				//if(true){
				    //btnLogin.setText("Hi " + loginDlg.getUsername() + "!");
				    //btnLogin.remove();
				    frame.getContentPane().remove(btnPanel);
				    client = createDlg.getClient();
				    panel = new MoveToMouse(gui);

				    panel.addMouseListener(new MouseAdapter()
					{
						public void mouseClicked(MouseEvent e)
						{
							if (connected)
							{
								int x = e.getX();
								int y = e.getY();
								thisPlayer.setDestination(x, y);
								//client.sendData(thisPlayer);
								int tile = panel.getLocalTile();
								GameEvent event = new GameEvent(x, y, GameEvent.EventType.PLAYER_MOVEMENT, tile);
								client.sendData(event);
							}
						}
					});
					connected = client.start();
					panel.setId(client.getId());
					thisPlayer.setId(client.getId());		//JPanel panel = new JPanel();
				    //panel = new MoveToMouse(gui);		//panel = new MoveToMouse(this);
				    frame.add(panel);		//panel.setSize(500, 500);
				    if (!connected)
				    {
						System.exit(0);		//panel.setBackground(Color.BLUE);
					}
				    frame.pack();
			    }
            }		//frame.add(panel);
		});

		btnPanel.add(btnLogin);
		btnPanel.add(btnCreate);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(520, 520);
		frame.getContentPane().add(btnPanel);

		frame.pack();
		frame.setVisible(true);

		//frame.pack();
	}

	public void sendGoldTaken(Gold gold)
	{
		GameEvent gold_taken = new GameEvent(gold.getX(), gold.getY(), GameEvent.EventType.GOLD_TAKEN);
		gold_taken.setId(clientId);
		gold_taken.setGoldId(gold.getId());
		client.sendData(gold_taken);
	}

	public void sendBattleCommand(String command)
	{
		GameEvent event = new GameEvent(command);
		event.setEventType(GameEvent.EventType.BATTLE);
		client.sendData(event);
	}

	public void sendCombat(GameEvent e)
	{
		client.sendData(e);

	}

	public void sendNewTile(Player player, int tile)
	{
		GameEvent move_tile = new GameEvent(player.getX(), player.getY(),
										    GameEvent.EventType.PLAYER_MOVEMENT, tile);
		move_tile.setId(clientId);
		client.sendData(move_tile);
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

	public void append(GameState gs)
	{
		panel.update(gs);
	}

	public void append(String str)
	{
		System.out.println(str);
	}

	public void connectionFailed()
	{
		connected = false;
	}
	public static <T extends Container> T findParent(Component comp, Class<T> clazz)
	{
		if (comp == null)
		   return null;
		if (clazz.isInstance(comp))
		   return (clazz.cast(comp));
		else
		   return findParent(comp.getParent(), clazz);
   }


}