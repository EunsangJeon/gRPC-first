package com.github.eunsang.grpc.greeting.client;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingClient {

    private  ManagedChannel channel =
            ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext() // only for dev to avoid secure setting
            .build();
    private Greeting greeting = Greeting.newBuilder()
            .setFirstName("Eunsang")
            .setLastName("Jeon")
            .build();
    private GreetServiceGrpc.GreetServiceBlockingStub greetSyncClient =
            GreetServiceGrpc.newBlockingStub(channel);

    private GreetServiceGrpc.GreetServiceStub greetAsyncClient =
            GreetServiceGrpc.newStub(channel);

    public void run() {
        // doUnaryCall();
        // doServerStreamingCall();
        // doClientStreamingCall();
        doBidiStreamingCall();

        System.out.print("Shutting down channel");
        channel.shutdown();
    }

    private void doUnaryCall() {
        // unary
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        GreetResponse greetResponse = greetSyncClient.greet(greetRequest);
        System.out.println(greetResponse.getResult());
    }

    private void doServerStreamingCall(){
        // server streaming
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        greetSyncClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> {
                    System.out.println(greetManyTimesResponse.getResult());
                });
    }

    private void doClientStreamingCall() {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver =
                greetAsyncClient.longGreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse value) {
                System.out.println("Received a response from the server:");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Server error");
            }

            @Override
            public void onCompleted() {
                System.out.println("Server stopped sending messages");
                latch.countDown();
            }
        });

        System.out.println("sending message");
        requestObserver.onNext(
                LongGreetRequest.newBuilder()
                    .setGreeting(
                            Greeting.newBuilder()
                                    .setFirstName("John")
                                    .setLastName("Lennon")
                                    .build()
                    )
                    .build()
        );

        System.out.println("sending message");
        requestObserver.onNext(
                LongGreetRequest.newBuilder()
                    .setGreeting(
                            Greeting.newBuilder()
                                    .setFirstName("Paul")
                                    .setLastName("McCartney")
                                    .build()
                    )
                    .build()
        );

        System.out.println("sending message");
        requestObserver.onNext(
                LongGreetRequest.newBuilder()
                    .setGreeting(
                            Greeting.newBuilder()
                                    .setFirstName("George")
                                    .setLastName("Harrison")
                                    .build()
                    )
                    .build()
        );

        System.out.println("sending message");
        requestObserver.onNext(
                LongGreetRequest.newBuilder()
                    .setGreeting(
                            Greeting.newBuilder()
                                    .setFirstName("Ringo")
                                    .setLastName("Starr")
                                    .build()
                    )
                    .build()
        );

        System.out.println("sending message done");
        requestObserver.onCompleted();

        try {
            latch.await(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doBidiStreamingCall() {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<GreetEveryoneRequest> requestObserver =
            greetAsyncClient.greetEveryone(new StreamObserver<GreetEveryoneResponse>() {

            @Override
            public void onNext(GreetEveryoneResponse value) {
                System.out.println("got server message");
                System.out.println("MESSAGE: ");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("got server error");
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("got server done");
                latch.countDown();
            }
        });

        System.out.println("sending message");
        requestObserver.onNext(
                GreetEveryoneRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("John")
                                        .setLastName("Lennon")
                                        .build()
                        )
                        .build()
        );

        System.out.println("sending message");
        requestObserver.onNext(
                GreetEveryoneRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Paul")
                                        .setLastName("McCartney")
                                        .build()
                        )
                        .build()
        );

        System.out.println("sending message");
        requestObserver.onNext(
                GreetEveryoneRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("George")
                                        .setLastName("Harrison")
                                        .build()
                        )
                        .build()
        );

        System.out.println("sending message");
        requestObserver.onNext(
                GreetEveryoneRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Ringo")
                                        .setLastName("Starr")
                                        .build()
                        )
                        .build()
        );

        System.out.println("sending message done");
        requestObserver.onCompleted();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello I'm a gRPC client");

        GreetingClient main = new GreetingClient();
        main.run();
    }
}
