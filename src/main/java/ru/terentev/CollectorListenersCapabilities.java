package ru.terentev;

public class CollectorListenersCapabilities {
    private static Boolean useTMSTestName=false;
    private static String url;
    private static String username;
    private static String password;
    private static Boolean enable=true;
    private static Boolean runOnlyInJenkins=false;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        CollectorListenersCapabilities.url = url;
    }

    public static void uploadTMSTestName(){
        useTMSTestName=true;
    }

    public static void notUploadTMSTestName(){
        useTMSTestName=false;
    }

    public static void enable(){
        enable=true;
    }

    public void disable(){
        enable=false;
    }

    public void collectOnlyInJenkins(){
        runOnlyInJenkins=true;
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

    public static Boolean getRunOnlyInJenkins() {
        return runOnlyInJenkins;
    }
}
