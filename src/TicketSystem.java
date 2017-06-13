
public class TicketSystem
{
	private Inquirier myInq;
	private Refunder myRefunder;
	private Booker myBooker;

	TicketSystem()
	{
		myInq = new Inquirier();
		myRefunder = new Refunder();
		myBooker = new Booker();
	}



}
