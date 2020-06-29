package ru.terentev.Models;

public class EnvCollector {
    static String BUILD_ID = "BUILD_ID";
    static String BUILD_URL = "BUILD_URL";
    static String getBuildId(){
       return System.getenv(BUILD_ID);
    }

    static String getBuildURL(){
        return System.getenv(BUILD_URL);
    }
}
