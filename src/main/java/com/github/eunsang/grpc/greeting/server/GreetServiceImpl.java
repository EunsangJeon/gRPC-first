package com.github.eunsang.grpc.greeting.server;

import com.proto.greet.*;
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
}
