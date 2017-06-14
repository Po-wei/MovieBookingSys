import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MovieDB implements Database
{
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> movieCollection;

	public MovieDB()
	{
		this.mongoClient = new MongoClient();
		this.database = mongoClient.getDatabase("TicketSys");
		this.movieCollection = database.getCollection("movie_info");
	}
	@Override
	public Movie queryByID(String ID)
	{
		Document myDoc = movieCollection.find(Filters.eq("id", ID)).first();
		System.out.println(myDoc);
		return new Movie(myDoc);
	}

}
