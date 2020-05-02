package com.github.eunsang.grpc.average.client;

import com.proto.average.AverageRequest;
import com.proto.average.AverageResponse;
import com.proto.average.AverageServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class AverageClient {
    private final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build();

    private void runClient() {
        System.out.println("client started");

        AverageServiceGrpc.AverageServiceStub averageServiceStub =
                AverageServiceGrpc.newStub(channel);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        StreamObserver<AverageRequest> requestObserver =
                averageServiceStub.average(new StreamObserver<AverageResponse>() {
            @Override
            public void onNext(AverageResponse value) {
                System.out.println("got server message");

                System.out.println("Result: " + value.getResult());
                countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("got server error");
            }

            @Override
            public void onCompleted() {
                System.out.println("terminated communication with server");
            }
        });

        System.out.println("sending message to server");
        requestObserver.onNext(AverageRequest.newBuilder()
                .setNumber(10)
                .build());

        System.out.println("sending message to server");
        requestObserver.onNext(AverageRequest.newBuilder()
                .setNumber(20)
                .build());

        System.out.println("sending message to server");
        requestObserver.onNext(AverageRequest.newBuilder()
                .setNumber(30)
                .build());

        System.out.println("sending message to server");
        requestObserver.onNext(AverageRequest.newBuilder()
                .setNumber(40)
                .build());

        System.out.println("sending message to server");
        requestObserver.onNext(AverageRequest.newBuilder()
                .setNumber(50)
                .build());

        System.out.println("sending end signal");
        requestObserver.onCompleted();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        channel.shutdown();
        System.out.println("client stopped");
    }

    public static void main(String[] args) {
        AverageClient averageClient = new AverageClient();
        averageClient.runClient();
    }
}
