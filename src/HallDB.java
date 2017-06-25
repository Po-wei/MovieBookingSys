import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.*;
import com.mongodb.client.*;

import org.bson.conversions.Bson;

import javax.print.Doc;

import java.util.*;

public class HallDB
{
	//private Map<String, String> HallMap;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> bigRoom;
	private MongoCollection<Document> smallRoom;

	//nested enum is effectively static
	public enum HallType
	{
		BIG_HALL, SMALL_HALL
	}

	public HallDB()
	{
		this.mongoClient = new MongoClient();
		this.database = mongoClient.getDatabase("TicketSys");
		this.bigRoom = database.getCollection("big_room");
		this.smallRoom = database.getCollection("small_room");
	}

	public void createHall(String movieId, String time, HallType hallType)
	{
		MongoCollection<Document> myCollection;
		switch (hallType)
		{
			case BIG_HALL:

				myCollection = database.getCollection(generateHallID(movieId, time));
				myCollection.drop();
				myCollection = database.getCollection(generateHallID(movieId, time));
				hallInit(myCollection, HallType.BIG_HALL);
				break;
	
			case SMALL_HALL:
				myCollection = database.getCollection(generateHallID(movieId, time));
				myCollection.drop();
				myCollection = database.getCollection(generateHallID(movieId, time));
				hallInit(myCollection, HallType.SMALL_HALL);
				break;
	
			default:
				break;
		}
	}


	public Seat queryByID(String HallID, String row, int seatNum)
	{
		MongoCollection<Document> seatCollection = database.getCollection(HallID);
		Bson myFilter = and(eq("row", row), eq("seatNum", seatNum));
		Document doc = seatCollection.find(myFilter).first();
		HallType type = checkHallSize(HallID);
		Seat seat;
		if (type == HallType.BIG_HALL) {
			seat = new BigSeat(doc);
		} else {
			seat = new SmallSeat(doc);
		}
		return seat;
	}



	public String generateHallID(String movieId, String time)
	{
		//may change in the future
		return movieId + time;
	}

	private void hallInit(MongoCollection<Document> myCollection, HallType hallType)
	{
		FindIterable<Document> findIterable;
		MongoCursor<Document> mongoCursor;
		switch(hallType)
		{
			case BIG_HALL:
				findIterable = this.bigRoom.find();
				mongoCursor = findIterable.iterator();
				while (mongoCursor.hasNext()) {
					Document doc = mongoCursor.next();
					//System.out.println(doc);
					myCollection.insertOne(doc);
				}
				break;

			case SMALL_HALL:
				findIterable = this.smallRoom.find();
				mongoCursor = findIterable.iterator();
				while (mongoCursor.hasNext()) {
					Document doc = mongoCursor.next();
					myCollection.insertOne(doc);
				}
				break;

			default:
				break;

		}
	}

	public int remain(String HallID)
	{
		MongoCollection<Document> seatCollection= database.getCollection(HallID);
		MongoCursor<Document> cursor =  seatCollection.find(eq("occupied", false)).iterator();
		int count = 0;
		while (cursor.hasNext()) {
			count++;
			cursor.next();
//			System.out.println(count);
		}
		return count;
	}


	public HallType checkHallSize(String HallID)
	{
		MongoCollection<Document> seatCollection= database.getCollection(HallID);
		Document doc = seatCollection.find().first();
		HallType type;
//		System.out.println("charat(2) = "+ doc.getString("id").trim().charAt(2));
		switch (doc.getString("id").trim().charAt(2))
		{
			case '1':
				return HallType.SMALL_HALL;
			case '2':
				return HallType.BIG_HALL;
			default:
				return null;
		}
	}


	public Seat[] getSeats(String HallID, int amount)
	{
		Seat[] seatList = null;
		if(remain(HallID) > amount)
		{
			MongoCollection<Document> seatCollection= database.getCollection(HallID);
			MongoCursor<Document> cursor =  seatCollection.find(eq("occupied", false)).iterator();
			seatList = new Seat[amount];
			HallType type = checkHallSize(HallID);
			for(int i = 0; i < amount; i++)
			{
				Document doc = cursor.next();
				if (type == HallType.BIG_HALL) {
					seatList[i] = new BigSeat(doc);
				} else {
					seatList[i] = new SmallSeat(doc);
				}
				// seat set to true
				seatCollection.updateOne(eq("id", doc.getString("id")),
						new Document("$set", new Document("occupied", true)));

			}
		}
		return seatList;
	}

