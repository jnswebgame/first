
import java.io.Serializable;


public class GameEvent implements Serializable
{
	enum EventType { PLAYER_MOVEMENT, GOLD_SPAWN, GOLD_TAKEN };
	private int move_to_x, move_to_y, player_id, gold_id;
	private int tile, new_tile;

	private EventType event_type;

	// Getters and setters
	public void setEventType(EventType event_type) { this.event_type = event_type; }
	public EventType getEventType() { return event_type; }
	public int getId() { return player_id; }
	public void setId(int id) { this.player_id = id; }
	public void setGoldId(int id) { this.gold_id = id; }
	public void setX(int x) { this.move_to_x = x; }
	public void setY(int y) { this.move_to_y = y; }
	public void setTile(int t) { this.tile = t; }
	public void setNewTile(int t) { this.new_tile = t; }
	public int getGoldId() { return this.gold_id; }
	public int getX() { return move_to_x; }
	public int getY() { return move_to_y; }
	public int getTile() { return tile; }
	public int getNewTile() { return new_tile; }

	// Constructors
	public GameEvent(int x, int y)
	{
		move_to_x = x;
		move_to_y = y;
		event_type = EventType.PLAYER_MOVEMENT;
		tile = 0;
	}

	public GameEvent(int x, int y, EventType event)
	{
		move_to_x = x;
		move_to_y = y;
		event_type = event;
		tile = 0;
	}

	public GameEvent(int x, int y, EventType event, int tile)
	{
		move_to_x = x;
		move_to_y = y;
		event_type = event;
		this.tile = tile;
	}




}