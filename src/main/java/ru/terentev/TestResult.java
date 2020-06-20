package ru.terentev;

public enum TestResult {
    Passed("PASSED"),
    Failed("FAILED"),
    Untested("Untested"),  //not allowed when adding a result
    Retest("Retest"),
    Blocked("Blocked");


    private String value;
    private TestResult(String id){value=id;}
    public static TestResult transformNGtoTR(int result){
        switch (result){
            case 1:return TestResult.Passed;
            case 2:return TestResult.Failed;
            case 3:return TestResult.Retest;
            case 4:return TestResult.Failed;

            default:return TestResult.Blocked;
        }
    }

}
