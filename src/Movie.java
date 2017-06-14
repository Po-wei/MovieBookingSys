import org.bson.Document;

public class Movie
{
    String id;
    String movie;
    String url;
    String classification;
    String descri;
    String infor;
    String score;
    String time;
    String hall;

    public Movie(Document doc) {
        id = doc.getString("id");
        movie = doc.getString("movie");
        url = doc.getString("url");
        classification = doc.getString("classification");
        descri = doc.getString("descri");
        infor = doc.getString("infor");
        score = doc.getString("score");
        time = doc.getString("time");
        hall = doc.getString("hall");
    }

    public getAge()
    {

    }

}
