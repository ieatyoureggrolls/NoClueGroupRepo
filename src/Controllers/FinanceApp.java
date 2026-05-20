package Controllers;

import Models.User;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class FinanceApp {
    public final String SAVE_PATH = "src/Data/Users/";
    public void run(){
        User dummyUser = new User("john_duhumjoe","jdoe@gmail.com", "passwordddd34", null); 
        System.out.println(dummyUser);
        String result = saveUserData(dummyUser, SAVE_PATH);
        System.out.println(result);

    }

    public String saveUserData(User user, String path) {
        File directory = new File(path);
        if (!directory.exists()) {'k'
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
    
}
