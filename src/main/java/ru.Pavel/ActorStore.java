package ru.Pavel;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActorStore extends AbstractActor {
    private Map<Integer>, ArrayList<Test>> store = new HashMap<Integer>, ArrayList<Test>>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(MessageStore.class, m -> {
                    if (store.containsKey(m.getPackageId())) {
                        ArrayList<Test> current_tests = store.get(m.getPackageId());
                        current_tests.addAll(m.getTest());
                        store.replace(m.getPackageId(), current_tests);
                    } else {
                        store.put(m.getPackageId(), m.getTest());
                    }
                })
                .match(MessageGet.class, reg -> {
                    sender().tell(
                            new MessageStore(req.getPackageId(), store.get(req.getPackageId())), self();
                    )
                })
                .build();
    }
}
