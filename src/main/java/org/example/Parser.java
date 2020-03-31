package org.example;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * @author Bischev Ramil
 * Класс для парсинга файла(файл должен лежать во временной папке ОС).
 */
public class Parser implements AutoCloseable{

    private final Config config = new Config();
    private Connection connect;
    private File file =  new File(System.getProperty("java.io.tmpdir") + "/access_old4.log");
    private List<Item> items = new ArrayList<>();



    public boolean connectToDB() {
        this.config.init();
        try {
            this.connect = DriverManager.getConnection(this.config.get("url"));
            connect.setAutoCommit(false);
            Statement st = connect.createStatement();
            st.execute("create table if not exists items (id serial primary key, ipAdress text(50), sectionName text(500), appVersion text(500), login text(500));");
            this.clearTable("items");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.connect != null;
    }

    public void clearTable(String tableName) {
        try (Statement statement = connect.createStatement()) {
            statement.execute("DELETE FROM " + tableName + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Парсим данные в ArrayList, когда количество записей равно 500_000, все данные из ArrayList переносятся в БД.
     * Это необходимо что бы Heap java не перегружался данными.
     */
    public void parserLog() {
        if (this.connectToDB()) {
            try(BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                String line;
                String ipAdress;
                String application;
                String appVersion;
                String login;
                String sectionName;
                line = reader.readLine();
                while((line = reader.readLine()) != null) {
                    if (line.contains("AppVersion=") && line.contains("Login=") && line.contains("SectionName=")) {
                        String[] entry = line.split("\t");
                        ipAdress = entry[0];
                        application = entry[5];
                        appVersion = getAppVersion(application);
                        login = getLogin(application);
                        sectionName = getSectionName(application);
                        this.items.add(new Item(ipAdress, appVersion, login, sectionName));
                    }
                    if (this.items.size() == 500000) {
                        this.loadToDB(this.items);
                        this.items.clear();
                    }
                }
                this.loadToDB(this.items);
                this.items.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String getAppVersion(String app) {
        return app.substring(app.lastIndexOf("AppVersion=") + 11, app.lastIndexOf("&L"));
    }

    private String getLogin(String app) {
        return app.substring(app.lastIndexOf("Login=") + 6, app.lastIndexOf("&S"));
    }

    private String getSectionName(String app) {
        return app.substring(app.lastIndexOf("SectionName=") + 12, app.length() - 1);
    }


    private void loadToDB(List<Item> l) {
        try (PreparedStatement statement = connect.prepareStatement(SQLItems.INSERT.query)) {
            for (Item item : l) {
                statement.setString(1, item.getIPadress());
                statement.setString(2, item.getSectionName());
                statement.setString(3, item.getAppVersion());
                statement.setString(4, item.getLogin());
                statement.addBatch();
            }
            statement.executeBatch();
            connect.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    enum SQLItems {
        INSERT("INSERT INTO items (ipAdress, sectionName, appVersion, login) VALUES (?, ?, ?, ?);");

        String query;

        SQLItems(String query) {
            this.query = query;
        }
    }

    @Override
    public void close() throws Exception {
        this.connect.close();
    }
}
