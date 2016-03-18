

import java.io.*;
import java.awt.*;

public class Player implements Serializable
{
	private int x, y, id;
	int xCurrent = 50, yCurrent = 50, xDestination = 50, yDestination = 50;
	private int xDistance = 0, yDistance = 0, xVel = 0, yVel = 0;
	private int velocity = 7;


	public String toString()
	{
		return "xCurrent: " + xCurrent + " yCurrent" + yCurrent + " xDest " + xDestination + " ID " + id;
	}

	// Copy constructor
	public Player(Player p)
	{
		xCurrent = p.xCurrent;
		yCurrent = p.yCurrent;
		xDestination = p.xDestination;
		yDestination = p.yDestination;
		xDistance = p.xDistance;
		yDistance = p.yDistance;
		xVel = p.xVel;
		yVel = p.yVel;
		id = p.id;
		x = p.x;
		y = p.y;
	}

	public void setPosition(int x, int y)
	{
		xCurrent = x;
		yCurrent = y;
	}

	public void setVelocity(int xVel, int yVel)
	{
		this.xVel = xVel;
		this.yVel = yVel;
	}

	public void setDestination(int x, int y)
	{
		xDestination = x;
		yDestination = y;
	}

	public void paintUnit(Graphics g)
	{
		updatePosition();
		g.setColor(Color.BLACK);
		g.fillOval(xCurrent-10, yCurrent-10, 20, 20);
	}

	protected void updatePosition()
	{
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
	}

	public Player(int x, int y)
	{
		this.xDestination = x;
		this.yDestination = y;
	}
	public Player()
	{
		x = 0; y = 0;
	}
	public int getX()
	{
		return x;
	}
	public int getY() { return y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }


}