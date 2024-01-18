public class RunnerII {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        for(int n : new int[]{90, 80, 100, 70, 85, 98, 12}) {
            bst.add(n);
        }

        System.out.println(bst);
    }
}