import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
/**
 * Created by jameshuang304 on 2017/6/13.
 */
public class Seat {
    protected String id;
    protected String row;
    protected int seatNum;
    protected boolean occupied;

    public Seat(Document doc) {
        id = doc.getString("id");
        row = doc.getString("row");
        seatNum = doc.getInteger("seatNum");
        occupied = doc.getBoolean("occupied");
    }
}
