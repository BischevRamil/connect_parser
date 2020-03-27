package org.example;


import java.util.Date;

public class Item {

    private String IPadress;
    private String appVersion;
    private String login;
    private String sectionName;
    private String dateTime;

    public Item(String IPadress, String appVersion, String login, String sectionName, String dateTime) {
        this.IPadress = IPadress;
        this.appVersion = appVersion;
        this.login = login;
        this.sectionName = sectionName;
        this.dateTime = dateTime;
    }

    public String getIPadress() {
        return IPadress;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getLogin() {
        return login;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Item{" +
                "IPadress='" + IPadress + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", login='" + login + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
