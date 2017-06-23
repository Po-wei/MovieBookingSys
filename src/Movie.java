import org.bson.Document;

public class Movie
{
    protected String id;
    protected String name;
    protected String url;
    protected String classification;
    protected String descri;
    protected String infor;
    protected String score;
    protected String time;
    protected String hall;

    public Movie(Document doc) {
        id = doc.getString("id");
        name = doc.getString("movie");
        url = doc.getString("url");
        classification = doc.getString("classification");
        descri = doc.getString("descri");
        infor = doc.getString("infor");
        score = doc.getString("score");
        time = doc.getString("time");
        hall = doc.getString("hall");
    }

    public int getAge()
    {
        int age;
        if ("普遍".equals(classification)) {
           age = 0;
        } else if ("保護".equals(classification)) {
            age = 6;
        } else if ("輔導".equals(classification)) {
            age = 15;
        } else {
            age = 18;
        }
        return age;
    }

    public double getScore()
    {
        String tmp[] = score.trim().split("/");
        return Double.parseDouble(tmp[0]);
    }

    public String getID()
    {
        return id;
    }

    public int getLength()
    {
        String tmp[] = infor.trim().split(":"); //tmp[1] is xxx分
        String len = tmp[1].substring(0, tmp[1].length() - 1);
        return Integer.parseInt(len);
    }

    public String[] getStart()
    {
        String tmp[] = time.trim().split("、");
        return tmp;
    }

}
