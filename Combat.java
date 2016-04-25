import java.util.Random;

public class Combat 
{
	private Random random;
	private boolean end;
	private Player turnPlayer;
	private Player opponent;
	public Combat(Player turn, Player op)
	{		
		newBattle(turn, op);
	}
	
	public Combat(){}
	
	public void newBattle(Player p1, Player p2)
	{
		if(p1.getAgi()>p2.getAgi())	
			newTurn(p1, p2);
		if(p2.getAgi()>p1.getAgi())
			newTurn(p2, p1);
		if (p2.getAgi()==p1.getAgi())
		{
			if(random.nextInt(2)==0)
				newTurn(p1, p2);
			else
				newTurn(p2,p1);
		}
	}

	public void newTurn(Player turn, Player op)
	{		
		turnPlayer=turn;
		opponent = op;
	}
	 
	private void action()
	{
		String choice="";
		if(choice.equals("attack"))
		{			
			damage(turnPlayer, opponent);
		}		
		if(choice.equals("cash"))
		{
			//subtracts gold, 
			//ends the battl
			cash();
		}
	}
	
	private boolean dodge()
	{
		boolean missed = false;
		int evasion = opponent.getAgi() - turnPlayer.getAgi();
		if(evasion>0)
		{			
			if(evasion >= random.nextInt(10))
				missed=true;
		}
		return missed;
	}
	
	private void damage(Player p1, Player p2)
	{
		if(!dodge())
		{
			int p1atk = p1.getAtk();
			int p2def = p2.getDef();
			int p2HP = p2.getHP();		
			int damage = (random.nextInt(4)+1+(p1atk * 2)) * criticalHit() -p2def;
			
			if(damage>0)
				p2.setHP(p2HP-damage);
			if(damage<=0)
				//return "missed", "No damage" or something.
			
			if(p2.getHP() <=0)
			{
				end=true;
				death(opponent);
			}
		}
		if(dodge())
		{
			newTurn(opponent, turnPlayer);
		}
	}
	private int criticalHit()
	{
		int ret = 1;
		int chance =  turnPlayer.getAgi() - opponent.getAgi();
		if(chance>0)
		{			
			if(chance >= random.nextInt(10))
				ret = 2;
		}
		return ret;
	}
	private void cash()
	{
		int gold = turnPlayer.getGold();
		//throw how much:
		//show how much must be thrown:
		int thrownRequired = opponent.getDef();
		if(thrownRequired>gold)
		{
			//you can't do that
		}
		else
		{
			turnPlayer.setGold(gold-thrownRequired);
			end = true;
		}
		
	}
	private void death(Player deadPlayer)
	{
		deadPlayer.setPosition(10, 10);
		end=true;
	}
	
}
