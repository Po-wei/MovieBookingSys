
public class TicketSystem
{
	public Inquirier myInq;
	public Refunder myRefunder;
	public Booker myBooker;
	private HallDB hallDB;
	private UserDB userDB;
	public MovieDB movieDB;
	private TicketDB ticketDB;

	public TicketSystem()
	{
		databaseInit();
		myInq = new Inquirier(ticketDB, hallDB, userDB, movieDB);
		myRefunder = new Refunder(ticketDB, hallDB, userDB, movieDB);
		myBooker = new Booker(ticketDB, hallDB, userDB, movieDB);
	}

	private void databaseInit() {
		hallDB = new HallDB();
		userDB = new UserDB();
		movieDB = new MovieDB();
		ticketDB = new TicketDB();

//		Movie[] movies = movieDB.getAllMovies();
//		for(Movie m : movies) {
//			for (String time : m.getStart()) {
//
//			}
//		}


		Movie[] movies = movieDB.getAllMovies();
		for(Movie m : movies) {
			for (String time : m.getStart()) {
				hallDB.createHall(m.id, time, m.getHallType());
			}
		}
	}

}

