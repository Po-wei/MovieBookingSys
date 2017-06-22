/**
 * Created by jameshuang304 on 2017/6/22.
 */
public class SeatNotEnoughException extends Exception {

    public SeatNotEnoughException(String movieID, String time) {
        super("失敗，" + movieID + "於" + time + "座位數量不夠");
    }

}
