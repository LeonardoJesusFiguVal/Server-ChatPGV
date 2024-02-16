package org.leonardoJesus.models.requests.get;

import java.io.Serializable;

public class ReqVerification implements Serializable {

    private String email;
    private String password;

    public ReqVerification(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ReqVerification() {
        this.email = null;
        this.password = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ReqVerification{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
