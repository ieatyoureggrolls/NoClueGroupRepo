package Views;

import Models.EntryData;
import Models.Goal;
import Models.User;
import java.time.LocalDate;
import java.util.List;

public class UI {
    //region menu options
    public static int getMenuOption(boolean signedIn){
        String message = "What would you like to do?";
        if (!signedIn){
            message += """
                    
                    1. Sign In
                    2. Sign Up
                    3. Exit
                    """;
        }
        else{

            message += """
                    
                    1. Add new financial data
                    2. View financial history
                    3. Add a financial goal
                    4. View financial goals
                    5. Sign out
                    """;
        }
        return Console.getIntInput(message, 1, (!signedIn ? 3 : 5));
    }
    //endregion

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

    public static double getTransactionAmount(){
        double amount = Console.getDoubleInput("How much was this transaction?");
        return  ((double) Math.round(amount * 100) / 100);
    }

    /**
     * Prompts the user to input a date
     * @return A String of a valid date
     */
    public static LocalDate getTransactionDate(){
        LocalDate date = Console.getDateInput("Please enter your transaction date:", Console.TextColor.DEFAULT);
        return date;
    }

    public static String getCategory(){
        return Console.getStringInput("What was the category of this expense?", false);
    }

    public static String getGoalDescription(){
        return Console.getStringInput("Please enter a description for your goal:");
    }

    public static double getGoalTargetAmount(){
        return Console.getDoubleInput("Please enter a target amount for your goal:");
    }

    public static LocalDate getGoalDeadline(){
        return Console.getDateInput("Please enter a date for your goal:", Console.TextColor.DEFAULT);
    }

    /**
     * Prompts the user to input a few values to make a new financial data point
     * @return EntryData, but if it is an expense it will also store where it was made at
     */
    public static EntryData addDataPoint(){
        boolean isExpense = Console.getBooleanInput("Is this an expense or income?", "E", "I");
        double amount = getTransactionAmount();
        LocalDate date = getTransactionDate();
        String category = getCategory();
        String madeAt;
        if (isExpense) {
            madeAt = Console.getStringInput("Where was this transaction made?", false);
            return new EntryData(amount, date, isExpense, category, madeAt);
        }

        return new EntryData(amount, date, isExpense, category);
    }

    public static int chooseSort(){
        return Console.getIntInput("""
                How would you like to sort your transaction history?
                1. Sort by date
                2. Sort by reversed date
                3. sort by category (alphabetically)
                4. sort by reversed category (alphabetically)
                5. sort by amount (largest first)
                6. sort by amount (smallest first)
                """);
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

    /**
     * Prints out only expenses made on the users account
     * @param user who is the active user
     */
    public static void printExpenses(User user){
        for (EntryData data : user.getHistory().history) {
            if (data.isExpense())
                Console.writeln(data.toString());
        }
    }

    public static void printGoals(User user) {
        Console.writeln("\n=== Financial Goals ===");
        
        if (user.getGoals() == null || user.getGoals().isEmpty()) {
            Console.writeln("No financial goals set yet.");
            Console.writeln("=======================\n");
            return;
        }

        for (Goal goal : user.getGoals()) {
            Console.writeln(goal.toString());
        }
        Console.writeln("=======================\n");
    }

    /**
     * Prints out only income made on the users account
     * @param user who is the active user
     */
    public static void printIncome(User user){
        for (EntryData data : user.getHistory().history) {
            if (!data.isExpense())
                Console.writeln(data.toString());
        }
    }

    /**
     * Prints all financial history on the users account
     * @param user who is the active user
     */
    public static void printHistory(User user) {
        List<EntryData> sortedTransactions = user.getHistory().getSortedHistory(chooseSort());
        Console.writeln("\n=== Transaction Ledger ===");
        
        // Check if history is empty
        if (user.getHistory().history.isEmpty()) {
            Console.writeln("No transactions found.");
            Console.writeln("==========================\n");
            return;
        }



        // ANSI Color Codes
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";

        for (EntryData entry : sortedTransactions) {
            // Determine color and sign based on whether it's an expense
            String color = entry.isExpense() ? ANSI_RED : ANSI_GREEN;
            String sign = entry.isExpense() ? "-" : "+";
            String location = entry.isExpense() ? " (at " + entry.getMadeAt() + ")" : "";

            // Print the formatted line
            Console.writeln(String.format("%s[%s] %s$%.2f%s%s | %s\n",
                color, 
                entry.getDate(), 
                sign, 
                entry.getAmount(), 
                location, 
                ANSI_RESET,
                entry.getCategory()
            ));
        }
        Console.writeln("==========================\n");
    }
}
