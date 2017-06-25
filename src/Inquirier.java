import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inquirier
{
    private HallDB hallDB;
    private UserDB userDB;
    private MovieDB movieDB;
    private TicketDB ticketDB;
    private Movie[] movies;

    public Inquirier(TicketDB ticketDB, HallDB hallDB, UserDB userDB, MovieDB movieDB) {
        this.hallDB = hallDB;
        this.userDB = userDB;
        this.movieDB = movieDB;
        this.ticketDB = ticketDB;
        movies = movieDB.getAllMovies(); //MovieDB.getAllMovies()
    }


    public String byScore(double score){ // Movie.score 用 double
        String alert = "";
        boolean found = false;
        for(Movie m : movies){
            if(score > m.getScore()) continue; //Movie.getScore()
            //if(found) System.out.print("，");
            if(found) alert += "，";
            //System.out.print(m.getID());
            alert += m.getID();
            found = true;
        }
        //if(found) System.out.print("\n");
        if(found) alert += "\n";
            //else System.out.println("查無符合條件之電影");
        else alert += "查無符合條件之電影\n";

        return alert;
    }

    public String byTime(int amount, String after, String before, String min, String max){
        String alert = "";
        boolean found = false;
        for(Movie m : movies){
            String[] starts = m.getStart(); //Movie.getStart()
            String id = m.getID(); //Movie.getID()
            int mlen = m.getLength(); // Movie.getLength();
            boolean has = false;
            for(String s : starts){
                if(!after.equals("")) if(t2ms(after)>t2ms(s)) continue;
                if(!before.equals("")) if(t2ms(before)<t2ms(s)) continue;
                if(!min.equals("")) if(Integer.parseInt(min)>mlen) continue;
                if(!max.equals("")) if(Integer.parseInt(max)<mlen) continue;

                if(amount > hallDB.remain(hallDB.generateHallID(id, s))) continue;
                //if(!has) System.out.print(id);
                if(!has) alert += id;
                //System.out.print("，"+s);
                alert += ("，"+s);
                has = true;
            }
            if(has){
                //System.out.print("\n");
                alert += "\n";
                found = true;
            }
        }
        //if(!found) System.out.println("查無符合條件之電影");
        if(!found) alert += "查無符合條件之電影";

        return alert;
    }
    public String ticketInfo(String id) {
        Ticket ticket = ticketDB.queryByID(id);
        /*System.out.println("電影名稱：" + ticket.movieName);
        System.out.println("播映時間：" + ticket.startTime);
        System.out.println("廳位：" + ticket.hall);
        System.out.println("座位：" + ticket.seatInfo);*/
        String alert = "電影名稱：" + ticket.movieName + "\n播映時間：" + ticket.startTime + "\n廳位：" + ticket.hall + "\n座位：" + ticket.seatInfo;

        return alert;
    }

    public String movieInfo(String id) {
        Movie movie = movieDB.queryByID(id);
        /*System.out.println("電影名稱：" + movie.name);
        System.out.println("分級：" + movie.classification);
        System.out.println("播映時間：" + movie.time);
        System.out.println("廳位：" + movie.hall);*/
        String alert = "電影名稱：" + movie.name + "\n分級：" + movie.classification + "\n播映時間：" + movie.time + "\n廳位：" + movie.hall;

        return alert;
    }

    public String specificSeats(int amount, String area, String row) {
        String alert = "";
        boolean found = false;
        for(Movie m : movies){
            String[] starts = m.getStart();
            String id = m.getID(); //Movie.getID()
            boolean has = false;
            for(String s : starts){
                String hallID = hallDB.generateHallID(id, s);
                if (!hallDB.checkSpecial(hallID, amount, area, row)) continue;
                //if(!has) System.out.print(id);
                if(!has) alert += id;
                //System.out.print("，"+s);
                alert += ("，"+s);
                has = true;
            }
            if(has){
                //System.out.print("\n");
                alert += "\n";
                found = true;
            }
        }
        //if(!found) System.out.println("查無符合條件之電影");
        if(!found) alert += "查無符合條件之電影";

        return alert;
    }

    private long t2ms(String time) {

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

}