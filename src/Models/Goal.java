package Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Goal {
    @JsonProperty("description")
    private String description;
    @JsonProperty("targetAmount")
    private double targetAmount;
    @JsonProperty("deadline")
    private LocalDate deadline;

    public Goal() {}

    public Goal(String description, double targetAmount, LocalDate deadline){
        setDescription(description);
        setTargetAmount(targetAmount);
        setDeadline(deadline);
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    private void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    private void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString(){
        return String.format("Goal: %s | Target: $%.2f | Deadline: %s", getDescription(), getTargetAmount(), getDeadline());
    }

}
