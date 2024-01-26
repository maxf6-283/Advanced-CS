public class Runner {
    public static void main(String[] args) {
        BinaryTree<Double> tree = new BinaryTree<>();
        for (double d : new double[] { 9.0, 8.0, 10.0, 7.0, 8.5, 9.8, 120.0 }) {
            tree.add(d);
        }

        System.out.println(tree);
        System.out.println("Tree " + (tree.contains(120.0) ? "contains" : "does not contain") + " 120.0");
        System.out.println("Tree " + (tree.contains(9.9) ? "contains" : "does not contain") + " 9.9");
    }
}
