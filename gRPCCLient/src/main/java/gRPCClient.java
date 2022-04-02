import com.makarov09.grpc.User;
import com.makarov09.grpc.userGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.makarov09.*;

import java.util.Scanner;

public class gRPCClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        userGrpc.userBlockingStub userStub = userGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                String s1 = scanner.nextLine();
                String s2 = scanner.nextLine();
                User.RegisterRequestMsg registerRequest = User.RegisterRequestMsg.newBuilder().setUserName(s1).setPassword(s2).build();
                User.APIResponseMsg response = userStub.userRegister(registerRequest);
                System.out.println(response.getResponseMessage());
            } else if (choice == 2) {
                String s3 = scanner.nextLine();
                String s4 = scanner.nextLine();
                User.LoginRequestMsg loginRequest = User.LoginRequestMsg.newBuilder().setUserName(s3).setPassword(s4).build();
                User.APIResponseMsg response = userStub.login(loginRequest);
                System.out.println(response.getResponseMessage());
            } else if (choice == 3) break;
            else {
                System.out.println("some error occurred. Please choose between 1, 2 or 3.");
            }
        }


    }
}
