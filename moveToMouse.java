import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class moveToMouse extends JPanel implements MouseListener, ActionListener
{
	public int xCurrent = 50;
	public int yCurrent = 50;
	public int xDestination = 50;
	public int yDestination = 50;
	public int xDistance;
	public int yDistance;
	public int xVel;
	public int yVel;

	public int velocity = 7;

	public Timer timer;

	public moveToMouse()
	{
		addMouseListener(this);
		timer = new Timer(200, this);
		timer.start();
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		moveToMouse panel = new moveToMouse();
		frame.add(panel);
		frame.setVisible(true);
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,500,500);
		g.setColor(Color.black);
		g.fillOval(xCurrent-10, yCurrent-10, 20, 20);
	}

	public void actionPerformed(ActionEvent e)
	{
		//movement logic
		xDistance = (xDestination - xCurrent);
		yDistance = (yDestination - yCurrent);
		if(xDistance == 0 && yDistance == 0)
		{
			xVel = 0;
			yVel = 0;
		}
		else
		{
			xVel = (int)((velocity * xDistance) / (Math.abs(xDistance) + Math.abs(yDistance)));
			yVel = (int)((velocity * yDistance) / (Math.abs(xDistance) + Math.abs(yDistance)));
		}
		
		xCurrent += xVel;
		yCurrent += yVel;
		/*
		System.out.println("xDistance:" + xDistance);
		System.out.println("xDestination:" + xDestination);
		System.out.println("xVel:" + xVel);
		System.out.println("yDistance:" + yDistance);
		System.out.println("yDestination:" + yDestination);
		System.out.println("yVel:" + yVel);
		*/
		repaint();
	}

	public void mousePressed(MouseEvent e)
	{
		xDestination = e.getX();
		yDestination = e.getY();
	}

	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}