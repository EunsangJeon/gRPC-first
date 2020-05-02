package com.github.eunsang.grpc.decomposition.client;

import com.proto.decomposition.DecompositionRequest;
import com.proto.decomposition.DecompositionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DecompositionClient {

    public static void main(String[] args) {
        System.out.println("client starts");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        DecompositionRequest decompositionRequest = DecompositionRequest.newBuilder()
                .setNumber(793)
                .build();

        DecompositionServiceGrpc.DecompositionServiceBlockingStub decompositionServiceBlockingStub =
                DecompositionServiceGrpc.newBlockingStub(channel);

        decompositionServiceBlockingStub.decomposition(decompositionRequest)
                .forEachRemaining(decompositionResponse -> {
                    System.out.println(decompositionResponse.getResult());
                });

        System.out.println("client closing");
        channel.shutdown();
        System.out.println("client terminated");
    }
}
