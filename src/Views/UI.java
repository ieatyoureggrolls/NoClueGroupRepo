package Views;

import Models.User;

public class UI {
    //region inputs

    /**
     * Prompts the user to input their username and returns what they entered.
     * @return String
     */
    public static String getUserName(){
        return Console.getStringInput("Please enter your username:", false);
    }

    /**
     * Prompts the user to input their password and returns what they entered.
     * @return String
     */
    public static String getPassword(){
        return Console.getStringInput("Please enter your password:", false);
    }

    /**
     * Prompts the user to input their email and returns what they entered.
     * @return String
     */
    public static String getEmail(){
        return Console.getStringInput("Please enter your email:", false);
    }
    //endregion

    //region print functions

    /**
     * prints the given user's username, password, and email in one line.
     * @param user
     */
    public static void printUserInfo(User user){
        Console.writeln(String.format("""
                User: %s
                Password: %s
                Email: %s
                """,  user.getUsername(), user.getPassword(), user.getEmail()));
    }

    public static void printBills(User user){

    }

    public static void printIncome(User user){

    }
    //endregion
}
