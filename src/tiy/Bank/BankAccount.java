package tiy.Bank;
import java.io.File;
import java.util.Scanner;


public class BankAccount {
    private String name;
    private double balance;


    public double deposit(double amountToDeposit) {
        if (amountToDeposit < 0) {
            System.out.println("You cannot deposit a negative amount of money!");
            System.out.println("Deposit canceled.");
        } else {
            balance += amountToDeposit;
        }
        return balance;
    }

    public double withdraw(double amountToWithdraw) {
        if (amountToWithdraw < 0) {
            System.out.println("You cannot withdraw a negative amount of money!");
            System.out.println("Withdrawal canceled.");
        } else if (balance - amountToWithdraw < 0) {
            System.out.println("There is not enough money in your account!");
            System.out.println("Withdrawal canceled.");
        } else {
            balance -= amountToWithdraw;
        }
        return balance;
    }



    public double transfer(double amountToTransfer, BankAccount accountToTransferTo) {
        if (amountToTransfer < 0) {
            System.out.println("You cannot transfer a negative amount of money!");
            System.out.println("Transfer canceled.");
            return balance;
        } else if (balance - amountToTransfer < 0) {
            System.out.println("There is not enough money in your account to transfer that amount!");
            System.out.println("Transfer canceled.");
            return balance;
        } else {
            System.out.println("You chose to transfer to: " + accountToTransferTo.getName() + "(balance: " + accountToTransferTo.getBalance() + ")");
            System.out.println("Transfer successful!");
            accountToTransferTo.balance += amountToTransfer;
            balance -= amountToTransfer;
            System.out.println("New balance in the account you transferred to: " + accountToTransferTo.getBalance());
            return balance;
        }
    }

    public void printInfo() {
        System.out.println("Printing bank account info...");
        System.out.println("\tName: " + name);
        System.out.println("\tBalance: " + balance);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

