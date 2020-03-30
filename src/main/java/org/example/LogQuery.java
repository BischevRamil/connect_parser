package org.example;

import java.sql.*;

public class LogQuery implements AutoCloseable{
    private final Config config = new Config();
    private Connection connect;

    public LogQuery() {
        connectToDB();
    }

    public boolean connectToDB() {
        this.config.init();
        try {
            this.connect = DriverManager.getConnection(this.config.get("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.connect != null;
    }

    public Integer allRecords() {
        int rows = 0;
        try (Statement statement = this.connect.createStatement()){
            ResultSet rs = statement.executeQuery("select count(*) from items;");
            rs.next();
            rows = rs.getInt(1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public Integer returnRecordQuonty(String field) {
        int rows = 0;
        try (Statement statement = this.connect.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(DISTINCT " + field + ") FROM items;");
            while(rs.next()) {
                rows = rs.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public String[] returnRecordMax(String field) {
        String[] fld = new String[2];
        try (Statement statement = this.connect.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT " + field + ", count(*) c FROM items GROUP BY " + field + " ORDER BY c DESC LIMIT 1;");
            rs.next();
            fld[0] = rs.getString(1);
            fld[1] = rs.getString(2);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return fld;
    }


    @Override
    public void close() throws Exception {
        this.connect.close();
    }
}
