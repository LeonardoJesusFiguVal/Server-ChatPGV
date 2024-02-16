package org.leonardoJesus;

import org.leonardoJesus.models.orders.JsonOrder;

public class JsonManager {

    public static String executeOrder(JsonOrder order){

        switch (order.getCommand()){
            case "getVerification":
                // TODO Implement case "getVerification"
                break;
            case "getChatUsers":
                // TODO Implement case "getChatUsers"
                break;
            case "getAllUsers":
                // TODO Implement case "getAllUsers"
                break;
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
        }

        return null;
    }
}
