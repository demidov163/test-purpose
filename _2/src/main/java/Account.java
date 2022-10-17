public class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public double withdrawMoney(double amount) {
        if (balance < amount) {
            throw new IllegalArgumentException("not enough money for withdraw");
        }
        return balance -= amount;
    }

    public double topUpBalance(double amount) {
        return balance += amount;
    }
}
