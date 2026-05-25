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

}
