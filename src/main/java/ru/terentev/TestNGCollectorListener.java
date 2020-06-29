package ru.terentev;

import io.qameta.allure.TmsLink;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import ru.terentev.Models.Test;

import static ru.terentev.DBhelper.createTable;
import static ru.terentev.DBhelper.putTestInDB;

public class TestNGCollectorListener implements IInvokedMethodListener {
    static {
        createTable();
    }
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            Test test = new Test();
            test.generateTest(method,testResult);
            if (CollectorListenersCapabilities.getCollectDataOnlyInJenkins()&&test.getBuild_id()!=null||!CollectorListenersCapabilities.getCollectDataOnlyInJenkins()) {
                putTestInDB(test);
            }
        }
    }
}
