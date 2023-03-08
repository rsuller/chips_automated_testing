package uk.gov.companieshouse.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

    Properties properties;

    public ConfigReader(Properties properties) {
        this.properties = properties;
    }

    private void loadProperties() {
        try (InputStream input = Files.newInputStream(
                Paths.get("conf/config.properties"))) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Load a specific property from the config file.
     * @param property the property to load.
     */
    public String getConfigProperty(String property) {
        loadProperties();
        if (null == properties.getProperty(property)) {
            throw new RuntimeException("The property " + property + " cannot be found. Please check and try again.");
        }
        return properties.getProperty(property);
    }

}
