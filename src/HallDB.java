import java.util.HashMap;
import java.util.Map;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.*;
import com.mongodb.client.*;
import com.sun.media.jfxmedia.control.VideoDataBuffer;

import javax.print.Doc;

public class HallDB implements Database
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

				//database.createCollection(generateID(movieId, time));

				//System.out.println(generateID(movieId, time));
				myCollection = database.getCollection(generateID(movieId, time));
				System.out.println(myCollection);
				hallInit(myCollection, HallType.BIG_HALL);
				break;
	
			case SMALL_HALL:
				myCollection = database.getCollection(generateID(movieId, time));
				hallInit(myCollection, HallType.SMALL_HALL);
				break;
	
			default:
				break;
		}

	}

	@Override
	public Seat queryByID(String hallID)
	{
		
		return null;
	}

	public String generateID(String movieId, String time)
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
		MongoCursor<Document> cursor =  seatCollection.find(Filters.eq("occupied", false)).iterator();
		int count = 0;
		while (cursor.hasNext()) {
			count++;
			cursor.next();
			System.out.println(count);
		}
		return count;
	}

	public Seat[] getSeats(String HallID, int amount)
	{
		if(remain(HallID) > amount)
		{
			MongoCollection<Document> seatCollection= database.getCollection(HallID);
			MongoCursor<Document> cursor =  seatCollection.find(Filters.eq("occupied", false)).iterator();
			Seat[] seatList = new Seat[amount];
			for(int i = 0; i < amount; i++)
			{
				Document doc = cursor.next();
				//seatList[i] = new Seat(doc);
				// TODO
				// seat set to false
				seatCollection.updateOne(Filters.eq("id", doc.getString("id")),
								         new Document("$set", new Document("occupied", true)));


			}
		}
		return null;
	}




	public static void main(String[] args)
	{
		HallDB db = new HallDB();
		//db.createHall("asd", "9:30", HallType.SMALL_HALL);


//		MongoCollection<Document> myCollection
//				= db.database.getCollection(db.generateID("asd", "9:30"));
//		myCollection.insertOne(new Document("GG","VERY BIG"));

		System.out.println(db.remain("jkl9:30"));
		db.getSeats("jkl9:30", 5);



	}


}
