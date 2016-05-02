import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Player implements Serializable
{
	private int goldCount;
	private int x, y, id, atk, def, agi;
	int xCurrent = 50, yCurrent = 50, xDestination = 50, yDestination = 50;
	int tile = 0;
	int hp =0;
	private int xDistance = 0, yDistance = 0, xVel = 0, yVel = 0;
	private int velocity = 7;

	MoveToMouse panel;

	private ImageIcon u = new ImageIcon("linkFront.png"); //there are no fucking pictures of link from the back on the whole internet
	private ImageIcon d = new ImageIcon("linkFront.png");
	private ImageIcon r = new ImageIcon("linkRight.png");
	private ImageIcon l = new ImageIcon("linkLeft.png");

	private Image up = u.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	private Image down = d.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	private Image right = r.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
	private Image left = l.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);	


	public String toString()
	{
		return "xCurrent: " + xCurrent + " yCurrent" + yCurrent + " xDest " + xDestination + " ID " + id;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(xCurrent-10, yCurrent-10, 20, 20);
	}

	public void addGold(int i)
	{
		goldCount += i;
		//System.out.println("You now have " + goldCount + " gold");
	}

	// Copy constructor
	public Player(Player p)
	{
		goldCount = p.goldCount;
		xCurrent = p.xCurrent;
		yCurrent = p.yCurrent;
		xDestination = p.xDestination;
		yDestination = p.yDestination;
		xDistance = p.xDistance;
		yDistance = p.yDistance;
		xVel = p.xVel;
		yVel = p.yVel;
		id = p.id;
		tile = p.tile;
		x = p.x;
		y = p.y;
		hp = p.hp;
		atk = p.atk;
		def = p.def;
		agi= p.agi;
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

	public void paintUnit(Graphics g, MoveToMouse panel)
	{
		updatePosition();

		if(Math.abs(xVel) > Math.abs(yVel))
		{
			if(xVel > 0)
			{
				g.drawImage(up, xCurrent-10, yCurrent-10, panel);
			}
			else
			{
				g.drawImage(down, xCurrent-10, yCurrent-10, panel);
			}
		}
		if(Math.abs(xVel) <= Math.abs(yVel))
		{	
			if(yVel > 0)
			{
				g.drawImage(right, xCurrent-10, yCurrent-10, panel);
			}
			else
			{
				g.drawImage(left, xCurrent-10, yCurrent-10, panel);
			}
		}
	}

	protected void updatePosition()
	{
		xDistance = (xDestination - xCurrent);
		yDistance = (yDestination - yCurrent);
		//if(xDistance < velocity && yDistance < velocity)
		if (xDistance == 0 && yDistance == 0)
		{
			xVel = 0;
			yVel = 0;
		}
		else
		{
			xVel = (int)((velocity * xDistance) / (Math.abs(xDistance) + Math.abs(yDistance)));
			yVel = (int)((velocity * yDistance) / (Math.abs(xDistance) + Math.abs(yDistance)));
		}

		// This fixes the jumping around at destination problem
		if (Math.abs(xDistance) < velocity && Math.abs(yDistance) < velocity)
		{
			xCurrent = xDestination;
			yCurrent = yDestination;
		} else
		{
			xCurrent += xVel;
			yCurrent += yVel;
		}
	}

	public Player(int x, int y)
	{
		this.xDestination = x;
		this.yDestination = y;
		goldCount = 0;
	}
	public Player()
	{
		x = 0; y = 0;
		goldCount = 0;
	}
	public int getX()
	{
		return xCurrent;
	}
	public int getHP(){ return hp;}	
	public int getAtk(){return atk;}
	public int getDef(){return def;}
	public int getAgi() {return agi;}
	public int getY() { return y; }
	public int getGold() { return goldCount;}
	public int getId() { return id; }
	public int getTile() { return tile; }

	
	public void setHP(int i){this.hp=i;}	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setId(int id) { this.id = id; }
	public void setGold(int g){this.goldCount= g;}		
	public void setATK(int i){this.atk=i;}	
	public void setDEF(int i) { this.def = i; }
	public void setAGI(int i) { this.agi=i;}
	public void setTile(int t) { this.tile = t; }


}
