package org.leonardoJesus;

import com.google.gson.Gson;
import org.leonardoJesus.models.requests.JsonOrder;
import org.leonardoJesus.models.requests.get.ReqMessages;
import org.leonardoJesus.models.requests.get.ReqUsers;
import org.leonardoJesus.models.requests.get.ReqVerification;
import org.leonardoJesus.models.responces.MessageList;
import org.leonardoJesus.models.responces.UsersList;
import org.leonardoJesus.models.responces.VerifiedUser;
import org.leonardoJesus.models.responces.objects.Message;
import org.leonardoJesus.models.responces.objects.User;

public class JsonManager {

    public static Gson gson = new Gson();

    public static String executeOrder(JsonOrder order){
        String response = null;

        System.out.println(order.toString());

        switch (order.getCommand()){
            case "getVerification" -> response = getVerification(order);
            case "getChatUsers" -> response = getChatUsers(order);
            case "getAllUsers" -> response = getAllUsers(order);
            case "getMessages" -> response = gson.toJson(getMessages(order));
            case "updateUser" -> response = gson.toJson(updateUser(order));
            case "postUser" -> response = gson.toJson(postUser(order));
            case "postMessage" -> response = gson.toJson(postMessage(order));
        }

        return response;
    }

    //Metodo para ejecutar la orden "getVerification"
    private static String getVerification(JsonOrder order){
        ReqVerification req = gson.fromJson(order.getParams(), ReqVerification.class);

        DbManager dbManager = new DbManager();
        String user = dbManager.getVerification(req);

        System.out.println(user);

        dbManager.closeConnection();

        return user;
    }

    //Metodo para ejecutar la orden "getChatUsers"
    private static String getChatUsers(JsonOrder order){
        ReqUsers req = gson.fromJson(order.getParams(), ReqUsers.class);

        DbManager dbManager = new DbManager();
        String users = dbManager.getChatUsers(req);
        dbManager.closeConnection();

        return users;
    }

    //Metodo para ejecutar la orden "getAllUsers"
    private static String getAllUsers(JsonOrder order){
        ReqUsers req = gson.fromJson(order.getParams(), ReqUsers.class);

        DbManager dbManager = new DbManager();
        String users = dbManager.getAllUsers(req);
        dbManager.closeConnection();

        return users;
    }

    //Metodo para ejecutar la orden "getMessages"
    private static String getMessages(JsonOrder order){
        ReqMessages req = gson.fromJson(order.getParams(), ReqMessages.class);

        DbManager dbManager = new DbManager();
        String messageList = dbManager.getMessages(req);
        dbManager.closeConnection();

        return messageList;
    }

    //Metodo para ejecutar la orden "updateUser"
    private static String updateUser(JsonOrder order) {
        User user = gson.fromJson(order.getParams(), User.class);

        DbManager dbManager = new DbManager();
        dbManager.updateUser(user);
        String newUser = dbManager.getNewUser(user.getEmail(), user.getPassword());
        dbManager.closeConnection();

        return newUser;
    }

    //Metodo para ejecutar la orden "PostUser"
    private static String postUser(JsonOrder order) {
        User user = gson.fromJson(order.getParams(), User.class);

        DbManager dbManager = new DbManager();
        dbManager.postUser(user);
        String newUser = dbManager.getNewUser(user.getEmail(), user.getPassword());
        dbManager.closeConnection();

        return newUser;
    }

    //Metodo para ejecutar la orden "PostMessage"
    private static Message postMessage(JsonOrder order) {
        Message message = gson.fromJson(order.getParams(), Message.class);

        DbManager dbManager = new DbManager();
        dbManager.postMessage(message);

        return null;
    }
}
