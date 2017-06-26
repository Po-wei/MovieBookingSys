import org.bson.Document;

/**
 * <h1>BigSeat</h1>
 * This class stores all information
 * of a seat in big hall
 * <p>
 * @author  OOP team 8
 * @version 1.0
 * @since   2017-06-26
 */
public class BigSeat extends Seat
{
	protected String region;

	/**
	 * Constructor that takes one argument
	 * and initialize all attributes
	 * @param doc A document type object that
	 *            stores an object retrieved
	 *            from database
	 */
	public BigSeat(Document doc)
	{
    	id = doc.getString("id");
        row = doc.getString("row");
        seatNum = doc.getInteger("seatNum");
        occupied = doc.getBoolean("occupied");
        region = doc.getString("region");
	}

	
}
