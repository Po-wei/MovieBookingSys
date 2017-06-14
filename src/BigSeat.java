import org.bson.Document;

/**
 * Created by jameshuang304 on 2017/6/13.
 */
public class BigSeat extends Seat
{
	protected String region;
	
	public BigSeat(Document doc)
	{
    	id = doc.getString("id");
        row = doc.getString("row");
        seatNum = doc.getInteger("seatNum");
        occupied = doc.getBoolean("occupied");
        region = doc.getString("region");
	}

	
}
