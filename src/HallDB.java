import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.*;
import com.mongodb.client.*;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
import org.bson.conversions.Bson;

import javax.print.Doc;

import java.util.*;

public class HallDB
{
	//private Map<String, String> HallMap;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> bigRoom;
	private MongoCollection<Document> smallRoom;

	public enum HallType
	{
		BIG_HALL, SMALL_HALL
	}

	public HallDB()
	{
		this.mongoClient = new MongoClient();
		this.database = mongoClient.getDatabase("TicketSys");
		this.bigRoom = database.getCollection("big_room");
		this.smallRoom = database.getCollection("small_room");
	}

	public void createHall(String movieId, String time, HallType hallType)
	{
		MongoCollection<Document> myCollection;
		switch (hallType)
		{
			case BIG_HALL:
				myCollection = database.getCollection(generateHallID(movieId, time));
				hallInit(myCollection, HallType.BIG_HALL);
				break;
	
			case SMALL_HALL:
				myCollection = database.getCollection(generateHallID(movieId, time));
				hallInit(myCollection, HallType.SMALL_HALL);
				break;
	
			default:
				break;
		}
	}


	public Seat queryByID(String HallID, String row, int seatNum)
	{
		MongoCollection<Document> seatCollection = database.getCollection(HallID);
		Bson myFilter = and(eq("row", row), eq("seatNum", seatNum));
		Document doc = seatCollection.find(myFilter).first();
		HallType type = checkHallSize(HallID);
		Seat seat;
		if (type == HallType.BIG_HALL) {
			seat = new BigSeat(doc);
		} else {
			seat = new SmallSeat(doc);
		}
		return seat;
	}



	public String generateHallID(String movieId, String time)
	{
		//may change in the future
		return movieId + time;
	}

	private void hallInit(MongoCollection<Document> myCollection, HallType hallType)
	{
		FindIterable<Document> findIterable;
		MongoCursor<Document> mongoCursor;
		switch(hallType)
		{
			case BIG_HALL:
				findIterable = this.bigRoom.find();
				mongoCursor = findIterable.iterator();
				while (mongoCursor.hasNext()) {
					Document doc = mongoCursor.next();
					//System.out.println(doc);
					myCollection.insertOne(doc);
				}
				break;

			case SMALL_HALL:
				findIterable = this.smallRoom.find();
				mongoCursor = findIterable.iterator();
				while (mongoCursor.hasNext()) {
					Document doc = mongoCursor.next();
					myCollection.insertOne(doc);
				}
				break;

			default:
				break;

		}
	}

	public int remain(String HallID)
	{
		MongoCollection<Document> seatCollection= database.getCollection(HallID);
		MongoCursor<Document> cursor =  seatCollection.find(eq("occupied", false)).iterator();
		int count = 0;
		while (cursor.hasNext()) {
			count++;
			cursor.next();
			System.out.println(count);
		}
		return count;
	}
	public boolean cancelSeats()
	{
		return false;
	}

	public boolean bookSeat()
	{
		return false;
	}


//	public Seat[] getSeats(String HallID, int amount)
//	{
//		Seat[] seatList = null;
//		if(remain(HallID) > amount)
//		{
//			MongoCollection<Document> seatCollection= database.getCollection(HallID);
//			MongoCursor<Document> cursor =  seatCollection.find(eq("occupied", false)).iterator();
//			seatList = new Seat[amount];
//			for(int i = 0; i < amount; i++)
//			{
//				Document doc = cursor.next();
//				seatList[i] = new Seat(doc);
//
//				// seat set to true
//				seatCollection.updateOne(eq("id", doc.getString("id")),
//								         new Document("$set", new Document("occupied", true)));
//
//			}
//		}
//		return seatList;
//	}

//	public boolean checkEnough(String HallID, int amount)
//	{
//		if(remain(HallID) > amount) {
//			return true;
//		}
//		return false;
//	}
	private HallType checkHallSize(String HallID)
	{
		MongoCollection<Document> seatCollection= database.getCollection(HallID);
		Document doc = seatCollection.find().first();
		HallType type;
		switch (doc.getString("id").charAt(2))
		{
			case '1':
				return HallType.BIG_HALL;
			case '2':
				return HallType.SMALL_HALL;
			default:
				return null;
		}
	}

	public Seat[] getSeats(String HallID, int amount)
	{
		Seat[] seatList = null;
		if(remain(HallID) > amount)
		{
			MongoCollection<Document> seatCollection= database.getCollection(HallID);
			MongoCursor<Document> cursor =  seatCollection.find(eq("occupied", false)).iterator();
			seatList = new Seat[amount];
			HallType type = checkHallSize(HallID);
			for(int i = 0; i < amount; i++)
			{
				Document doc = cursor.next();
				if (type == HallType.BIG_HALL) {
					seatList[i] = new BigSeat(doc);
				} else {
					seatList[i] = new SmallSeat(doc);
				}
				// seat set to true
				seatCollection.updateOne(eq("id", doc.getString("id")),
						new Document("$set", new Document("occupied", true)));

			}
		}
		return seatList;
	}

	public ArrayList<Seat> getSpecialSeats(String HallID, int amount, boolean continuous, String area, String row)
	{
		HallType type = checkHallSize(HallID);
		ArrayList<Seat> specialSeats = null;
		if (continuous == false) {
			MongoCollection<Document> seatCollection= database.getCollection(HallID);
			if ("none".equals(area)) {
				MongoCursor<Document> cursor =  seatCollection.find(eq("row", row)).iterator();
				while (cursor.hasNext()) {
					Seat seat;
					if (type == HallType.BIG_HALL) {
						seat = new BigSeat(cursor.next());
					} else {
						seat = new SmallSeat(cursor.next());
					}
					specialSeats.add(seat);
				}
			} else if ("none".equals(row))  {
				MongoCursor<Document> cursor =  seatCollection.find(eq("area", area)).iterator();
				while (cursor.hasNext()) {
					Seat seat;
					if (type == HallType.BIG_HALL) {
						seat = new BigSeat(cursor.next());
					} else {
						seat = new SmallSeat(cursor.next());
					}
					specialSeats.add(seat);
				}
			} else { //two conditions are set
				Bson myFilter = and(eq("area", area), eq("row", row));
				MongoCursor<Document> cursor =  seatCollection.find(myFilter).iterator();
				while (cursor.hasNext()) {
					Seat seat;
					if (type == HallType.BIG_HALL) {
						seat = new BigSeat(cursor.next());
					} else {
						seat = new SmallSeat(cursor.next());
					}
					specialSeats.add(seat);
				}
			}
		} else {
			//TODO continuous is set


		}

		return specialSeats;
	}


//	public static void main(String[] args)
//	{
//		HallDB db = new HallDB();
//		db.createHall("asd", "9:30", HallType.SMALL_HALL);
//
//
//		MongoCollection<Document> myCollection
//				= db.database.getCollection(db.generateHallID("asd", "9:30"));
//		myCollection.insertOne(new Document("GG","VERY BIG"));
//
//		System.out.println(db.remain("jkl9:30"));
//		db.getSeats("jkl9:30", 5);
//
//
//
//	}
}
