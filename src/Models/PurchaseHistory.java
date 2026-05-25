package Models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PurchaseHistory {
    public ArrayList<EntryData> history =  new ArrayList<>();


    public List<EntryData> getSortedHistory(){
        List<EntryData> sortedList = new ArrayList<>(history);

        sortedList.sort(Comparator.comparing(EntryData::getDate).reversed());

        return sortedList;
    }

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
