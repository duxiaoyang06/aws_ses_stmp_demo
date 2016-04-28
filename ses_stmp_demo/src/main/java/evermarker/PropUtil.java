package evermarker;


import java.util.Properties;



public class PropUtil {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(PropUtil.class.getResourceAsStream("/app.properties"));
        } catch (Exception exc) {
            System.out.println("Couldn't load the properties file: " + exc.getMessage());
        }
    }
    public static String get(String key) {
        return properties.getProperty(key);
    }

}
