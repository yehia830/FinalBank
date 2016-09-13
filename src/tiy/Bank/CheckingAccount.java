package tiy.Bank;

public class CheckingAccount extends BankAccount {

    public CheckingAccount() {
        super();
        setName("Checking");
    }

    public CheckingAccount(double balance) {
        super();
        setName("Checking");
        this.setBalance(balance);
    }
}