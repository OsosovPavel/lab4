package ru.Pavel;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;


public class ActorTest extends AbstractActor{
  private ActorSelection actorStore = getContext().actorSelection("/user/actorStore");
  private final String SCRIPT_BY_NAME = "nashorn";

  private ArrayList<Test> runTest(String jsScript, String functionName, String testName,
                                  String expectedResult, ArrayList<Integer> params) throws ScriptException, NoSuchMethodException {
      ScriptEngine engine = new ScriptEngineManager().getEngineByName(SCRIPT_BY_NAME);
      engine.eval(jsScript);
      Invocable invocable = (Invocable) engine;
      String result = invocable.invokeFunction(functionName, params.toArray()).toString();

      Test test = new Test(testName, expectedResult, params, expectedResult.equals(result));
      ArrayList<Test> currentTests = new ArrayList<Test>();
      currentTests.add(test);
      return currentTests;
  }

  @Override
    public Receive createReceive() {
      return ReceiveBuilder.create()
              .match(MessageTest.class, m -> {
                  ActorStore.tell(new MessageStore(m.getPackageId(),
                          runTest(m.getJsScript(), m.getFunctionName(), m.getTest().getTestName(),
                                  m.getTest().getExpectedResult(), m.getTest().getParams())),
                  self());
              })
              .build();
  }
}
