package ru.terentev.Models;

import io.qameta.allure.TmsLink;
import org.json.simple.JSONObject;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import ru.terentev.CollectorListenersCapabilities;
import ru.terentev.testrailAPI.APIClient;
import ru.terentev.testrailAPI.APIException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static ru.terentev.Models.EnvCollector.getBuildId;
import static ru.terentev.Models.EnvCollector.getBuildURL;


public class Test {
    private String tms_id;
    private String tms_url;
    private String testName;
    private String testSuite;
    private String testSection;
    private Long elapsedTime;
    private TestResult testResult;
    private LocalDateTime startTime;
    private String build_id;
    private String build_URL;
    private IInvokedMethod method;
    private ITestResult result;

    public String getTms_id() {
        return tms_id;
    }

    public String getTms_url() {
        return tms_url;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestSuite() {
        return testSuite;
    }

    public String getTestSection() {
        return testSection;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getBuild_id() {
        return build_id;
    }

    public String getBuild_URL() {
        return build_URL;
    }

    public void generateTest(IInvokedMethod method, ITestResult result){
        this.method = method;
        this.result = result;
        TmsLink tmsURL = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TmsLink.class);
        String caseId = null;
        if (tmsURL == null) {
            Object atr = result.getAttribute("TMS_ID");
            if (atr != null) {
                caseId = getCaseIdFromURL(atr.toString());
            }
        } else {
            caseId = getCaseIdFromURL(tmsURL.value());
        }
        this.tms_id=caseId;
        if (CollectorListenersCapabilities.getTMSUrl()!=null) {
            String url = CollectorListenersCapabilities.getTMSUrl();
            if (!url.endsWith("/"))
            {
                url += "/";
            }
            this.tms_url=url + "index.php?/cases/view/" + caseId;
        }
        this.elapsedTime=result.getEndMillis() - result.getStartMillis();
        this.startTime=LocalDateTime.ofInstant(Instant.ofEpochMilli(result.getStartMillis()), ZoneId.systemDefault());
        this.testResult=TestResult.transformNGtoTR(result.getStatus());
        String buildId = getBuildId();
        String buildURL = getBuildURL();
        if (buildId!=null){
            this.build_id=buildId;
        }
        if (buildURL!=null){
            this.build_URL=buildURL;
        }
    }

    @Override
    public String toString() {
        return "Test{" +
                "tms_id='" + tms_id + '\'' +
                ", tms_url='" + tms_url + '\'' +
                ", testName='" + testName + '\'' +
                ", testSuite='" + testSuite + '\'' +
                ", testSection='" + testSection + '\'' +
                ", elapsedTime=" + elapsedTime +
                ", testResult=" + testResult +
                ", startTime=" + startTime +
                ", build_id='" + build_id + '\'' +
                ", build_URL='" + build_URL + '\'' +
                '}';
    }

    public void updateTestName(){
        if (CollectorListenersCapabilities.getUseTMSTestName()){
            getNameFromTMS(tms_id);
        }else {
            if (method.getTestMethod().getDescription().isEmpty()) {
                this.testName = method.getTestMethod().getQualifiedName();
            } else {
                this.testName = method.getTestMethod().getTestClass().getName() + method.getTestMethod().getDescription();
            }
        }
    }

    private void getNameFromTMS(String tms_id){
        if (CollectorListenersCapabilities.getUsername()==null){
            throw  new IllegalStateException("TestRail username doesn't set. Define it using CollectorListenersCapabilities.setUsername(\"username\");");
        }

        if (CollectorListenersCapabilities.getPassword()==null){
            throw  new IllegalStateException("TestRail password doesn't set. Define it using CollectorListenersCapabilities.setPassword(\"password\");");
        }

        if (CollectorListenersCapabilities.getTMSUrl()==null){
            throw  new IllegalStateException("TestRail uri doesn't set. Define it using CollectorListenersCapabilities.setUrl(\"url\");");
        }

        APIClient client = new APIClient(CollectorListenersCapabilities.getTMSUrl());
        client.setUser(CollectorListenersCapabilities.getUsername());
        client.setPassword(CollectorListenersCapabilities.getPassword());
        JSONObject c = null;
        try {
            c = (JSONObject) client.sendGet("get_case/"+ tms_id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        }
        if (c!=null){
            this.testName = (String) c.get("title");
        }
        Long suiteId = (Long) c.get("suite_id");
        Long sectionId =(Long) c.get("section_id");
        if (suiteId!=null){
            this.testSuite=getInformationFromTMS("get_suite",suiteId,"name",client);
        }
        if (sectionId!=null){
            this.testSection=getInformationFromTMS("get_section",sectionId,"name",client);
        }
    }

    private String getInformationFromTMS(String request, Long id, String title, APIClient client){
            JSONObject resp = null;
            try {
                resp = (JSONObject) client.sendGet(request+"/"+ id);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
            if (resp!=null){
                return (String) resp.get(title);
            }
        return null;
    }

    private String getCaseIdFromURL(String URL){
        String[] id= URL.split("/");
        return id[id.length-1];
    }
}
