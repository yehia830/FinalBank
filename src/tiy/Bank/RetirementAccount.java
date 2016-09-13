package tiy.Bank;

/**
 * Created by Yehia830 on 9/4/16.
 */
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Scanner;


public class RetirementAccount extends BankAccount implements Runnable {
    private double interestRate;
    private int sleepTime;
    private long startRunTime;
    private long finishRunTime;

    public RetirementAccount() {
        super();
        setName("Retirement");
        interestRate = 1.10;
        sleepTime = 120000;
    }

    public RetirementAccount(double accountBalance) {
        super();
        setName("Retirement");
        interestRate = 1.10;
        sleepTime = 120000;
        this.setBalance(accountBalance);
    }

    public RetirementAccount(long startRunTime, double accountBalance) {
        super();
        setName("Retirement");
        this.setBalance(accountBalance);
        interestRate = 1.10;
        sleepTime = 120000;
        this.startRunTime = startRunTime;

        this.finishRunTime = finishTime();
        long timeElapsed = startRunTime - finishRunTime;
        double timeElapsedInMilliseconds = (double)timeElapsed / 1000000;
        double numIntervals = (timeElapsedInMilliseconds / sleepTime);
        for (int counter = 0; counter < numIntervals; counter++) {
            this.setBalance(this.getBalance() * interestRate);
        }
    }

    public long finishTime() {
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
//                System.out.println("Retirement thread running");
                Thread.sleep(sleepTime);
                double newBalWithInterest = getBalance() * interestRate;
                setBalance(newBalWithInterest);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // Getters and setters
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
