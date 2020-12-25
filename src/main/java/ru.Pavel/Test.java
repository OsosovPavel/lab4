package ru.Pavel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Test {
    private final String TEST_NAME = "testName";
    private final String EXPECTED_RESULT = "expectedResult";
    private final String PARAMS = "params";
    private final String RESULT = "result";

    @JsonProperty(TEST_NAME)
    private String testName;

    @JsonProperty(EXPECTED_RESULT)
    private String expectedResult;

    @JsonProperty(PARAMS)
    private ArrayList<Integer> params;

    @JsonProperty(RESULT)
    private boolean result;

    @JsonCreator
    public Test(String testName, String expectedResult, ArrayList<Integer> params, boolean result) {
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = result;
    }
    public String getTestName() {
        return this.testName;
    }

    public String getExpectedResult() {
        return this.expectedResult;
    }

    public ArrayList<Integer> getParams() {return this.params;}

    public boolean getResult() {return this.result;}
}
