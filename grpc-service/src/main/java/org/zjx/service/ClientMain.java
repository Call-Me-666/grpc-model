package org.zjx.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.zjx.service.HelloGrpc.*;

import java.util.concurrent.TimeUnit;

import static org.zjx.service.HelloGrpc.newBlockingStub;
import static org.zjx.service.HelloGrpc.newStub;

public class ClientMain {
    private final HelloBlockingStub blockingStub;
    private final ManagedChannel managedChannel;

    public ClientMain(String ip,int port){
        this.managedChannel = ManagedChannelBuilder.forTarget(ip+":"+port).usePlaintext(true).build();
        this.blockingStub = newBlockingStub(this.managedChannel);

        System.out.println("Connected to server at" + ip+":"+port);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                this.managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Client shut down");
        }));
    }

    private void shutdown() throws InterruptedException {
        this.managedChannel.shutdown().awaitTermination(5,TimeUnit.SECONDS);
        System.out.println("Client closed");
    }

    public static void main(String[] args) throws InterruptedException {
        ClientMain client = new ClientMain("127.0.0.1",10001);
        Service.HelloReply helloReply = client.blockingStub.sayHello(Service.HelloRequest.newBuilder().setName("王宇轩").build());
        System.out.println("接受到服务器返回信息："+helloReply.getResult());
        client.shutdown();
    }
}
