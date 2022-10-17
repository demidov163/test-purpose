import lombok.SneakyThrows;

public class NotifyWaitExample {
    public static class Data {
        private String msg;
        private boolean transfered = false;

        public synchronized void send(String msg)  {
            while (transfered) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            this.msg = msg;
            System.out.println("Msg " + msg + " was sent " + Thread.currentThread().getName());
            transfered = true;
            notifyAll();
        }

        public synchronized String receive() {
            while (!transfered) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            String receivedMsg = msg;
            System.out.println("Msg " + receivedMsg + " was received " + Thread.currentThread().getName());
            transfered = false;
            notifyAll();
            return receivedMsg;
        }
    }


    public static class Sender implements Runnable {
        private Data data;

        public Sender(Data data) {
            this.data = data;
        }

        @SneakyThrows
        @Override
        public void run() {
            String[] array = new String[] {"msg 1", " msg 2", "msg 3", "msg 4", "msg 5", "end"};
            for (String msg : array) {
                data.send(msg);
                Thread.currentThread().sleep(500);
            }
        }
    }

    public static class Receiver implements Runnable {
        private Data data;

        public Receiver(Data data) {
            this.data = data;
        }

        @SneakyThrows
        @Override
        public void run() {
            String msg = data.receive();
            while (!"end".equals(msg)) {
                Thread.currentThread().sleep(400);
                msg = data.receive();
            }
        }
    }

    public static void main(String[] args) {
        Data data = new Data();

        var receiver = new Receiver(data);
        var sender = new Sender(data);
        new Thread(receiver).start();
        new Thread(sender).start();



    }
}
