/**
 * Created by jameshuang304 on 2017/6/22.
 */
public class WrongRegionException extends Exception {
    public WrongRegionException () {
        super("失敗，該電影放映廳為小廳，無法指定區域");
    }
}
