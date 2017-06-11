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
	
	
	public static void Main(String[] args)
	{
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("TheDatabaseName");
		MongoCollection<Document> collection = database.getCollection("TheCollectionName");
		
	}

}
