package org.selenium.pom.management.tool;


import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;

import java.lang.reflect.Method;
import java.util.List;

public class TestRailClient {
    private static final String testRailUrl = "https://yourdomain.testrail.com/";
    private static final String testRailUsername = "yourusername";
    private static final String testRailPassword = "your-password";
    private TestRail testRail;
    private Run testRun;
    private int testRunId;
    private int testSuiteId;
    private int projectId;

    public TestRailClient(){
        testRail = TestRail.
                builder(testRailUrl,testRailUsername,testRailPassword).
                applicationName("Test Automation").
                build();
    }
    public void beforeTestResult(Method method){
        projectId =1;
        testSuiteId =1;
        String testRunName = method.getDeclaringClass().getSimpleName();
        testRun = testRail.runs().add(projectId,new Run().setSuiteId(testSuiteId).setName(testRunName)).execute();
        testRunId = testRun.getId();
    }
    public void  addTestResult(String name, String status) throws Exception {
        int caseId = getCaseId(name);
        List<ResultField> resultFields = testRail.resultFields().list().execute();
        testRail.results().addForCase(testRunId,caseId,new Result().setStatusId(getStatusId(status)),resultFields);
    }

    private int getCaseId(String methodName) throws Exception {
        List<CaseField> caseFields = testRail.caseFields().list().execute();
        for(Case testCase: testRail.cases().list(projectId,testSuiteId, caseFields).execute())
        {
            if(testCase.getTitle().equals(methodName)){
                return testCase.getId();
            }
        }
        throw new Exception("No matching test case found for" +methodName);
    }

    private int getStatusId(String status){
        return switch (status.toLowerCase()) {
            case "pass" -> 1;
            case "fail" -> 5;
            default -> 6;
        };
    }
}
