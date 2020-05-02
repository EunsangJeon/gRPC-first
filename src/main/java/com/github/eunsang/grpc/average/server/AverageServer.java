package com.github.eunsang.grpc.average.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class AverageServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new AverageServiceImpl())
                .build();

        server.start();
        System.out.println("server started");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("server stopped");
        }));

        server.awaitTermination();
    }
}
