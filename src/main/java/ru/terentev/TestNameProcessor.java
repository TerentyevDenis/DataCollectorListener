package ru.terentev;

import org.json.simple.JSONObject;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import ru.terentev.testrailAPI.APIClient;
import ru.terentev.testrailAPI.APIException;

import java.io.IOException;

public class TestNameProcessor {
    public static String getTestName(IInvokedMethod method, ITestResult testResult,String case_id){
        String name;
        if (CollectorListenersCapabilities.getUseTMSTestName()){
            name = getNameFromTMS(case_id);
        }else {
            if (method.getTestMethod().getDescription().isEmpty()) {
                name = method.getTestMethod().getQualifiedName();
            } else {
                name = method.getTestMethod().getTestClass().getName() + method.getTestMethod().getDescription();
            }
        }
        return name;
    }

    private static String getNameFromTMS(String tms_id){
        if (CollectorListenersCapabilities.getUsername()==null){
            throw  new IllegalStateException("TestRail username doesn't set. Define it using CollectorListenersCapabilities.setUsername(\"username\");");
        }

        if (CollectorListenersCapabilities.getPassword()==null){
            throw  new IllegalStateException("TestRail password doesn't set. Define it using CollectorListenersCapabilities.setPassword(\"password\");");
        }

        if (CollectorListenersCapabilities.getPassword()==null){
            throw  new IllegalStateException("TestRail uri doesn't set. Define it using CollectorListenersCapabilities.setUrl(\"url\");");
        }

        APIClient client = new APIClient(CollectorListenersCapabilities.getUrl());
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
        if (c==null){
            return null;
        }
        return (String) c.get("title");
    }

    public static String getCaseIdFromURL(String URL){
        String[] id= URL.split("/");
        return id[id.length-1];
    }

}
