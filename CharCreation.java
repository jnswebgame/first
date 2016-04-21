import java.util.*;

public class CharCreation 
{
	private int points = 18;
	private int atk, def, agility =0;
	private int allStats = 0;
	
	private Scanner scan = new Scanner(System.in);
	
	public static void main(String args[])
	{
		CharCreation cc = new CharCreation();
		cc.entry();
	}
	
	public CharCreation()
	{
 
//		atk = input;
	//	points -= input;
		//System.out.println("Enter your desired defense (");
		/*()
		input box atk;
		get the stuff in the atk box (parse string)
		points-= inputPoints
		change points label to points
		*/
	}
	private void entry()
	{
		System.out.println("Enter the stat you want to change: ");
		String inStat = scan.next().toLowerCase();
		System.out.println("Enter the number that you want: ");
		int statChoice = scan.nextInt();
		reducePoints(statChoice, inStat);
		
	}

	private void reducePoints(int i, String stat)
	{
		
		int input = i;
		int result =0;
		if(input<=points)
		{
			points -= input;		
			switch(stat) 
			{
				case "attack" : setAttack(i); result=getAttack(); allStats++;
				case "defense" : setDefense(i); result = getDefense();allStats++;
				case "agility" : setAgility(i);	result = getAgility();allStats++;
			}
			if(allStats<4)
			System.out.println( "Your "+stat+"stat is now " + result + ". You now have "+points+" points left");
			
			
		}
		
		if(input>points)
		{
			System.out.println("Sorry, you don't have enough points for that. Enter less");			
			entry();
		}		
	}
	private void setAgility (int i)
	{
		agility= i;
	}
	private void setDefense (int i)
	{
		def= i;
	}
	private void setAttack (int i)
	{
		atk=i ;
	}
	private int getAttack()
	{
		return atk;
	}
	private int getDefense()
	{
		return def;
	}
	private int getAgility()
	{
		return agility;
	}
}
