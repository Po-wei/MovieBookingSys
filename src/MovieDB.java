import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

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

	public Movie[] getAllMovies() {
		MongoCursor<Document> cursor =  movieCollection.find().iterator();
		ArrayList<Movie> movieList = null;
		while(cursor.hasNext()) {
			movieList.add(new Movie(cursor.next()));
		}
		return movieList.toArray(new Movie[0]);
	}

	public String[] getAllMovieNames() {
		Movie[] movies = getAllMovies();
		String[] movieNames = new String[movies.length];
		int indx = 0;
		for (Movie m : movies) {
			movieNames[indx] = m.name;
			indx++;
		}
		return movieNames;
	}

	public String[] getStartByName(String movieName) {
		Movie[] allMovies = getAllMovies();
		String[] movieAllTime = null;

		for (Movie m : allMovies) {
			if (m.name.equals(movieName)) {
				movieAllTime = m.getStart();
				break;
			}
		}

		return  movieAllTime;
	}

	public String getIDByName (String movieName) {
		Movie[] allMovies = getAllMovies();
		String movieID = null;
		for (Movie m : allMovies) {
			if (m.name.equals(movieName)) {
				movieID = m.getID();
				break;
			}
		}
		return movieID;
	}

}
