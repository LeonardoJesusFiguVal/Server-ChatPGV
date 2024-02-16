package org.leonardoJesus.models.requests.get;

import java.io.Serializable;

public class ReqUsers implements Serializable {

    private String mail;

    public ReqUsers(String mail) {
        this.mail = mail;
    }

    public ReqUsers(){
        mail = null;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
