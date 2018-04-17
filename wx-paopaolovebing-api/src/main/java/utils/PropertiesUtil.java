package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties p = null;

	public static String getValue(String name) {
		if (p == null) {
			p = new Properties();
			InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
			try {
				System.out.println("resourceAsStream" + resourceAsStream);
				p.load(resourceAsStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p.getProperty(name);
	}

	public static void main(String[] args) {
		String value = PropertiesUtil.getValue("jdbc.url");
		System.out.println(value);
	}
}
