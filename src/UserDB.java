import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;


public class UserDB implements Database
{
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> userCollection;

	public UserDB()
	{
		this.mongoClient = new MongoClient();
		this.database = mongoClient.getDatabase("TicketSys");
		this.userCollection = database.getCollection("user");
	}

	@Override
	public User queryByID(String userID)
	{
		int userIndx = Integer.parseInt(userID);
		Document myDoc = userCollection.find(Filters.eq("index", userIndx)).first();
		System.out.println(myDoc);

		return new User(myDoc);
	}

	public static void main(String[] args) {
		UserDB myUserDB = new UserDB();
		User myUser = myUserDB.queryByID("3");
		System.out.println(myUser.name);
	}

}
