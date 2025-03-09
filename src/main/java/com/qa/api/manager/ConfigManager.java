package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    static {

        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties")) {

            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProp(String key) {
        return properties.getProperty(key);
    }

    public static void setProp(String key, String value) {
        properties.setProperty(key, value);
    }

}
