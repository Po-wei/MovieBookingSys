
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inquirier
{
    private HallDB hallDB;
    private UserDB userDB;
    private MovieDB movieDB;

    public Inquirier(HallDB hallDB, UserDB userDB, MovieDB movieDB) {
        this.hallDB = hallDB;
        this.userDB = userDB;
        this.movieDB = movieDB;
    }


    static Movie[] movies = MovieDB.getAllMovies(); //MovieDB.getAllMovies()

    public static void byScore(double score){ // Movie.score 用 double
        boolean found = false;
        for(Movie m : movies){
            if(score > m.getScore()) continue; //Movie.getScore()
            if(found) System.out.print("，");
            System.out.print(m.getID());
            found = true;
        }
        if(has) System.out.print("\n");
        else System.out.println("查無符合條件之電影");
    }
    public static void byTimeAfter(int amount, String time) {by("time", amount, time, 1);} //最早
    public static void byTimeBefore(int amount, String time){by("time", amount, time, -1);} //最晚
    public static void byMaxLength(int amount, String len)	{by("length", amount, len, 1);}
    public static void byMinLength(int amount, String len)	{by("length", amount, len, -1);}
    public static void ticketInfo(String id){
        TicketDB tdb = new TicketDB();
        Ticket ticket = tdb.queryByID(id);
        System.out.println("電影名稱：" + ticket.movieName);
        System.out.println("播映時間：" + ticket.startTime);
        System.out.println("廳位：" + ticket.hall);
        System.out.println("座位：" + ticket.seatInfo);
        return;
    }
    public static void movieInfo(String id){
        MovieDB mdb = new MovieDB();
        Movie movie = mdb.queryByID(id);
        System.out.println("電影名稱：" + movie.movie);
        System.out.println("分級：" + movie.classification);
        System.out.println("播映時間" + movie.time);
        System.out.println("廳位" + movie.hall);
        return;
    }
    public static void specificSeats(int amount, String area, String row){
        // to be done

        return;
    }

    private static void by(String tl, int amount, String time, byte pm){
        int t = t2ms(time);
        boolean found = false;
        HallDB hdb = new HallDB();
        for(Movie m : movies){
            String[] starts = m.getStart(); //Movie.getStart()
            String id = m.getID(); //Movie.getID()
            int mlen = m.getLength(); // Movie.getLength();
            int len = int(time);
            boolean has = false;
            for(String s : starts){
                if(tl.equals("length")) if((mlen-len)*pm > 0) continue;
                if(tl.equals("time")) if((t-t2ms(s))*pm > 0) continue;
                if(amount > hdb.remain(hdb.generateHallID(id, s))) continue;
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

    private static long t2ms(String time){
        SimpleDateFormat f = new SimpleDateFormat("HH：mm");
        Date d = f.parse(time);
        return d.getTime();
    }

}

