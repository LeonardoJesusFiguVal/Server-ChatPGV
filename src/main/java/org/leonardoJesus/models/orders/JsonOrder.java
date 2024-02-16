package org.leonardoJesus.models.orders;

public class JsonOrder {

    private String command;
    private String params;

    public JsonOrder(String command, String params) {
        this.command = command;
        this.params = params;
    }

    public JsonOrder() {
        this.command= null;
        this.params = null;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
