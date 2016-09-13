package tiy.Bank;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Assignment9Runner {
    public static boolean runThreads = true;

    public static void main(String[] args) {
        System.out.println("Assignment9Runner running....");

        Assignment9Runner myRunner = new Assignment9Runner();

        myRunner.runProgram(myRunner);
        System.exit(0);
    }

    public void runProgram(Assignment9Runner myRunner) {
        long startRunTime = System.nanoTime();
        Bank myBank = new Bank("4-1 Bank");


        myRunner.readFileInfoInBank(myBank, startRunTime);

        Scanner myScanner = new Scanner(System.in);
        System.out.print("Welcome to the 4-1 bank. What is your first name? And If this Dom, you lose. ");
        String userName = myScanner.nextLine();
        Customer myCustomer = new Customer(userName);


        boolean onList = checkIfCustomerIsOnlist(myBank, myCustomer);


        if (!onList) {

            System.out.print("Welcome new customer! How many accounts do you have? ");
            int userNumAccounts = myScanner.nextInt();
            myScanner.nextLine();
            System.out.println("Entering info for " + userNumAccounts + " accounts...");


            for (int counter = 0; counter < userNumAccounts; counter++) {
                System.out.println("Account " + (counter + 1) + ":");
                makeNewAccountWithUserInput(myCustomer);
            }


            myBank.addCustomer(myCustomer);

        } else {
            System.out.println("Welcome back, " + userName + "!");
        }


        for (Customer customer : myBank.getCustomerList()) {
            if (customer.getName().equals(userName)) {
                myCustomer = customer;
            }
        }

        boolean keepGoing = true;

        while (keepGoing) {
            int acctChoiceInt = userChooseWhichAccountToUse(myCustomer, myScanner);

            while (acctChoiceInt == 0) {
                makeNewAccountWithUserInput(myCustomer);
                acctChoiceInt = userChooseWhichAccountToUse(myCustomer, myScanner);
            }
            BankAccount acctChoice = myCustomer.getCustomerListOfAccounts().get(acctChoiceInt - 1);


            keepGoing = displayAccountActionsMenu(acctChoice, myCustomer, myBank);

        }

    }

    public boolean checkIfCustomerIsOnlist(Bank myBank, Customer myCustomer) {
        for (Customer people : myBank.getCustomerList()) {

            if (myCustomer.getName().equalsIgnoreCase(people.getName())) {
                return true;
            }
        }
        return false;
    }

    public void readFileInfoInBank(Bank myBank, long startRunTime) {
        ArrayList<String> accountNameList = new ArrayList<String>();
//
        File myFile = new File("ListOfCustomers.txt");
        if (myFile.exists()) {
            try {
                Scanner fileScanner = new Scanner(myFile);
                String currentLine = fileScanner.nextLine();
                String[] splitCurrentLine = currentLine.split(",");
                for (String name : splitCurrentLine) {
                    accountNameList.add(name);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            for (String name : accountNameList) {
                Customer myCustomer = new Customer(name + ".txt", name, startRunTime);
                myBank.addCustomer(myCustomer);
            }
        }
    }

    public void makeNewAccountWithUserInput(Customer myCustomer) {
        Scanner myScanner = new Scanner(System.in);
        boolean threadsKeepRunning = true;

        BankAccount thisAccount;
        while (true) {
            System.out.println("What type of account is this account?");
            System.out.println(" 1. Checking");
            System.out.println(" 2. Savings");
            System.out.println(" 3. Retirement");
            int typeAcct = myScanner.nextInt();
            myScanner.nextLine();
            if (typeAcct == 1) {
                thisAccount = new CheckingAccount();
                break;
            } else if (typeAcct == 2) {
                thisAccount = new SavingsAccount();

                SavingsAccount myAccountS = new SavingsAccount();
                myAccountS = (SavingsAccount)thisAccount;
                Thread savingsThread = new Thread(myAccountS);
                thisAccount = myAccountS;
                savingsThread.start();
                break;
            } else if (typeAcct == 3) {
                thisAccount = new RetirementAccount();

                RetirementAccount myAccountR = new RetirementAccount();
                myAccountR = (RetirementAccount)thisAccount;
                Thread retirementThread = new Thread(myAccountR);
                thisAccount = myAccountR;
                retirementThread.start();
                break;
            } else {
                System.out.println("Not valid.");
            }
        }
        System.out.print("What is the balance of this account? ");
        double thisBalance = myScanner.nextDouble();
        thisAccount.setBalance(thisBalance);
        myScanner.nextLine();

        myCustomer.addBankAccount(thisAccount);
    }


    public int userChooseWhichAccountToUse(Customer myCustomer, Scanner myScanner) {
        System.out.println("Which account would you like to use?");
        int counter = 1;
        for (BankAccount account : myCustomer.getCustomerListOfAccounts()) {
            System.out.println(" " + counter + ". " + account.getName() + " (Balance: " + account.getBalance() + ")");
            counter++;
        }
        System.out.println(" 0. Add a new account");
        int acctChoiceInt = myScanner.nextInt();
        myScanner.nextLine();

        return acctChoiceInt;
    }

    public int askUserWhatAccountActionToDo(Scanner myScanner) {
        System.out.println("What would you like to do?");
        System.out.println(" 1. Withdraw");
        System.out.println(" 2. Deposit");
        System.out.println(" 3. Transfer");
        System.out.println(" 4. Print account info");
        System.out.println(" 5. Print bank info");
        System.out.println(" 6. Select another account");
        System.out.println(" 7. Exit");

        int userChoseToDo = myScanner.nextInt();
        myScanner.nextLine();

        return userChoseToDo;
    }

    public boolean displayAccountActionsMenu(BankAccount acctChoice, Customer myCustomer, Bank myBank) {
        Scanner myScanner = new Scanner(System.in);
        while (true) {
            int userChoseToDo = askUserWhatAccountActionToDo(myScanner);

            if (userChoseToDo == 1) {
                System.out.println("Current balance: " + acctChoice.getBalance());
                System.out.print("How much would you like to withdraw? ");
                double withdrawAmount = myScanner.nextDouble();
                myScanner.nextLine();
                double newBal = acctChoice.withdraw(withdrawAmount);
                System.out.println("New balance: " + newBal);
                System.out.println();
            } else if (userChoseToDo == 2) {
                System.out.println("Current balance: " + acctChoice.getBalance());
                System.out.print("How much would you like to deposit? ");
                double depositAmount = myScanner.nextDouble();
                myScanner.nextLine();
                double newBal = acctChoice.deposit(depositAmount);
                System.out.println("New balance: " + newBal);
                System.out.println();
            }

            else if (userChoseToDo == 3) {
                System.out.println("Current balance: " + acctChoice.getBalance());
                System.out.print("How much would you like to transfer from this account? ");
                Double transferAmount = myScanner.nextDouble();
                myScanner.nextLine();
                System.out.println("To which account would you like to transfer the money?");
                int counter2 = 1;
                for (BankAccount account : myCustomer.getCustomerListOfAccounts()) {
                    System.out.println(" " + counter2 + ". " + account.getName());
                    counter2++;
                }
                int transferChoiceInt = myScanner.nextInt();
                myScanner.nextLine();

                acctChoice.setBalance(acctChoice.transfer(transferAmount, myCustomer.getCustomerListOfAccounts().get(transferChoiceInt - 1)));
                System.out.println("New balance in current account: " + acctChoice.getBalance());
                System.out.println();
            }
            else if (userChoseToDo == 4) {
                acctChoice.printInfo();
            } else if (userChoseToDo == 5) {
                myBank.printInfo();
            } else if (userChoseToDo == 6) {

                myCustomer.customerListOfAccountsToFile();
                break;
            } else if (userChoseToDo == 7) {

                myCustomer.customerListOfAccountsToFile();

                myBank.customerListToFile();
                Assignment9Runner.runThreads = false;

                writeFinishRunTimeFile();
                return false;

            } else {
                System.out.println("Wrong! Try again");
            }
        }
        return true;
    }

    public void writeFinishRunTimeFile() {
        try {
            long now = System.nanoTime();
            File myFinishRunTimeFile = new File("finishRunTime.txt");
            FileWriter myFinishRunTimeWriter = new FileWriter(myFinishRunTimeFile);

            myFinishRunTimeWriter.write("" + now);
            myFinishRunTimeWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}