package org.leonardoJesus.models.responces;

import org.leonardoJesus.models.responces.objects.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageList implements Serializable {

    private ArrayList<Message> messageArray;

    public MessageList(ArrayList<Message> messageArray) {
        this.messageArray = messageArray;
    }

    public MessageList() {
        this.messageArray = null;
    }

    public ArrayList<Message> getMessageArray() {
        return messageArray;
    }

    public void setMessageArray(ArrayList<Message> messageArray) {
        this.messageArray = messageArray;
    }
}
