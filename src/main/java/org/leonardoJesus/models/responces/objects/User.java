package org.leonardoJesus.models.responces.objects;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String name;
    private String password;
    private int image;

    public User(String email, String name, String password, int image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public User() {
        this.email = null;
        this.name = null;
        this.password = null;
        this.image = 0;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}