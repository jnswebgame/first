
import java.io.Serializable;


public class GameEvent implements Serializable
{
	enum EventType { PLAYER_MOVEMENT };
	private int move_to_x, move_to_y, player_id;

	private EventType event_type;

	// Getters and setters
	public void setEventType(EventType event_type) { this.event_type = event_type; }
	public EventType getEventType() { return event_type; }
	public int getId() { return player_id; }
	public void setId(int id) { this.player_id = id; }
	public int getX() { return move_to_x; }
	public int getY() { return move_to_y; }

	// Constructors
	public GameEvent(int x, int y)
	{
		move_to_x = x;
		move_to_y = y;
		event_type = EventType.PLAYER_MOVEMENT;
	}




}