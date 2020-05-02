package com.github.eunsang.grpc.greeting.server;

import com.proto.greet.*;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();
        String lastName = greeting.getLastName();

        String result = "Hello " + firstName + " " + lastName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();
        String lastName = request.getGreeting().getFirstName();

        try {
            for (int i = 0; i < 10; i++) {
                String result = "Hello " + firstName + " " + lastName + ", response #: " + i;
                GreetManyTimesResponse greetManyTimesResponse = GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(greetManyTimesResponse);
                Thread.sleep(200L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<LongGreetRequest>
        longGreet(StreamObserver<LongGreetResponse> responseObserver) {

        StreamObserver<LongGreetRequest> requestObserver =
                new StreamObserver<LongGreetRequest>() {

            String result = "";

            @Override
            public void onNext(LongGreetRequest value) {
                // client sends a message
                result += "Hello, " +
                        value.getGreeting().getFirstName() +
                        " " +
                        value.getGreeting().getLastName() +
                        ".\n";
            }

            @Override
            public void onError(Throwable t) {
                // client sends an error
                System.out.println("Client Error");
            }

            @Override
            public void onCompleted() {
                // client is done
                responseObserver.onNext(
                        LongGreetResponse.newBuilder()
                            .setResult(result)
                            .build()
                );

                responseObserver.onCompleted();
                // this is when we wanna return a response using responseObserver
            }
        };

        return requestObserver;
    }

    @Override
    public StreamObserver<GreetEveryoneRequest>
        greetEveryone(StreamObserver<GreetEveryoneResponse> responseObserver) {

        StreamObserver<GreetEveryoneRequest> requestObserver =
                new StreamObserver<GreetEveryoneRequest>() {
            @Override
            public void onNext(GreetEveryoneRequest value) {
                System.out.println("got client request");
                String result = "Hello, " +
                        value.getGreeting().getFirstName() +
                        " " +
                        value.getGreeting().getLastName();
                GreetEveryoneResponse greetEveryoneResponse = GreetEveryoneResponse.newBuilder()
                        .setResult(result)
                        .build();
                responseObserver.onNext(greetEveryoneResponse);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("got client error");
            }

            @Override
            public void onCompleted() {
                System.out.println("got client done");
                responseObserver.onCompleted();
            }
        };

        return requestObserver;
    }

    @Override
    public void greetWithDeadline(GreetWithDeadlineRequest request,
                                  StreamObserver<GreetWithDeadlineResponse> responseObserver) {

        Context current = Context.current();

        try {
            for (int i = 0; i < 3; i++) {
                if(!current.isCancelled()) {
                    System.out.println("sleep 100ms");
                    Thread.sleep(100);
                }
                else {
                    return;
                }
            }

            System.out.println("send response");

            responseObserver.onNext(
                    GreetWithDeadlineResponse.newBuilder()
                            .setResult("Hello, " + request.getGreeting().getFirstName())
                            .build()
            );

            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
