import javax.naming.directory.SearchControls;

/**
 * This class handle all booking ticket query
 *
 * @author powei
 *
 */

public class Booker{

	private TicketDB ticketDB;
	private HallDB hallDB;
	private UserDB userDB;
	private MovieDB movieDB;

	public Booker(TicketDB ticketDB, HallDB hallDB, UserDB userDB, MovieDB movieDB) {
		this.ticketDB = ticketDB;
		this.hallDB = hallDB;
		this.userDB = userDB;
		this.movieDB = movieDB;
	}

	public String generalBook(String userID, String movieID, String time, int amount) {
		String alert = "";
		Movie movie = movieDB.queryByID(movieID);
		User user = userDB.queryByID(userID);
		String hallID = hallDB.generateHallID(movieID, time);

		int newRemain = hallDB.remain(hallID) - amount;
		try{
			if(newRemain < 0) {
				throw new SeatNotEnoughException(movieID, time);
			}
			if(user.age < movie.getAge()) {
				throw new WatchThreeSmallException(movie.classification, user.age);
			}
			Seat[] bookedSeats = hallDB.getSeats(hallID, amount);
			Ticket[] booked = new Ticket[amount];
			int i = 0;
			for (Seat s : bookedSeats) {
				if (i > 0) {
					//System.out.print(",");
					alert += ",";
				}
				Ticket t = ticketDB.createTicket(movie.name, time, s.getSeat(), movie.hall, movie.id);
				booked[i++] = t;
				//System.out.print(t.id);
				alert += t.id;
			}
			//System.out.println("\n" + movieID + "於"+ time+"目前仍有"+ newRemain);
			String nr;
			HallDB.HallType hallType = hallDB.checkHallSize(hallID);
			if(hallType == HallDB.HallType.SMALL_HALL) nr = "" + newRemain ;
			else{
				int[] gbyr = hallDB.getSpecialRemain(hallID);
				nr = "\ngray: " + gbyr[0] + "\nblue:" + gbyr[1] + "\nyellow:" + gbyr[2] + "\nred:" + gbyr[3] + "\n";
			}
			alert = alert + "\n" + movieID + "於"+ time+"目前仍有"+ nr;
		} catch (SeatNotEnoughException e) {
			//System.out.println(e.getMessage());
			alert = e.getMessage();
			//TODO What to do if exception happens?
		} catch (WatchThreeSmallException e) {
			//System.out.println(e.getMessage());
			alert = e.getMessage();
			//TODO What to do if exception happens?
		}

		return alert;
	}

	public String conditionalBook(String userID, String movieID, String time, int amount, boolean continuous, String area, String row) {
		String alert = "";
		Movie movie = movieDB.queryByID(movieID);
		User user = userDB.queryByID(userID);
		String hallID = hallDB.generateHallID(movieID, time);

		String newRemain;

		HallDB.HallType hallType = hallDB.checkHallSize(hallID);
		try {
			if (hallType == HallDB.HallType.SMALL_HALL && !"none".equals(area)) {
				throw new WrongRegionException();
			}
			if (user.age < movie.getAge()) {
				throw new WatchThreeSmallException(movie.classification, user.age);
			}
			//在此假設getSpecialSeats會回傳購票好的座位或是null
			Seat[] bookedSeats = hallDB.getSpecialSeats(hallID, amount, continuous, area, row);
			if (null == bookedSeats) {
				throw new SeatNotEnoughException(movieID, time);
			}
			Ticket[] booked = new Ticket[amount];
			int i = 0;
			for(Seat s : bookedSeats){
				//if(i>0) System.out.print(",");
				if(i>0) alert += ",";
				Ticket t = ticketDB.createTicket(movie.name, time, s.getSeat(), movie.hall, movie.id);
				booked[i++] = t;
				//System.out.print(t.id);
				alert += t.id;
			}
			//System.out.println("\n" + movieID + "於" + time + "目前仍有" + newRemain);

			if(hallType == HallDB.HallType.SMALL_HALL) newRemain = "" + (hallDB.remain(hallID) - amount) ;
			else{
				int[] gbyr = hallDB.getSpecialRemain(hallID);
				newRemain = "\ngray: " + gbyr[0] + "\nblue:" + gbyr[1] + "\nyellow:" + gbyr[2] + "\nred:" + gbyr[3] + "\n";
			}

			alert = alert + "\n" + movieID + "於" + time + "目前仍有" + newRemain;
		} catch (WrongRegionException e) {
			//System.out.println(e.getMessage());
			alert = e.getMessage();
		} catch (SeatNotEnoughException e) {
			//System.out.println(e.getMessage());
			alert = e.getMessage();
		} catch (WatchThreeSmallException e) {
			//System.out.println(e.getMessage());
			alert = e.getMessage();
		}

		return alert;
	}
}
