package Models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class User {
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("PasswordHash")
    private char[] passwordHash;
    @JsonProperty("history")
    private PurchaseHistory history;
    @JsonProperty("goals")
    private ArrayList<Goal> goals = new ArrayList<>();

    //Empty constructor. Needed for Jackson
    public User(){
    setHistory(new PurchaseHistory());
    }

    public User(String username, String email, String password, PurchaseHistory history) {
        String passwordHash = Password.createHash(password.toCharArray());
        setUsername(username);
        setEmail(email);
        setPassword(passwordHash);
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

    public char[] getPasswordHash() {
        return passwordHash;
    }

    private void setPasswordHash(char[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public ArrayList<Goal> getGoals(){
        return this.goals;
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

    public void addDataPoint(EntryData data){
        getHistory().history.add(data);
    }

    public void addGoal(Goal goal){
        if (goal != null){
            goals.add(goal);
        }
    }


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



