import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;

public class HallDB implements Database
{
	@Override
	public Hall queryByID(String ID)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("test");
		MongoCollection<Document> collection = database.getCollection("big_room");
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		System.out.println("auto!!");
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
	}

}
