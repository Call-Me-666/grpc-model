package org.zjx.service;

import io.grpc.ServerBuilder;
import io.grpc.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServerMain {
    private final Server server;

    public ServerMain() {
        this.server = ServerBuilder.forPort(10001).addService(new HelloServiceImpl()).build();
    }

    public void start() throws IOException {
            server.start();
        System.out.println("Server is started");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    public void stop() throws InterruptedException {
        if (this.server != null) {
            this.server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            System.out.println("Server is close");
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerMain server = new ServerMain();
        server.start();
        server.blockUntilShutdown();
    }
}
