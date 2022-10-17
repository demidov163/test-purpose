import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicBarrierExample {
    static class Worker implements Runnable {
        private CyclicBarrier phase1;
        private CyclicBarrier phase2;

        public Worker(CyclicBarrier phase1, CyclicBarrier phase2) {
            this.phase1 = phase1;
            this.phase2 = phase2;
        }

        @SneakyThrows
        @Override
        public void run() {
            System.out.println("Phase1 has done");
            phase1.await();
            System.out.println("Phase2 has done");
            phase2.await();
            System.out.println("Phase3 has done");
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier1 = new CyclicBarrier(5);
        CyclicBarrier cyclicBarrier2 = new CyclicBarrier(5);

        IntStream.range(0, 5).forEach((i) -> new Thread(new Worker(cyclicBarrier1, cyclicBarrier2)).start());


    }
}
