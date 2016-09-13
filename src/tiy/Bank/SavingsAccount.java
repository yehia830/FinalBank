package tiy.Bank;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by jessicatracy on 8/18/16.
 */
public class SavingsAccount extends BankAccount implements Runnable {
    private double interestRate;
    private int sleepTime;
    private long startRunTime;
    private long finishRunTime;

    public SavingsAccount() {
        super();
        setName("Savings");
        interestRate = 1.05;
        sleepTime = 10000;
    }

    public SavingsAccount(double accountBalance) {
        super();
        setName("Savings");
        interestRate = 1.05;
        sleepTime = 10000;
        this.setBalance(accountBalance);
    }

    public SavingsAccount(long startRunTime, double accountBalance) {
        super();
        setName("Savings");
        this.setBalance(accountBalance);
        interestRate = 1.05;
        sleepTime = 10000;
        this.startRunTime = startRunTime;
//        System.out.println("Start run time: " + startRunTime);
        //read in finish run time from file.
        this.finishRunTime = readFinishTime();
//        System.out.println("Finish run time: " + finishRunTime);
        long timeElapsed = startRunTime - finishRunTime;
//        System.out.println("Elapsed time: " + timeElapsed);
        double timeElapsedInMilliseconds = (double)timeElapsed / 1000000;
        double numIntervals = (timeElapsedInMilliseconds / sleepTime);
        for (int counter = 0; counter < numIntervals; counter++) {
            this.setBalance(this.getBalance() * interestRate);
        }
    }

    public long readFinishTime() {
        try {
            File myFinishTimeFile = new File("finishRunTime.txt");
            Scanner myFileScanner = new Scanner(myFinishTimeFile);
            String currentLine = myFileScanner.nextLine();
            finishRunTime = Long.valueOf(currentLine);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return finishRunTime;
    }

    public void run() {
        try {
            while (Assignment9Runner.runThreads) {
//            while (true) {
//                System.out.println("Savings thread running");
                Thread.sleep(sleepTime);
                double newBalWithInterest = getBalance() * interestRate;
                setBalance(newBalWithInterest);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // getters and setters

    public long getStartRunTime() {
        return startRunTime;
    }

    public void setStartRunTime(long startRunTime) {
        this.startRunTime = startRunTime;
    }

    public long getFinishRunTime() {
        return finishRunTime;
    }

    public void setFinishRunTime(long finishRunTime) {
        this.finishRunTime = finishRunTime;
    }
}
