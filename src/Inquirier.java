
import java.text.ParseException;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inquirier
{
    private HallDB hallDB;
    private UserDB userDB;
    private MovieDB movieDB;
    private TicketDB ticketDB;

    public Inquirier(TicketDB ticketDB, HallDB hallDB, UserDB userDB, MovieDB movieDB) {
        this.hallDB = hallDB;
        this.userDB = userDB;
        this.movieDB = movieDB;
        this.ticketDB = ticketDB;
    }


    Movie[] movies = movieDB.getAllMovies(); //MovieDB.getAllMovies()

    public void byScore(double score){ // Movie.score 用 double
        boolean found = false;
        for(Movie m : movies){
            if(score > m.getScore()) continue; //Movie.getScore()
            if(found) System.out.print("，");
            System.out.print(m.getID());
            found = true;
        }
        if(found) System.out.print("\n");
        else System.out.println("查無符合條件之電影");
    }
    public void byTimeAfter(int amount, String time) { by("time", amount, time, (byte)1); } //最早
    public void byTimeBefore(int amount, String time) { by("time", amount, time, (byte)-1); } //最晚
    public void byMaxLength(int amount, String len)	{ by("length", amount, len, (byte)1); }
    public void byMinLength(int amount, String len)	{ by("length", amount, len, (byte)-1); }
    public void ticketInfo(String id) {
        Ticket ticket = ticketDB.queryByID(id);
        System.out.println("電影名稱：" + ticket.movieName);
        System.out.println("播映時間：" + ticket.startTime);
        System.out.println("廳位：" + ticket.hall);
        System.out.println("座位：" + ticket.seatInfo);
        return;
    }

    public void movieInfo(String id) {
        Movie movie = movieDB.queryByID(id);
        System.out.println("電影名稱：" + movie.name);
        System.out.println("分級：" + movie.classification);
        System.out.println("播映時間" + movie.time);
        System.out.println("廳位" + movie.hall);
        return;
    }

    public void specificSeats(int amount, String area, String row) {
        // to be done
        boolean found = false;
        for(Movie m : movies){
            String[] starts = m.getStart();
            String id = m.getID(); //Movie.getID()
            boolean has = false;
            for(String s : starts){
                String hallID = hallDB.generateHallID(id, s);
                if (!hallDB.checkSpecial(hallID, amount, area, row)) continue;
                if(!has) System.out.print(id);
                System.out.print("，"+s);
                has = true;
            }
            if(has){
                System.out.print("\n");
                found = true;
            }
        }
        if(!found) System.out.println("查無符合條件之電影");
        return;
    }

    private void by(String tl, int amount, String time, byte pm) {
        long t = t2ms(time);
        boolean found = false;

        for(Movie m : movies){
            String[] starts = m.getStart(); //Movie.getStart()
            String id = m.getID(); //Movie.getID()
            int mlen = m.getLength(); // Movie.getLength();
            int len = (int)t;
            boolean has = false;
            for(String s : starts){
                if(tl.equals("length")) if((mlen-len)*pm > 0) continue;
                if(tl.equals("time")) if((t-t2ms(s))*pm > 0) continue;
                if(amount > hallDB.remain(hallDB.generateHallID(id, s))) continue;
                if(!has) System.out.print(id);
                System.out.print("，"+s);
                has = true;
            }
            if(has){
                System.out.print("\n");
                found = true;
            }
        }
        if(!found) System.out.println("查無符合條件之電影");
    }

    private long t2ms(String time) {
        SimpleDateFormat f = new SimpleDateFormat("HH：mm");
        Date d = null;
        try {
            d = f.parse(time);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return d.getTime();
    }

}

