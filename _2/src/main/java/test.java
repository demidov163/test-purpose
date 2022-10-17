import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] strs) {
        List<Pair> pairs = Arrays.asList(new Pair(1, "v1"), new Pair(1, "v2")); //p1(k1, v1) p2(k1, v2)
        Map<Integer, String> pairsMap = pairs.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));
        Map<Integer, List<String>> pairsAllValues = pairs.stream()
                .collect(Collectors.toMap(Pair::getKey,
                        (pair) -> {
                            List<String> list = new ArrayList<>();
                            list.add(pair.value);
                            return list;
                        },
                        (v1, v2) -> { v1.addAll(v2); return v1; }));
        System.out.print(pairsAllValues);



    }

    static class Pair {
        private Integer key;
        private String value;

        public Pair(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
