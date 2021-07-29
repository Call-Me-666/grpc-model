package org.zjx.service;

import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloGrpc.HelloImplBase {
    @Override
    public void sayHello(Service.HelloRequest request, StreamObserver<Service.HelloReply> responseObserver) {
        Service.HelloReply reply = Service.HelloReply.newBuilder().setResult("Hello " + request.getName()).build();
        System.out.println("接受到请求");
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
