package org.example;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Bischev Ramil
 * Класс конфигурации для подключению к базе данных SQLite3.
 */
public class Config {

    private final Properties values = new Properties();

    public void init() {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("AppSQLite.properties")) {
            values.load(in);
            Class.forName(values.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String get(String key) {
        return this.values.getProperty(key);
    }
}
