package com.github.eunsang.grpc.decomposition.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class DecompositionServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new DecompositionServiceImpl())
                .build();

        System.out.println("server starting");
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("server closing");
            server.shutdown();
            System.out.println("server terminated");
        }));

        server.awaitTermination();
    }
}
