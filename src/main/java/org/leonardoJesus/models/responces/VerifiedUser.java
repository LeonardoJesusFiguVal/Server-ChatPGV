package org.leonardoJesus.models.responces;


import org.leonardoJesus.models.responces.objects.User;

import java.io.Serializable;

public class VerifiedUser implements Serializable {

    private User user;

    public VerifiedUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