	public Seat[] getSpecialSeats(String HallID, int amount, boolean continuous, String area, String row)
	{
		HallType type = checkHallSize(HallID);
		ArrayList<Seat> specialSeats = new ArrayList<Seat>();
		MongoCollection<Document> seatCollection= database.getCollection(HallID);
		if (continuous == false)
		{
			if ("none".equals(area)) {
				Bson filter = and(eq("row",row), eq("occupied",false));
				MongoCursor<Document> cursor =  seatCollection.find(filter).iterator();
				while (cursor.hasNext() && specialSeats.size() < amount)
				{
//					System.out.println("popo");
					Seat seat;
					Document doc = cursor.next();
//					System.out.println("seatNum = " + doc.getInteger("seatNum"));
					if (type == HallType.BIG_HALL) {
						seat = new BigSeat(doc);
					} else {
						seat = new SmallSeat(doc);
					}

					specialSeats.add(seat);
				}
			} else if ("none".equals(row))  {
				Bson filter = and (eq("region", area), eq("occupied", false));
				MongoCursor<Document> cursor =  seatCollection.find(filter).iterator();
				while (cursor.hasNext()) {
					Seat seat;
					if (type == HallType.BIG_HALL) {
						seat = new BigSeat(cursor.next());
					} else {
						seat = new SmallSeat(cursor.next());
					}
					specialSeats.add(seat);
				}
			}
//			else { //two conditions are set
//				Bson myFilter = and(eq("area", area), eq("row", row));
//				MongoCursor<Document> cursor =  seatCollection.find(myFilter).iterator();
//				while (cursor.hasNext()) {
//					Seat seat;
//					if (type == HallType.BIG_HALL) {
//						seat = new BigSeat(cursor.next());
//					} else {
//						seat = new SmallSeat(cursor.next());
//					}
//					specialSeats.add(seat);
//				}
//			}
		} else { //continuous
			//TODO continuous is set
			if ("none".equals(area)) { //specific row
				MongoCursor<Document> cursor =  seatCollection.find(eq("row", row)).iterator();

				while (cursor.hasNext() && specialSeats.size() < amount) {
					Document doc = cursor.next();
					if(type == HallType.SMALL_HALL &&
							(doc.getInteger("seatNum") == 5 ||
									doc.getInteger("seatNum") == 13))
					{
						specialSeats.clear();
						if(doc.getBoolean("occupied").equals("false"))
						{
							Seat seat;
							seat = new SmallSeat(cursor.next());

							specialSeats.add(seat);
						}
					}
					else if(doc.getBoolean("occupied").equals("false"))
					{
						Seat seat;
						if (type == HallType.BIG_HALL) {
							seat = new BigSeat(doc);
						} else {
							seat = new SmallSeat(doc);
						}
						specialSeats.add(seat);
					}
					else
					{
						specialSeats.clear();
					}
				}
			}
			else if ("none".equals(row))  // continuous and specific area
			{
				MongoCursor<Document> cursor =  seatCollection.find(eq("region", area)).iterator();
				Document firstSeat = cursor.next();
				String currentRow = firstSeat.getString("row"); // first
				Seat seat;
				if (firstSeat.getBoolean("occupied") == false) {
					if (type == HallType.BIG_HALL) {
						seat = new BigSeat(firstSeat);
					} else {
						seat = new SmallSeat(firstSeat);
					}
					specialSeats.add(seat);
				}

				while (cursor.hasNext() && specialSeats.size() < amount) {
					Document doc = cursor.next();

					if (!doc.getString("row").equals(currentRow)) {
						specialSeats.clear();
					} else {
						currentRow = doc.getString("row");

						switch (area) {
							case "blue":
								if ("H".equals(currentRow) || "I".equals(currentRow) ||
									"J".equals(currentRow) || "K".equals(currentRow) ||
									"L".equals(currentRow))
								{
									if (doc.getInteger("seatNum") == 30 ||
											doc.getInteger("seatNum") == 32)
									{
										specialSeats.clear();
										if (doc.getBoolean("occupied").equals("false"))
										{
											seat = new BigSeat(doc);
											specialSeats.add(seat);
										}
									}

								}
								break;
							case "yellow":
								if ("I".equals(currentRow) || "J".equals(currentRow))
								{
									if (doc.getInteger("seatNum") == 28)
									{
										specialSeats.clear();
										if (doc.getBoolean("occupied").equals("false"))
										{
											seat = new BigSeat(doc);
											specialSeats.add(seat);
										}
									}
								}
								break;
							case "gray":
								if ("H".equals(currentRow) || "I".equals(currentRow) ||
										"J".equals(currentRow) || "K".equals(currentRow))
								{
									if (doc.getInteger("seatNum") == 32)
									{
										specialSeats.clear();
										if (doc.getBoolean("occupied").equals("false"))
										{
											seat = new BigSeat(doc);
											specialSeats.add(seat);
										}
									}
								}
								else if ("L".equals(currentRow))
								{
									if (doc.getInteger("seatNum") == 36)
									{
										specialSeats.clear();
										if (doc.getBoolean("occupied").equals("false"))
										{
											seat = new BigSeat(doc);
											specialSeats.add(seat);
										}
									}
								}
								break;
						}


						if (doc.getBoolean("occupied").equals("false"))
						{
							seat = new BigSeat(doc);
							specialSeats.add(seat);
						}
						else
						{
							specialSeats.clear();
						}
					}
				}
			}
		}


		if (specialSeats.size() == amount) {

			for (Seat s : specialSeats) {
				seatCollection.updateOne(eq("id", s.id),
						new Document("$set", new Document("occupied", true)));
			}

			return specialSeats.toArray(new Seat[0]);
		} else {
			return null;
		}
	}

