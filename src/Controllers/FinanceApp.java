package Controllers;

import Models.*;

import java.io.File;
import java.io.IOException;

import Views.Console;
import Views.UI;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.time.LocalDate;
import javax.crypto.AEADBadTagException;

public class FinanceApp {
    public final String SAVE_PATH = "src/Data/Users/";
    private User activeUser = null;

    public void run() {
        mainMenu();
    }
    /**
     * Saves the user data to a JSON file. The file is named after the user's username and stored in the specified directory. 
     * If the directory does not exist, it is created. The method uses Jackson for JSON serialization and handles any IO exceptions that may occur.
     * @param user The User object containing the data to be saved.
     * @param path The directory path where the user data should be saved.
     * @return A string message indicating whether the save was successful or if an error occurred.
     */
    public String saveUserData(User user, String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = user.getUsername() + ".json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());//Allowes Jackson to properly save dates from java.time
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            File outFile = new File(directory, filename);//Creates the save file in the specified directory with the username as the filename
            mapper.writerWithDefaultPrettyPrinter().writeValue(outFile, user);
            return "User data saved successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving user data.";
        }
    }

    public void mainMenu() {
        do {
            int choice = UI.getMenuOption(activeUser != null) + (activeUser != null ? 3 : 0);
            switch (choice) { //1-3 not signed in, 4-6 signed in
                case 1: //Sign in
                    signIn();
                    break;
                case 2: //Sign up
                    signUp();
                    break;
                case 3: //Exit
                    return;
                case 4: //Add financial data point
                    addData();
                    break;
                case 5: //View financial history
                    viewHistory();
                    break;
                case 6:
                    addGoal();
                    break;
                case 7:
                    viewGoals();
                    break;
                case 8: //Sign Out
                    signOut();
                    break;
            }
        } while (true);
    }

    /**
     * Handles the user sign-in process. Gets username and password from user, checks for a matching user file, and verifies the password. 
     * If successful, sets the activeUser to the signed-in user.
     * @return True if sign-in is successful, false otherwise.
     */
    private boolean signIn() {
        String username = UI.getUserName();
        for (File file : new File(SAVE_PATH).listFiles()) { //Iterate through user files to find a matching username. 
            if (file.getName().equals(username + ".json")) { 
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
             
                try {
                    activeUser = mapper.readValue(file, User.class);
                    String inputPassword = UI.getPassword();
                    if (Password.verifyPassword(inputPassword.toCharArray(), activeUser.getPassword())) {
                        return true; // Successful sign-in
                    } else {
                        System.out.println("Incorrect password.");
                        activeUser = null;
                        return false; // Incorrect password
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false; // Error reading user data
                }
            }
        }
//        activeUser = new User(); //here for testing purposes

        return false;
    }

    /**
     * Handles the user sign-up process. Gets username, email, and password from user, creates a new User object, and sets it as the activeUser.
     * @return True if sign-up is successful, false otherwise.
     */
    private boolean signUp() {
        String username = UI.getUserName();
        String email = UI.getEmail();
        String password = UI.getPassword();
        PurchaseHistory history = new PurchaseHistory();
        activeUser = new User(username, email, password, history);
        return false;
    }

    private void signOut() {
        saveUserData(activeUser, SAVE_PATH);
        activeUser = null;
    }

    private void addData() {
        activeUser.addDataPoint(UI.addDataPoint());
    }

    private void viewHistory() {
        UI.printHistory(activeUser);
    }

    private void addGoal(){
        if (activeUser != null){
            String desc = UI.getGoalDescription();
            double targetAmount = UI.getGoalTargetAmount();
            LocalDate deadline = UI.getGoalDeadline();
            activeUser.addGoal(new Goal(desc, targetAmount, deadline));
        }
    }

    private void viewGoals(){
        if (activeUser != null){
            UI.printGoals(activeUser);
        }
    }
}
