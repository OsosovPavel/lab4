package ru.Pavel;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PackageMessageTest {
    private final String PACKAGE_ID = "packageId";
    private final String JS_SCRIPT = "jsScript";
    private final String FUNCTION_NAME = "functionName";
    private final String TESTS = "tests";

    @JsonProperty(PACKAGE_ID)
    private Integer packageId;

    @JsonProperty(JS_SCRIPT)
    private String jsScript;

    @JsonProperty(FUNCTION_NAME)
    private String functionName;

    @JsonProperty(TESTS)
    private ArrayList<Test> tests;

    @JsonCreator
    PackageMessageTest(@JsonProperty(PACKAGE_ID)int packageId, @JsonProperty(JS_SCRIPT) String jsScript,
                       @JsonProperty(FUNCTION_NAME) String functionName, @JsonProperty(TESTS) ArrayList<Test> tests ) {
        this.packageId = packageId;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }
    public Integer getPackageId() {
        return this.packageId;
    }

    public String getJsScript() {
        return this.jsScript;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public ArrayList<Test> getTests() {
        return this.tests;
    }
}

