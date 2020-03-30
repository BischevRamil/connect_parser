package org.example;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Parser implements AutoCloseable{

    private final Config config = new Config();
    private Connection connect;
    private File file =  new File(System.getProperty("java.io.tmpdir") + "/access_old4.log");
    private List<Item> items = new ArrayList<>();
   // private SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);



    public boolean connectToDB() {
        this.config.init();
        try {
            this.connect = DriverManager.getConnection(this.config.get("url"));
            connect.setAutoCommit(false);
            Statement st = connect.createStatement();
            st.execute("create table if not exists items (id serial primary key, ipAdress text(50), sectionName text(500), appVersion text(500), login text(500), dateTime text(100));");
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

    public void parserLog() {
        if (this.connectToDB()) {
            try(BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                String line;
                String ipAdress;
                String application;
                String appVersion;
                String login;
                String sectionName;
                String dateTime;
                line = reader.readLine();
                while((line = reader.readLine()) != null) {
                    if (line.contains("AppVersion=") && line.contains("Login=") && line.contains("SectionName=")) {
                        String[] entry = line.split("\t");
                        ipAdress = entry[0];
                        dateTime = entry[3].substring(1, entry[3].length() - 7);
                        application = entry[5];
                        appVersion = getAppVersion(application);
                        login = getLogin(application);
                        sectionName = getSectionName(application);
                        this.items.add(new Item(ipAdress, appVersion, login, sectionName, dateTime));
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
                statement.setString(5, item.getDateTime());
                statement.addBatch();
            }
            statement.executeBatch();
            connect.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    enum SQLItems {
        GETALL("SELECT * FROM items;"),
        INSERT("INSERT INTO items (ipAdress, sectionName, appVersion, login, dateTime) VALUES (?, ?, ?, ?, ?);");

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
