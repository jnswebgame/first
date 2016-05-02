


public class SaveAccounts
{

	public static void main(String[] args)
	{
		Login login = new Login();
		Account account = new Account("Jonah", "jonah");
		Player play = new Player();
		play.setAGI(6);
		play.setATK(6);
		play.setHP(20);
		play.setDEF(6);
		account.setPlayer(play);
		login.saveAccount(account);

	}

}