
public class TicketSystem
{
	public Inquirier myInq;
	public Refunder myRefunder;
	public Booker myBooker;
	public HallDB hallDB;
	public UserDB userDB;
	public MovieDB movieDB;
	public TicketDB ticketDB;

	public TicketSystem()
	{
		myInq = new Inquirier(ticketDB, hallDB, userDB, movieDB);
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
