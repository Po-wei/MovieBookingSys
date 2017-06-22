
public class TicketSystem
{
	private Inquirier myInq;
	private Refunder myRefunder;
	private Booker myBooker;
	private HallDB hallDB;
	private UserDB userDB;
	private MovieDB movieDB;
	private TicketDB ticketDB;

	public TicketSystem()
	{
		myInq = new Inquirier(hallDB, userDB, movieDB);
		myRefunder = new Refunder(ticketDB, hallDB, userDB, movieDB);
		myBooker = new Booker(ticketDB, hallDB, userDB, movieDB);
	}

	private void databaseInit() {
		hallDB = new HallDB();
		userDB = new UserDB();
		movieDB = new MovieDB();
		ticketDB = new TicketDB();
	}



}
