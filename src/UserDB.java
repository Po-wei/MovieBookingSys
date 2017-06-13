import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.sun.media.jfxmedia.control.VideoDataBuffer;

public class UserDB implements Database
{
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> userCollection;

	public UserDB()
	{
		initialDB();
	}

	private void initialDB()
	{
		this.mongoClient = new MongoClient();
		this.database = mongoClient.getDatabase("TicketSys");
		this.userCollection = database.getCollection("user");
	}

	@Override
	public User queryByID(String userID)
	{
		Document myDoc = userCollection.find(eq("i", 71)).first();
		return null;
	}

}
