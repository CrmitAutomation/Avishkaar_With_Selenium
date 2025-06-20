package utils;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class configReader {

	public static Properties initProperties() {
		Properties prop = new Properties();
		try {
			// To read the config.properties file we use below method by creating object
			FileReader reader = new FileReader(
					System.getProperty("user.dir") + "\\src\\main\\resources\\configFiles\\config.properties");

			// To load the config file
			prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}