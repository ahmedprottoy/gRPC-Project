package gRPCServer;

import io.grpc.ServerBuilder;
import gRPCServices.userServices;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting GRPC server.");
        io.grpc.Server server = ServerBuilder.forPort(9090).addService(new userServices()).build();
        server.start();

        System.out.println("Server started at "+server.getPort());
        server.awaitTermination();
    }
}
