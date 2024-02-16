package org.leonardoJesus;

import com.google.gson.Gson;
import org.leonardoJesus.models.requests.JsonOrder;
import org.leonardoJesus.models.requests.get.ReqUsers;
import org.leonardoJesus.models.requests.get.ReqVerification;
import org.leonardoJesus.models.responces.UsersList;
import org.leonardoJesus.models.responces.VerifiedUser;
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
            /*
            case "getMessages":
                // TODO Implement case "getMessages"
                break;
            case "updateUser":
                // TODO Implement case "updateUser"
                break;
            case "postUser":
                // TODO Implement case "postUser"
                break;
            case "postMessage":
                // TODO Implement case "postMessage"
                break;

             */
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

    private static UsersList getAllUsers(JsonOrder order){
        ReqUsers req = gson.fromJson(order.getParams(), ReqUsers.class);

        DbManager dbManager = new DbManager();
        UsersList users = new UsersList(dbManager.getAllUsers(req));
        dbManager.closeConnection();

        return users;
    }
}
