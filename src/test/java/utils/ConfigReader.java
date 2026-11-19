package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    // Static block runs once when the class is first loaded
    static {
        // 1. Read "-Denv" from terminal; default to "qa" if running from VS Code UI
        String env = System.getProperty("env", "qa").toLowerCase();
        String fileName = env + ".properties";

        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                properties.load(input);
                System.out.println("Loaded local properties file: " + fileName);
            } else {
                // Does not crash in CI/CD when .properties files are gitignored
                System.out.println("No local .properties file found. Using CI/CD environment variables.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment properties: " + fileName, e);
        }
    }

    // Method to fetch any key from the loaded properties file
    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in the active .properties file.");
        }
        return value;
    }
}
