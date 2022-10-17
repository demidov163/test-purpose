import org.apache.commons.codec.digest.MurmurHash3;

public class BloomFilter {
    private int[] array;

    public BloomFilter(int m) {
        array = new int[m];
    }

    private int computeHash(String k) {
        return MurmurHash3.hash32x86(k.getBytes());
    }

    public void addToFilter(String k) {
        array[Math.abs(computeHash(k)) & array.length] = 1;
    }

    public void deleteFromFilter(String k) {
        array[Math.abs(computeHash(k)) & array.length] = 0;
    }

    public boolean isAbsent(String k) {
        return array[Math.abs(computeHash(k)) & array.length] == 0;
    }

    public boolean isPresent(String k) {
        return array[Math.abs(computeHash(k)) & array.length] == 1;
    }

    public static void main(String[] strs) {

        var filter  = new BloomFilter(100);
        filter.addToFilter("abc");
        filter.addToFilter("gth");
        filter.addToFilter("drt");
        filter.addToFilter("pot");
        filter.addToFilter("fart");
        System.out.println("new-----------------");
        System.out.println(filter.isAbsent("rat"));
        System.out.println(filter.isAbsent("cat"));
        System.out.println(filter.isAbsent("dog"));
        System.out.println(filter.isAbsent("face"));
        System.out.println(filter.isAbsent("grow"));
        System.out.println("old-----------------");
        System.out.println(filter.isAbsent("fart"));
        System.out.println(filter.isAbsent("abc"));
        System.out.println(filter.isAbsent("drt"));
        System.out.println(filter.isAbsent("pot"));
        System.out.println(filter.isAbsent("gth"));

        System.out.println("-----------------");

        System.out.println(filter.isPresent("abc"));
        System.out.println(filter.isPresent("drt"));
        System.out.println(filter.isPresent("pot"));
        System.out.println(filter.isPresent("fart"));


    }

}
