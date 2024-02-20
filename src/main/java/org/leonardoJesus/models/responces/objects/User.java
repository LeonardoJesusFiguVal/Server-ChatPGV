package org.leonardoJesus.models.responces.objects;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String name;
    private String password;
    private boolean state;
    private String image;

    public User(String email, String name, String password, String image, boolean state) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
        this.state = state;
    }

    public User() {
        this.email = null;
        this.name = null;
        this.password = null;
        this.image = null;
        this.state = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setState(boolean state){
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", image='" + image + '\'' +
                '}';
    }
}