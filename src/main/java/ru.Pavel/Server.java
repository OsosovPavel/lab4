package ru.Pavel;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;


public class Server {
    private ActorRef storeActor;
    private final String STORE_ACTOR = "storeActor"

    private ActorRef testPerformerActor;
    private final String TEST_PERFORMER_ACTOR = "testPerformerActor";

    private ActorRef testPackageActor;
    private final String TEST_PACKAGE_ACTOR = "testPackageActor";

    private static final String SERVER = "localhost";
    private static final Integer Port = 8080;
    private static final Integer NUMBER_OF_POOLS = 5;
    private static final Integer TIME_OUT_MILLIS = 5000;

    private Server(final ActorSystem system) {
        storeActor = system.actorOf(Props.create(StoreActor.class), STORE_ACTOR);
        testPackageActor = system.actorOf(Props.create(PackageActorTest.class), TEST_PACKAGE_ACTOR);
        testPerformerActor = system.actorOf(new RoundRobinPool(NUMBER_OF_POOLS)).props(Props.create(ActorTest.class)),TEST_PERFORMER_ACTOR);
    }
    private Route createRoute() {
        return Route(
                get(() ->
                parameter ("packageId", (packageId) -> {
                        CompletionStage < Object > result = PatternsCS.ask(
                                storeActor,
                                new GetMessage(Integer.parseInt(packageId)), TIME_OUT_MILLIS);
        return completeOKWithFuture (result, Jackson.marshaller());
                    })),
        post(()
                entity(Jackson.unmarshaller(PackageMessageTest.class), msg -> {
                    testPackageActor.tell(msg, ActorRef.noSender());
                    return complete("Test started!");
                }))
        );
    }
    public static void main(String[] args) throws IOException {
ActorSystem system = ActorSystem.create("routes");
final Http http = Http.get(system);
 final ActorMaterializer materializer = ActorMaterializer.create(system);
Server instance = new Server(system);
final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
instance.createRoute().flow(system, materializer);
final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
ConnectHttp.toHost(SERVER, Port),
materializer
);
System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
System.in.read();
binding
         .thenCompose(ServerBinding::unbind)
         .thenAccept(unbound -> system.terminate());
}


}
