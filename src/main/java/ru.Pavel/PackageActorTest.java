package ru.Pavel;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;



public class PackageActorTest extends AbstractActor {
 private  ActorSelection testPerformerRouter = getContext().actorSelection("/user/testPerformerActor");

 public Receive createReveive() {
     return ReceiveBuilder.create()
             .match(PackageMessageTest.class, m -> {
                 for (Test test: m.getTests()) {
                     testPerformerRouter
                             .tell(new TestMessage(m.getPackageId(), m.getJsScript(), m.getFunctionName(), test), self());
                 }
             })
             .build()
 }
}
