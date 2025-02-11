package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class configReader {

    public static String readKey(String key) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        fis = new FileInputStream("./src/test/resources/config.properties");
        prop = new Properties();
        prop.load(fis);
        fis.close();
        return prop.getProperty(key);
    }
}
