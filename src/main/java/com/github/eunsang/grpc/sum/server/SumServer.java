package com.github.eunsang.grpc.sum.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SumServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("SumServer is running.");

        Server server = ServerBuilder.forPort(50051)
                .addService(new SumServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("SumServer has been terminated.");
        }));

        server.awaitTermination();
    }
}
