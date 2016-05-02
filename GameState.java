

import java.io.*;
import java.util.*;

public class GameState implements Serializable
{
	private ArrayList<Player> players;
	private ArrayList<Gold> golds;
	private ArrayList<Account> accounts;
	private int currentId;
	private int clientId;
	private int total_gold;
	boolean [] goldIds = { false, false, false, false, false};

	public GameState()
	{
		players = new ArrayList<Player>();
		golds = new ArrayList<Gold>();
		currentId = 0;
	}
	public void setGolds(ArrayList<Gold> gold)
	{
		golds = gold;
	}

	public void removeFromGolds(Gold gold)
	{
		for(int i = 0; i < golds.size(); i++)
		{
			if (gold.getId() == golds.get(i).getId())
			{
				golds.remove(i);
			}
		}
	}
	public void setTotalGold(int gold)
	{
		total_gold = gold;
	}
	public int getTotalGold()
	{
		return total_gold;
	}
	public void addToPlayers(Player p)
	{
		players.add(p);
	}
	public void setPlayers(ArrayList<Player> p)
	{
		players = p;
	}
	public void addToGolds(Gold g)
	{
		golds.add(g);
	}
	public void setId(int id)
	{
		currentId = id;
	}
	public void setClientId(int id)
	{
		clientId = id;
	}
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	public ArrayList<Gold> getGolds()
	{
		return golds;
	}
	public int getId()
	{
		return currentId;
	}
	public int getClientId()
	{
		return clientId;
	}



}