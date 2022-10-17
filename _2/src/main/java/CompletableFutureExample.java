import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String args[]) {
        CompletableFuture
                .runAsync(() -> System.out.println("runAsync " + Thread.currentThread().getName()))
                .thenAcceptAsync(unused -> System.out.println("thenAccept " + Thread.currentThread().getName())).join();

        System.out.println("main end " + Thread.currentThread().getName());

    }
}