	public int[] getSpecialRemain(String HallID) {

		int[] count = {0,0,0,0}; //gray, blue, yellow, red

		MongoCollection<Document> seatCollection= database.getCollection(HallID);
		MongoCursor<Document> cursor =  seatCollection.find(eq("occupied", false)).iterator();
		while (cursor.hasNext()) {

			switch(cursor.next().getString("region")) {
				case "gray":
					count[0]++;
					break;
				case "blue":
					count[1]++;
					break;
				case "yellow":
					count[2]++;
					break;
				case "red":
					count[3]++;
					break;
				default:
					System.out.println("I have a big cock!");
			}
		}
		return count;
	}

	public void cancelSeat(Ticket ticket) {
		String hallID = generateHallID(ticket.movieID, ticket.startTime);
		HallType type = checkHallSize(hallID);
		MongoCollection<Document> seatCollection = database.getCollection(hallID);

		String[] seatInfo = ticket.seatInfo.split("_");
		Bson myFilter = and(eq("row", seatInfo[0]), eq("seatNum", Integer.parseInt(seatInfo[1])));
		seatCollection.updateOne(myFilter, new Document("$set", new Document("occupied", false)));
	}

	public boolean checkSpecial(String hallID, int amount, String area, String row)
	{
		HallType type = checkHallSize(hallID);
		ArrayList<Seat> specialSeats = new ArrayList<>();
		MongoCollection<Document> seatCollection= database.getCollection(hallID);
		if ("none".equals(area)) {
			MongoCursor<Document> cursor = seatCollection.find(eq("row", row)).iterator();
			while (cursor.hasNext()) {
				Seat seat;
				if (type == HallType.BIG_HALL) {
					seat = new BigSeat(cursor.next());
				} else {
					seat = new SmallSeat(cursor.next());
				}
				specialSeats.add(seat);
			}
		} else if ("none".equals(row)) {
			MongoCursor<Document> cursor = seatCollection.find(eq("area", area)).iterator();
			while (cursor.hasNext()) {
				Seat seat;
				if (type == HallType.BIG_HALL) {
					seat = new BigSeat(cursor.next());
				} else {
					seat = new SmallSeat(cursor.next());
				}
				specialSeats.add(seat);
			}
		}
		if (specialSeats.size() < amount)
		{
			return  false;
		}
		else
		{
			return  true;
		}
	}

}
