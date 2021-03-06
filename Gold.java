
import java.util.Random;
import java.io.*;
import java.awt.*;

public class Gold implements Serializable
{
	private int x, y, id, tile;
	//Random = random;

	public String toString()
	{
		return "x: " + x + " y: " + y + " ID: " + id;
	}

	public Gold() {}
	public Gold(int x, int y)
	{
		this.x = x;
		this.y = y;
		tile = 0;
	}

	public void paintUnit(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillOval(x-7, y-10, 14, 20);
	}

	public int getX() {	return x; }
	public int getY() { return y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	public int getTile() { return tile; }
	public void setTile(int t) { this.tile = t; }
	public Rectangle getBounds()
	{
		return new Rectangle(x-7, y-10, 14, 20);
	}

}