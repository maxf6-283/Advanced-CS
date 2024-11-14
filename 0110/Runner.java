import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Runner {
    public static void main(String[] args) {
        String[] letters = "a f d d e v j q w e e a e z f g j".split(" ");
        Set<String> hashLetters = new HashSet<>();
        Set<String> treeLetters = new TreeSet<>();
        for(String letter : letters) {
            hashLetters.add(letter);
            treeLetters.add(letter);
        }
        System.out.println("HashSet of letters: " + hashLetters);
        System.out.println("TreeSet of letters: " + treeLetters);
        
        String[] words = "one two three one two three six seven one two".split(" ");
        Set<String> h1 = new HashSet<>();
        Set<String> t1 = new TreeSet<>();
        for (String word : words) {
            h1.add(word);
            t1.add(word);
        }
        System.out.println("HashSet of words: " + h1);
        System.out.println("TreeSet of words: " + t1);


        int[] nums = {3, 5, 4, 7, 5, 17, 29, 17, 4, 6, 56, 72, 6};
        Set<Integer> hSet = new HashSet<>();
        Set<Integer> tSet = new TreeSet<>();
        for(int n : nums) {
            hSet.add(n);
            tSet.add(n);
        }
        System.out.println("HashSet of integers: " + hSet);
        System.out.println("TreeSet of integers: " + tSet);
    }
}