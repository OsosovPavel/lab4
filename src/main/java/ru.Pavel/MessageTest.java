package ru.Pavel;

public class MessageTest {
    private Integer packageId;
    private String jsScript;
    private String functionName;
    private Test test;

    public MessageTest(Integer packageId, String jsScript, String functionName, Test test) {
        this.packageId = packageId;
        this.jsScript = jsScript;
        this.functionName = functionName;
        this.test = test;
    }
    public Integer getPackageId() {
        return packageId;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public Test getTest() {return this.test;}
}
