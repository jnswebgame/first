

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class CreateDialog extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JLabel lbAgility;
    private JLabel lbAttack;
    private JLabel lbDefense;
    private JLabel lbHealth;
    private JTextField tfAttack;
    private JTextField tfDefense;
    private JTextField tfHealth;
    private JTextField tfAgility;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
    private Client client;

    public CreateDialog(Frame parent, String host, int port, GameGUI gui) {
        super(parent, "Login", true);
        //
        Login login = new Login();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);

        lbAgility = new JLabel("Agility: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbAgility, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

  		tfAgility = new JTextField(20);
  		tfAgility.setText("0");
  		cs.gridx = 1;
  		cs.gridy = 2;
  		cs.gridwidth = 2;
  		panel.add(tfAgility, cs);

  		lbAttack = new JLabel("Attack: ");
  		cs.gridx = 0;
  		cs.gridy = 3;
  		cs.gridwidth = 1;
  		panel.add(lbAttack, cs);

  		tfAttack = new JTextField(20);
  		tfAttack.setText("0");
  		cs.gridx = 1;
  		cs.gridy = 3;
  		cs.gridwidth = 2;
  		panel.add(tfAttack, cs);

  		lbDefense = new JLabel("Defense: ");
  		cs.gridx = 0;
  		cs.gridy = 4;
  		cs.gridwidth = 1;
  		panel.add(lbDefense, cs);

  		tfDefense = new JTextField(20);
  		tfDefense.setText("0");
  		cs.gridx = 1;
  		cs.gridy = 4;
  		cs.gridwidth = 2;
  		panel.add(tfDefense, cs);

  		lbHealth = new JLabel("Health(HP): ");
  		cs.gridx = 0;
  		cs.gridy = 5;
  		cs.gridwidth = 1;
  		panel.add(lbHealth, cs);

  		tfHealth = new JTextField(20);
  		tfHealth.setText("0");
  		cs.gridx = 1;
  		cs.gridy = 5;
  		cs.gridwidth = 2;
  		panel.add(tfHealth, cs);

        btnLogin = new JButton("Login");

        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
				if (getTotalValue() != 18)
				{
					JOptionPane.showMessageDialog(CreateDialog.this,
							"Your stats must add up to 18!", "Total",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Account account = new Account(getUsername(), getPassword());
				account.setPlayer(getPlayer());

                if (login.saveAccount(account)) {
                    JOptionPane.showMessageDialog(CreateDialog.this,
                            "Hi " + getUsername() + "! You have successfully signed up!",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    client = new Client(host, port, getUsername(), gui);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CreateDialog.this,
                            "Username taken, try again!",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;

                }
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

	public int getTotalValue()
	{
		int total = 0;
		total += Integer.parseInt(tfAgility.getText().trim());
		total += Integer.parseInt(tfAttack.getText().trim());
		total += Integer.parseInt(tfDefense.getText().trim());
		total += Integer.parseInt(tfHealth.getText().trim());

		return total;

	}
	public Player getPlayer()
	{
		Player play = new Player();
		play.setAGI(Integer.parseInt(tfAgility.getText().trim()));
		play.setATK(Integer.parseInt(tfAttack.getText().trim()));
		play.setDEF(Integer.parseInt(tfDefense.getText().trim()));
		play.setHP(Integer.parseInt(tfHealth.getText().trim()));


		return play;
	}
    public String getUsername() {
        return tfUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Client getClient()
    {
		return client;
	}
}