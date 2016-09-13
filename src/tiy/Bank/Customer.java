package tiy.Bank;

import java.util.ArrayList;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Customer {
    private String name;
    private ArrayList<BankAccount> customerListOfAccounts = new ArrayList<BankAccount>(); //You have to initialize here or else it will be null!!

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String fileName, String name, long startRunTime) {
        this.name = name;
        try {
            File customerAccountFile = new File(fileName);
            Scanner fileScanner = new Scanner(customerAccountFile);

            BankAccount myAccount = null;
            String accountName;
            double accountBalance;
            String currentLine;
            String[] myArray;
            while (fileScanner.hasNext()) {
                currentLine = fileScanner.nextLine();
                myArray = currentLine.split("=");
                accountName = myArray[1];


                currentLine = fileScanner.nextLine();
                myArray = currentLine.split("=");
                accountBalance = Double.valueOf(myArray[1]);

                if (accountName.equals("Checking")) {
                    myAccount = new CheckingAccount(accountBalance);
                } else if (accountName.equals("Savings")) {

                    myAccount = new SavingsAccount(startRunTime, accountBalance);

                } else if (accountName.equals("Retirement")) {

                    myAccount = new RetirementAccount(startRunTime, accountBalance);
//
                }


                if (myAccount != null) {

                    customerListOfAccounts.add(myAccount);

                    if (myAccount.getName().equals("Savings")) {
                        SavingsAccount myAccountS = (SavingsAccount)myAccount;
                        Thread savingsThread = new Thread(myAccountS);
                        savingsThread.start();
                    }
                    if (myAccount.getName().equals("Retirement")) {
                        RetirementAccount myAccountR = (RetirementAccount)myAccount;
                        Thread retirementThread = new Thread(myAccountR);
                        retirementThread.start();
                    }
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void printInfo() {
        System.out.println("Printing customer info...");
        System.out.println("Name: " + name);
        System.out.println("Number of accounts: " + getNumAccounts());
        System.out.println("Accounts:");
        for (BankAccount account: customerListOfAccounts) {
            account.printInfo();
        }
    }

    public void addBankAccount(BankAccount account) {
        customerListOfAccounts.add(account);
    }

    public int getNumAccounts() {
        int numAccounts = 0;
        for (BankAccount account : customerListOfAccounts) {
            numAccounts++;
        }
        return numAccounts;
    }


    public void customerListOfAccountsToFile() {
        try {
            File customerListOfAccountsFile = new File(name + ".txt");
            FileWriter customerListOfAccountsWriter = new FileWriter(customerListOfAccountsFile);
            for (BankAccount account : customerListOfAccounts) {
                customerListOfAccountsWriter.write("account.name=" + account.getName() + "\n");
                customerListOfAccountsWriter.write("account.balance=" + account.getBalance() + "\n");
            }
            customerListOfAccountsWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void customerListOfAccountsToFile2() {

        String holdCurrentLine = "";
        try {
            File customerAccountFile = new File(name + ".txt");
            Scanner fileScanner = new Scanner(customerAccountFile);
            while (fileScanner.hasNext()) {
                holdCurrentLine += fileScanner.nextLine() + "\n";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            File customerListOfAccountsFile = new File(name + ".txt");
            FileWriter customerListOfAccountsWriter = new FileWriter(customerListOfAccountsFile);
            customerListOfAccountsWriter.write(holdCurrentLine);
            for (BankAccount account : customerListOfAccounts) {
                customerListOfAccountsWriter.write("account.name=" + account.getName() + "\n");
                customerListOfAccountsWriter.write("account.balance=" + account.getBalance() + "\n");
            }
            customerListOfAccountsWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BankAccount> getCustomerListOfAccounts() {
        return customerListOfAccounts;
    }

    public void setCustomerListOfAccounts(ArrayList<BankAccount> customerListOfAccounts) {
        this.customerListOfAccounts = customerListOfAccounts;
    }
}

