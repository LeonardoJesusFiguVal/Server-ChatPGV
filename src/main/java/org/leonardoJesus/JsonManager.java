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
            case "getVerification" -> response = gson.toJson(getVerification(order));
            case "getChatUsers" -> response = gson.toJson(getChatUsers(order));
            case "getAllUsers" -> response = gson.toJson(getAllUsers(order));
            case "getMessages" -> response = gson.toJson(getMessages(order));
            case "updateUser" -> response = gson.toJson(updateUser(order));
            case "postUser" -> response = gson.toJson(postUser(order));
            case "postMessage" -> response = gson.toJson(postMessage(order));
        }

        return response;
    }

    //Metodo para ejecutar la orden "getVerification"
    private static VerifiedUser getVerification(JsonOrder order){
        ReqVerification req = gson.fromJson(order.getParams(), ReqVerification.class);

        DbManager dbManager = new DbManager();
        User user = dbManager.getVerification(req);

        System.out.println(user.toString());

        dbManager.closeConnection();

        return new VerifiedUser(user);
    }

    //Metodo para ejecutar la orden "getChatUsers"
    private static UsersList getChatUsers(JsonOrder order){
        ReqUsers req = gson.fromJson(order.getParams(), ReqUsers.class);

        DbManager dbManager = new DbManager();
        UsersList users = new UsersList(dbManager.getChatUsers(req));
        dbManager.closeConnection();

        return users;
    }

    //Metodo para ejecutar la orden "getAllUsers"
    private static UsersList getAllUsers(JsonOrder order){
        ReqUsers req = gson.fromJson(order.getParams(), ReqUsers.class);

        DbManager dbManager = new DbManager();
        UsersList users = new UsersList(dbManager.getAllUsers(req));
        dbManager.closeConnection();

        return users;
    }

    //Metodo para ejecutar la orden "getMessages"
    private static MessageList getMessages(JsonOrder order){
        ReqMessages req = gson.fromJson(order.getParams(), ReqMessages.class);

        DbManager dbManager = new DbManager();
        MessageList messageList = new MessageList(dbManager.getMessages(req));
        dbManager.closeConnection();

        return messageList;
    }

    //Metodo para ejecutar la orden "updateUser"
    private static User updateUser(JsonOrder order) {
        User user = gson.fromJson(order.getParams(), User.class);

        DbManager dbManager = new DbManager();
        dbManager.updateUser(user);

        return dbManager.getNewUser(user.getEmail(), user.getPassword());
    }

    //Metodo para ejecutar la orden "PostUser"
    private static User postUser(JsonOrder order) {
        User user = gson.fromJson(order.getParams(), User.class);

        DbManager dbManager = new DbManager();
        dbManager.postUser(user);

        return dbManager.getNewUser(user.getEmail(), user.getPassword());
    }

    //Metodo para ejecutar la orden "PostMessage"
    private static Message postMessage(JsonOrder order) {
        Message message = gson.fromJson(order.getParams(), Message.class);

        DbManager dbManager = new DbManager();
        dbManager.postMessage(message);

        return null;
    }
}
