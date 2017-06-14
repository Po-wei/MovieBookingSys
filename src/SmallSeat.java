import org.bson.Document;

/**
 * Created by jameshuang304 on 2017/6/13.
 */
public class SmallSeat extends Seat
{
	public SmallSeat(Document doc)
	{
        id = doc.getString("id");
        row = doc.getString("row");
        seatNum = doc.getInteger("seatNum");
        occupied = doc.getBoolean("occupied");
	}
}
