package Models;
public class InterestEstimate {

        // Monthly compounding
        public double calculateFutureValue(double principal, double apy, int years) {

            double rate = apy / 100.0;
            int compoundsPerYear = 12;

            return principal * Math.pow( 1 + (rate / compoundsPerYear),compoundsPerYear * years);
        }

    }





