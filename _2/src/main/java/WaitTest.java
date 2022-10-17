public class WaitTest {
    public static void main(String[] args) {
        try {
            var o = new Object();
            synchronized (o) {

                o.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
