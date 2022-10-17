import lombok.SneakyThrows;

public class Deadlock {

    @SneakyThrows
    public static void transferMoney(Account from, Account to, double amount) {
        synchronized (from) {
            Thread.sleep(100);
            synchronized (to) {
                from.withdrawMoney(amount);
                to.topUpBalance(amount);
            }
        }
    }


    public static void main(String[] args) {
        var from = new Account(500);
        var to = new Account(1000);

        var thread1 = new Thread(() -> transferMoney(from, to, 100));
        var thread2 = new Thread(() -> transferMoney(to, from, 100));

        thread1.start();
        thread2.start();
    }

}
