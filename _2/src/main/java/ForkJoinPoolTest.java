import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {
    public static final int thread_transaction_threshold = 6;
    public static final int large_transaction_threshold = 10_000;

    public static void main(String[] args) {
        var forkJoinPool = ForkJoinPool.commonPool();
        int[] transactions = new int[]{555, 44_444, 5553, 33_213, 34, 3434, 7775, 31, 341_434, 686_843, 32_434, 222,
                42_521, 7460, 2342, 77_777, 522, 2355, 6474, 4_322_145, 45_243, 7657, 3111, 5, 6547, 43, 64_564,
                32_4342, 6_521, 76_313};
        var result = forkJoinPool.invoke(new CustomRecursiveTask(transactions));
        System.out.println("Transaction with large " + result);
    }

    @Log
    static class CustomRecursiveTask extends RecursiveTask<Long> {
        private final int[] transactions;

        public CustomRecursiveTask(int[] transactions) {
            this.transactions = transactions;
        }

        @Override
        protected Long compute() {
            if (transactions.length > thread_transaction_threshold) {
                log.info("Split and aggregate " + Arrays.toString(transactions) + " by " + Thread.currentThread().getName());
                return ForkJoinTask.invokeAll(splitTask())
                        .stream()
                        .mapToLong(ForkJoinTask::join)
                        .sum();
            } else {
                log.info("Processing " + Arrays.toString(transactions) + " by " + Thread.currentThread().getName());
                return Arrays.stream(transactions)
                        .filter(t -> t > large_transaction_threshold)
                        .count();
            }
        }

        private Collection<CustomRecursiveTask> splitTask() {
            return Arrays.asList(new CustomRecursiveTask(Arrays.copyOfRange(transactions,
                    0,
                    transactions.length / 2)),
                    new CustomRecursiveTask(Arrays.copyOfRange(transactions,
                            transactions.length / 2, transactions.length)));
        }
    }
}
