import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class TestProp {
	private static Properties properties;
	static {
		try {
			ClassPathResource resource = new ClassPathResource("test.properties", TestProp.class);
			properties = PropertiesLoaderUtils.loadProperties(resource);
			System.out.println(properties.getProperty("jbc"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getValue(String name) {
		return properties.getProperty(name);
	}

	public static void main(String[] args) {
		String value = TestProp.getValue("test");
		System.out.println(value);
	}
}
