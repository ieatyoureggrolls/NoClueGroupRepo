package Models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("history")
    private PurchaseHistory history;

    //Empty constructor. Needed for Jackson
    public User(){
    
    }
    public User(String username, String email, String password, PurchaseHistory history) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setHistory(history);
    }

    //regions Getters and Setters
    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public PurchaseHistory getHistory() {
        return history;
    }

    private void setHistory(PurchaseHistory history) {
        this.history = history;
    }
    //endregion

    //region Override Methods
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", history=" + history +
                '}';    
    }
}



