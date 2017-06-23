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

    private Map<String, Ticket> tickets = new HashMap<String, Ticket>();  //key:ID value:ticket object

    public Ticket createTicket(String name, String time, String info, String hall, String movieID){
        String id = generateTicketID(name, time, info, hall, movieID); //某種生成ID的方法
        Ticket ticket = new Ticket(id, name, time, info, hall, movieID);
        tickets.put(id, ticket);
        return ticket;
    }

    private String generateTicketID (String name, String time, String info, String hall, String movieID) {
        return name + time + info + hall + movieID;
    }

    public Ticket queryByID(String id) {
        return tickets.get(id);
    }

    public void available(Ticket ticket) {
        tickets.remove(generateTicketID(ticket.movieName, ticket.startTime, ticket.seatInfo, ticket.hall, ticket.movieID));
    }
}
