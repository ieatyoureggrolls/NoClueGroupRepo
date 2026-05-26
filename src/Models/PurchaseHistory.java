package Models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PurchaseHistory {
    public ArrayList<EntryData> history = new ArrayList<>();


    public List<EntryData> getSortedHistory(int sortWay) {
        List<EntryData> sortedList = new ArrayList<>(history);

        switch (sortWay) {
            case 1: //Sort by date
                sortedList.sort(Comparator.comparing(EntryData::getDate));
                break;
            case 2: //reversed date
                sortedList.sort(Comparator.comparing(EntryData::getDate).reversed());
                break;
            case 3: //category
                sortedList.sort(Comparator.comparing(EntryData::getCategory));
                break;
            case 4: //reversed category
                sortedList.sort(Comparator.comparing(EntryData::getCategory).reversed());
                break;
            case 5: //amount (largest first)
                sortedList.sort(Comparator.comparing(EntryData::getAmount).reversed());
                break;
            case 6: //amount (smallest first)
                sortedList.sort(Comparator.comparing(EntryData::getAmount));
                break;
        }

        return sortedList;
    }

    public double calculateTotalBalance() {
        double balance = 0.0;

        for (EntryData entry : history) {
            if (entry.isExpense()) {
                balance -= entry.getAmount();
            } else {
                balance += entry.getAmount();
            }
        }

        return balance;
    }
}