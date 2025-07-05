import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        int i = 1;
        while (!StdIn.isEmpty()) {
            String cur = StdIn.readString();
            if (StdRandom.bernoulli((double) 1 / i)) {
                champion = cur;
            }
            i += 1;
        }
        StdOut.println(champion);
    }
}
