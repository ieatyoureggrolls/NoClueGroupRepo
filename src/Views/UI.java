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

    private static String getTransactionAmount(){
        double amount = Console.getDoubleInput("How much was this transaction?");
        String dollarAmount = "$" + (Math.round(amount * 100) / 100);
        return dollarAmount;
    }

    public static String getTransactionDate(){
        String date = Console.getStringInput("Please enter your transaction date:", false);
        //ToDo validate the date to make sure that it is valid
        return date;
    }

    public static String addDataPoint(){
        //ToDo make this return an EntryData instead of just a string
        boolean isExpense = Console.getBooleanInput("Is this an income or an expense?(Y/N)", "Y", "N");
        String amount = getTransactionAmount();
        String date = getTransactionDate();
        String madeAt;
        if (isExpense) {
            madeAt = Console.getStringInput("Where was this transaction made?", false);
            return String.format("An expense was made on %s to %s for %s",  date, madeAt, amount);
        }

        return String.format("Income was reported on %s for %s",  date, amount);
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
