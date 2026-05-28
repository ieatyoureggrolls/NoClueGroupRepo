package Models;
public class InterestEstimate {
//    private double balance;
//     private double interest;


        // Monthly compounding
        public double calculateFutureValue(double principal, double apy, int years) {

            double rate = apy / 100.0;
            int compoundsPerYear = 12;

            return principal * Math.pow( 1 + (rate / compoundsPerYear),compoundsPerYear * years);
        }

    }

//     public InterestEstimate(double balance, double interest) {
//         setBalance(balance);
//         setInterest(interest);
//     }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    public double getInterest() {
//        return interest;
//    }
//
//    public void setInterest(double interest) {
//        this.interest = interest;
//    }
//
//    public void accountDetails(){
//
//   }



