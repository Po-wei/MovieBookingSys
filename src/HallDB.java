import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;
import com.sun.media.jfxmedia.control.VideoDataBuffer;

public class HallDB implements Database
{
	private Map<String, String> HallMap;
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
		// HallMap = new HashMap<String, String>();
		initialDB();
		// constructMap();
	}

	public void createHall(String movieId, String time, HallType hallType)
	{
		MongoCollection<Document> myCollection;
		switch (hallType)
		{
			case BIG_HALL:
				myCollection = database.getCollection(generateID(movieId, time));
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
	public Hall queryByID(String hallID)
	{
		return null;
	}

	public String generateID(String movieId, String time)
	{
		//may change in the future
		return movieId + time;
	}

	/**
	 * This method will construct Database properly!
	 * 
	 */
	private void initialDB()
	{
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("TicketSystem");
		MongoCollection<Document> bigRoom = database.getCollection("big_room");
		MongoCollection<Document> smallRoom = database.getCollection("small_room");
	}
	
	private void hallInit(MongoCollection<Document> myCollection, HallType hallType)
	{
		switch(hallType)
		{
		case BIG_HALL:
			database.getCollection("big_room");
			FindIterable<Document> findIterable =myCollection.find();
//			MongoCursor<Document> mongoCursor = findIterable.iterator();
			for(Document document : findIterable)
			{
				myCollection.insertOne(document);
			}
			break;
			
		}
	}
	
	public static void main(String[] args)
	{
//		MongoClient mongoClient = new MongoClient();
//		MongoDatabase database = mongoClient.getDatabase("test");
//		MongoCollection<Document> collection = database.getCollection("big_room");
//		FindIterable<Document> findIterable = collection.find();
//		MongoCursor<Document> mongoCursor = findIterable.iterator();
//		// FindIterable<Document> findIterable =
//		// database.getCollection("big_room");
//		System.out.println("auto!!");
//		while (mongoCursor.hasNext())
//		{
//			Document doc = mongoCursor.next();
//			System.out.println(doc.getString("row") + doc.getInteger("seatNum"));
//		}
//		MongoCollection<Document> collection2 = database.getCollection("dick_room");
		
		HallDB db = new HallDB();
		db.createHall("asd", "9:30", HallType.BIG_HALL);

	}
}
