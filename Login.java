

import java.io.*;
import java.util.*;

public class Login
{
	static private ArrayList<Account> accounts;

	public Login()
	{
		try
		{
			load();
		} catch (Exception e)
		{
			accounts = new ArrayList<Account>();
		}
	}

	public boolean saveAccount(Account account)
	{
		for (int i = 0; i < accounts.size(); i++)
		{

			if (accounts.get(i).getName().equals(account.getName()))
			{
				//System.out.println("Double");
				//System.out.println("accounts is: " + accounts.size());
				return false;
			}
		}
		accounts.add(account);
		try
		{
			save();
		} catch(Exception e)
		{
		}
		return true;
	}


    public static boolean authenticate(String username, String password)
    {
        for (int i = 0; i < accounts.size(); i++)
        {
			if ( username.equals (accounts.get(i).getName()) && password.equals(accounts.get(i).getPassword()) )
			{
				return true;
			}

		}
		return false;
    }


    public static void save() throws FileNotFoundException
    {
        try
        {
            FileOutputStream fout = new FileOutputStream("Accounts.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeUnshared(accounts);
            fout.close();
        }catch(Exception e){
            //e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() throws Exception
    {
        try
        {
            FileInputStream fin = new FileInputStream("Accounts.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);
            ArrayList<Account> al = (ArrayList<Account>)ois.readObject();
            if (al.size() > 0)
            {
				accounts = al;
			} else {
				accounts = new ArrayList<Account>();
			}
            ois.close();
        }catch(Exception e){
            //e.printStackTrace();
            throw e;
            //JOptionPane.showMessageDialog(
        }

    }

}