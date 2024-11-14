public class Runner{
    public static void main(String[] args) {
        // part 1
        BST<Integer> bst = new BST<>();
        int[] nums = {90, 80, 100, 70, 85, 98, 120};
        for (int each : nums) {
            bst.add(each);
        }
        System.out.println("Part 1:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
        System.out.println("Contains 85? " + bst.contains(85));
        System.out.println("Contains 86? " + bst.contains(86));
        
        // part 2
        bst.remove(70);
        bst.remove(120);
        System.out.println("\nPart 2:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
        
        // part 3
        bst = new BST<>();
        nums = new int[]{90, 80, 100, 70, 85, 98, 120};
        for (int each : nums) {
            bst.add(each);
        }
        bst.remove(100);
        System.out.println("\nPart 3:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
        
        // part 4
        bst = new BST<>();
        nums = new int[] {90, 80, 100, 98, 91, 99};
        for (int each : nums) {
            bst.add(each);
        }
        bst.remove(100);
        System.out.println("\nPart 4:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
        
        // part 5
        bst = new BST<>();
        nums = new int[]{90, 100, 98, 110};
        for (int each : nums) {
            bst.add(each);
        }
        bst.remove(90);
        System.out.println("\nPart 5:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
        
        // part 6
        bst = new BST<>();
        nums = new int[] {90, 80, 100, 98, 110, 91, 99};
        for (int each : nums) {
            bst.add(each);
        }
        bst.remove(90);
        System.out.println("\nPart 6:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
        
        // part 7
        bst = new BST<>();
        nums = new int[] {90};
        for (int each : nums) {
            bst.add(each);
        }
        bst.remove(90);
        System.out.println("\nPart 7:");
        System.out.println("In-order: " + bst);
        System.out.println("Pre-order: " + bst.toStringPreOrder());
    }
}