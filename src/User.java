import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
public class User
{
    protected int index;
    protected String name;
    protected int age;

    public User(Document doc)
    {
        index = doc.getInteger("index");
        name = doc.getString("name");
        age = doc.getInteger("age");
    }

    //all fields are primitive/immutable type
    //so we do not need a constructor
}
