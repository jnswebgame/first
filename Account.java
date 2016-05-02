
import java.io.*;


public class Account implements Serializable
{
	private String name;
	private String password;
	Player player;

	public Account(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	public void setPlayer(Player p)
	{
		player = p;
	}
	public Player getPlayer(Player p)
	{
		return player;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void getPassword(String password)
	{
		this.password = password;
	}
	public String getName()
	{
		return name;
	}
	public String getPassword()
	{
		return password;
	}
}