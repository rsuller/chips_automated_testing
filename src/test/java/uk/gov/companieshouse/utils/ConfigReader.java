package uk.gov.companieshouse.utils;

import java.io.*;
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
                Paths.get("conf/config.properties"))){
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConfigProperty(String property) {
        loadProperties();
        if (null == properties.getProperty(property)) {
            throw new RuntimeException("The property " + property +
                    " cannot be found. Please check and try again.");
        }
        return properties.getProperty(property);
    }

}
