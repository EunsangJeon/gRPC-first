package com.github.eunsang.grpc.sum.client;

import com.proto.sum.Numbers;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SumClient {

    public static void main(String[] args) {
        System.out.println("SumClient is running.");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        SumServiceGrpc.SumServiceBlockingStub sumClient = SumServiceGrpc.newBlockingStub(channel);

        Numbers numbers = Numbers.newBuilder()
                .setFirst(1)
                .setSecond(2)
                .build();

        SumRequest sumRequest = SumRequest.newBuilder()
                .setNumbers(numbers)
                .build();

        SumResponse sumResponse = sumClient.sum(sumRequest);
        int result = sumResponse.getResult();

        System.out.println("Answer: " + result);

        channel.shutdown();
        System.out.println("SumClient has been terminated.");
    }
}
