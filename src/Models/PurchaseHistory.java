package Models;

import java.util.ArrayList;

public class PurchaseHistory {
    public ArrayList<EntryData> history =  new ArrayList<>();

    public double calculateTotalBalance(){
        double balance = 0.0;

        for (EntryData entry : history){
            if (entry.isExpense()){
                balance -= entry.getAmount();
            } else {
                balance += entry.getAmount();
            }
        }

        return balance;
    }
}
