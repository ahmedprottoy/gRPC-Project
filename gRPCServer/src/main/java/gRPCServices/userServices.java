package gRPCServices;

import com.makarov09.grpc.User;
import com.makarov09.grpc.userGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.*;

public class userServices extends userGrpc.userImplBase{
    @Override
    public void userRegister(User.RegisterRequestMsg request, StreamObserver<User.APIResponseMsg> responseObserver) {
        String userName = request.getUserName();
        String password = request.getPassword();

        User.APIResponseMsg.Builder response = User.APIResponseMsg.newBuilder();

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grpcdb", "root", "mongodb69");

            String query = "INSERT INTO userInfo(userName,password) "
                    + "VALUES(\"" + userName+"\",\""+password+"\")";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            response.setResponseCode(201).setResponseMessage("registration successful!");

//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            //System.out.println(e.getMessage());
//            //System.out.println("invalid");
        }
        catch (SQLException e) {
            e.printStackTrace();

            response.setResponseCode(401).setResponseMessage("User already exists.");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void login(User.LoginRequestMsg request, StreamObserver<User.APIResponseMsg> responseObserver) {
        System.out.println("Inside Login");
        String url = "jdbc:mysql://localhost:3306/grpcdb";
        String user = "root";
        String pass = "mongodb69";



        String userName = request.getUserName();
        String password = request.getPassword();

        User.APIResponseMsg.Builder response = User.APIResponseMsg.newBuilder();
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from userinfo where userName = \""+userName+"\"");
            boolean b = false;

            while (resultSet.next()) {
                String n = resultSet.getString("userName");
                String p = resultSet.getString("password");
                b = true;

                if (userName.equals(n) && password.equals(p)) {
                    response.setResponseCode(0).setResponseMessage("Login Successful");
                } else {
                    response.setResponseCode(100).setResponseMessage("Invalid Username or password");
                }
            }

            if (!b) {
                response.setResponseCode(100).setResponseMessage("INVALID");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }


    @Override
    public void logout(User.EmptyMsg request, StreamObserver<User.APIResponseMsg> responseObserver) {

    }
}
