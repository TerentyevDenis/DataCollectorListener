package ru.terentev;

import io.qameta.allure.TmsLink;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import ru.terentev.Models.Test;
import ru.terentev.Models.TestResult;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static ru.terentev.DBhelper.createTable;
import static ru.terentev.DBhelper.putTestInDB;
import static ru.terentev.TestNameProcessor.getCaseIdFromURL;

public class TestNGCollectorListener implements IInvokedMethodListener {
    static {
        createTable();
    }
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            Test test = new Test();
            TmsLink tmsURL = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TmsLink.class);
            String caseId = null;
            if (tmsURL == null) {
                Object atr = testResult.getAttribute("TMS_ID");
                if (atr != null) {
                    caseId = getCaseIdFromURL(atr.toString());
                }
            } else {
                caseId = getCaseIdFromURL(tmsURL.value());
            }
            test.setTms_id(caseId);
            if (CollectorListenersCapabilities.getUrl()!=null) {
                String url = CollectorListenersCapabilities.getUrl();
                if (!url.endsWith("/"))
                {
                    url += "/";
                }
                test.setTms_url(url + "index.php?/cases/view/" + caseId);
            }
            test.setTestName(TestNameProcessor.getTestName(method,testResult,caseId));
            test.setElapsedTime(testResult.getEndMillis() - testResult.getStartMillis());
            test.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(testResult.getStartMillis()), ZoneId.systemDefault()));
            test.setTestResult(TestResult.transformNGtoTR(testResult.getStatus()));
            System.out.println(test);
            putTestInDB(test);
        }
    }
}
