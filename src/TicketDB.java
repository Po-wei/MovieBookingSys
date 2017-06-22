/**
 * Created by jameshuang304 on 2017/6/22.
 */

import java.util.*;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;


public class TicketDB{

//    private MongoClient mongoClient;
//    private MongoDatabase database;
//    private MongoCollection<Document> ticketCollection;
//
//    public TicketDB()
//    {
//        this.mongoClient = new MongoClient();
//        this.database = mongoClient.getDatabase("TicketSys");
//        this.ticketCollection = database.getCollection("ticket");
//    }

    private static Map<String, Ticket> tickets = new HashMap<String, Ticket>();

    public Ticket createTicket(String name, String time, String info, String hall){
        String id = generateTicketID(name, time, info, hall); //某種生成ID的方法
        Ticket ticket = new Ticket(id, name, time, info, hall);
        tickets.put(id, ticket);
        return ticket;
    }

    private String generateTicketID (String name, String time, String info, String hall) {
        return name + time + info + hall;
    }

    public Ticket queryByID(String id) {
        return tickets.get(id);
    }

}
