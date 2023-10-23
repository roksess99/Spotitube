package nl.han.oose.dea.dao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {
    private static final String PROPERTIES_FILE = "./src/main/resources/database.properties";
    private Properties properties;

    public DatabaseProperties() {
        loadProperties();
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Unable to find " + PROPERTIES_FILE + " in the classpath.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }
}

