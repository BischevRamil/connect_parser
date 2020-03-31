package org.example;

public class Item {

    private String IPadress;
    private String appVersion;
    private String login;
    private String sectionName;

    public Item(String IPadress, String appVersion, String login, String sectionName) {
        this.IPadress = IPadress;
        this.appVersion = appVersion;
        this.login = login;
        this.sectionName = sectionName;
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

}
