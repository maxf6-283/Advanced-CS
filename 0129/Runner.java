public class Runner {
    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();
        for(int n : new int[]{90, 80, 100, 70, 85, 98, 120}) {
            tree.add(n);
        }
        System.out.println(tree);
        System.out.println(tree.toStringPreOrder());

        System.out.printf("Height: %d%n", tree.getHeight());
        System.out.printf("Level: %d%n", tree.getLevel());
        System.out.printf("Height of 100: %d%n", tree.getHeight(100));
        System.out.printf("Height of 70:   %d%n", tree.getHeight(70));

        tree.clear();
        for(int n : new int[]{90, 150, 170, 160, 171, 151}) {
            tree.add(n);
        }
        System.out.println(tree);
        System.out.println(tree.toStringPreOrder());

        System.out.printf("Height: %d%n", tree.getHeight());
        System.out.printf("Level: %d%n", tree.getLevel());
        System.out.printf("Height of 150: %d%n", tree.getHeight(150));
        System.out.printf("Height of 151:   %d%n", tree.getHeight(151));
    }
}
