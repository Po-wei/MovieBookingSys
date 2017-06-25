

import java.text.*;
import java.util.*;
import java.sql.Timestamp;
import java.util.Date;

public class Refunder
{
    //for test
    private TicketDB ticketDB;
    private HallDB hallDB;
    private UserDB userDB;
    private MovieDB movieDB;

    public Refunder(TicketDB ticketDB, HallDB hallDB, UserDB userDB, MovieDB movieDB) {
        this.ticketDB = ticketDB;
        this.hallDB = hallDB;
        this.userDB = userDB;
        this.movieDB = movieDB;
    }




    private long t2ms(String time){
        String fuck = time.replace("：",":");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = f.parse(fuck);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return d.getTime();
    }
}            hallDB.cancelSeat(ticket);
        }
        else message = "退票失敗，退票需於開場時間前20分鐘前";
        //System.out.println(message);

        String alert = message;
        return alert;
    }

    private long t2ms(String time){
        String fuck = time.replace("：",":");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = f.parse(fuck);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return d.getTime();
    }
    public String refund(String ticketID){
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        Ticket ticket = ticketDB.queryByID(ticketID);
        String message = "";
        if(null == ticket) message = "退票失敗，此電影票ID不存在";
        else if(t2ms(ticket.startTime) - t2ms(df.format(now)) > 1200000) {
            message = "退票成功，全額退款";
            //ticket.available();  //待實作
            ticketDB.available(ticket);
            hallDB.cancelSeat(ticket);
        }
        else message = "退票失敗，退票需於開場時間前20分鐘前";
        //System.out.println(message);
=======
>>>>>>> branch 'master' of https://github.com/jameshuang304/MovieBookingSys.git

}