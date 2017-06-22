/**
 * Created by jameshuang304 on 2017/6/22.
 */
public class WatchThreeSmallException extends Exception {
    public WatchThreeSmallException(String classification, int age) {
        super("失敗，該電影分級為" + classification + "，" + age + "歲無法購買");
    }
}
