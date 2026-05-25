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
        User dummyUser = new User("john_duhumjoe", "jdoe@gmail.com", "passwordddd34", null);
        System.out.println(dummyUser);
        String result = saveUserData(dummyUser, SAVE_PATH);
        System.out.println(result);
        mainMenu();
    }

    public String saveUserData(User user, String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = user.getUsername() + ".json";
        ObjectMapper mapper = new ObjectMapper();

        try {
            File outFile = new File(directory, filename);
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

    private boolean signIn() {
        activeUser = new User(); //here for testing purposes
        return false;
    }

    private boolean signUp() {
        activeUser = new User(); //here for testing purposes
        return false;
    }

    private void signOut() {
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
