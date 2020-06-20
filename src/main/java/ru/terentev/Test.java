package ru.terentev;

import java.time.LocalDateTime;

public class Test {
    private String tms_id;
    private String tms_url;
    private String testName;
    private Long elapsedTime;
    private TestResult testResult;
    private LocalDateTime startTime;
    private String Build_id;
    private String Build_URL;

    public String getTms_url() {
        return tms_url;
    }

    public void setTms_url(String tms_url) {
        this.tms_url = tms_url;
    }

    public String getTms_id() {
        return tms_id;
    }

    public void setTms_id(String tms_id) {
        this.tms_id = tms_id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getBuild_id() {
        return Build_id;
    }

    public void setBuild_id(String build_id) {
        Build_id = build_id;
    }

    public String getBuild_URL() {
        return Build_URL;
    }

    public void setBuild_URL(String build_URL) {
        Build_URL = build_URL;
    }

    @Override
    public String toString() {
        return "Test{" +
                "tms_id='" + tms_id + '\'' +
                ", tms_url='" + tms_url + '\'' +
                ", testName='" + testName + '\'' +
                ", elapsedTime=" + elapsedTime +
                ", testResult=" + testResult +
                ", startTime=" + startTime +
                ", Build_id='" + Build_id + '\'' +
                ", Build_URL='" + Build_URL + '\'' +
                '}';
    }
}
