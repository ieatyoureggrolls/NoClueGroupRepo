package Models;

import java.time.LocalDate;

public class EntryData {
    private double amount;
    private LocalDate date;
    private String madeAt;
    private boolean isExpense;

    public EntryData() {}

    public EntryData(double amount, LocalDate date, boolean isExpense) {
        setAmount(amount);
        setDate(date);
        setExpense(isExpense);
    }

    public EntryData(double amount, LocalDate date, boolean isExpense, String madeAt) {
        this(amount, date, isExpense);
        setMadeAt(madeAt);
    }

    //region getters/setters
    public double getAmount() {
        return amount;
    }

    private void setAmount(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException("Date cannot be null or empty");
        this.date = date;
    }

    public boolean isExpense() {
        return isExpense;
    }

    private void setExpense(boolean expense) {
        isExpense = expense;
    }

    public String getMadeAt() {
        return madeAt;
    }

    private void setMadeAt(String madeAt) {
        this.madeAt = madeAt;
    }
    //endregion


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("$");
        sb.append(getAmount());
        sb.append(" was reported as ");
        sb.append(isExpense() ? "an expense" : "income");
        sb.append(" on ");
        sb.append(getDate());
        if (!isExpense())
            return sb.toString();
        sb.append(" at ");
        sb.append(getMadeAt());
        return sb.toString();
    }
}
