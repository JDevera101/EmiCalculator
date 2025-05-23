package emi;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class LoanCalculatorApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter loan amount: ");
        double loanAmount = input.nextDouble();
        System.out.print("Enter annual interest rate (%): ");
        double annualRate = input.nextDouble();
        double monthlyRate = annualRate / 12 / 100;
        System.out.print("Enter loan term (years): ");
        int years = input.nextInt();
        int months = years * 12;
        double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, months))
                   / (Math.pow(1 + monthlyRate, months) - 1);
        System.out.printf("\nYour EMI is: %.2f\n", emi);
        // Puts this output into a .txt file
        try {
            FileWriter writer = new FileWriter("emi_history.txt", true);
            writer.write("Loan: " + loanAmount + ", Rate: " + annualRate + "%, Years: " + years + ", EMI: " + String.format("%.2f", emi) + "\n");
            writer.close();
            System.out.println("\nCalculation saved to emi_history.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }

        input.close();
    }
}
