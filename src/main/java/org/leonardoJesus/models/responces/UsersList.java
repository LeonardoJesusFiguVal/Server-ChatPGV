package org.leonardoJesus.models.responces;

import org.leonardoJesus.models.responces.objects.User;

import java.io.Serializable;
import java.util.ArrayList;

public class UsersList implements Serializable {

    private ArrayList<User> users;

    public UsersList(ArrayList<User> users) {
        this.users = users;
    }

    public UsersList(){
        this.users = null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
