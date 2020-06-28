package ru.terentev;

public class CollectorListenersCapabilities {
    private static Boolean useTMSTestName = false;
    private static String url;
    private static String username;
    private static String password;
    private static Boolean enable = true;

    public static void setCollectDataOnlyInJenkins(Boolean collectDataOnlyInJenkins) {
        CollectorListenersCapabilities.collectDataOnlyInJenkins = collectDataOnlyInJenkins;
    }

    private static Boolean collectDataOnlyInJenkins = false;
    private static String dBUrl;
    private static String dBUser;
    private static String dBPassword;

    public static String getdBUrl() {
        return dBUrl;
    }

    public static void setdBUrl(String dBUrl) {
        CollectorListenersCapabilities.dBUrl = dBUrl;
    }

    public static String getdBUser() {
        return dBUser;
    }

    public static void setdBUser(String dBUser) {
        CollectorListenersCapabilities.dBUser = dBUser;
    }

    public static String getdBPassword() {
        return dBPassword;
    }

    public static void setdBPassword(String dBPassword) {
        CollectorListenersCapabilities.dBPassword = dBPassword;
    }

    public static String getTMSUrl() {
        return url;
    }

    public static void setTMSUrl(String url) {
        CollectorListenersCapabilities.url = url;
    }

    public static void setUseTMSTestName(boolean useTMSTestName) {
        CollectorListenersCapabilities.useTMSTestName = useTMSTestName;
    }

    public static void enable() {
        enable = true;
    }

    public static void disable() {
        enable = false;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CollectorListenersCapabilities.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CollectorListenersCapabilities.username = username;
    }

    public static Boolean getUseTMSTestName() {
        return useTMSTestName;
    }

    public static Boolean getEnable() {
        return enable;
    }

    public static Boolean getCollectDataOnlyInJenkins() {
        return collectDataOnlyInJenkins;
    }
}
