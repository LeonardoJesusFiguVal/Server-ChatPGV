package org.leonardoJesus.models.responces.objects;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Message implements Serializable {

    private String sender;
    private String target;
    private String mDate;
    private String body;
    private boolean state;

    public Message(String sender, String target, String mDate, String body, boolean state) {
        this.sender = sender;
        this.target = target;
        this.mDate = mDate;
        this.body = body;
        this.state = state;
    }

    public Message() {
        this.sender = null;
        this.target = null;
        this.mDate = null;
        this.body = null;
        this.state = false;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
