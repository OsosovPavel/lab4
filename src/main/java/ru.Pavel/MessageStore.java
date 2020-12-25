package ru.Pavel;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MessageStore {
    private final String PACKAGE_ID = "packageId";
    private final String TEST = "test";

    @JsonProperty(PACKAGE_ID)
    private Integer packageId;

    @JsonProperty(TEST)
    private ArrayList<Test> test;

    @JsonCreator
    public MessageStore(@JsonProperty(PACKAGE_ID) Integer packageId, @JsonProperty(TEST) ArrayList<Test> test) {
        this.packageId = packageId;
        this.test = test;
    }

    public Integer getPackageId() {return this.packageId;}

    public ArrayList<Test> getTest() {return this.test;}
}
