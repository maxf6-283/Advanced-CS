public class Runner {
    public static void main(String[] args) {
        MaxHeap<Double> maxHeap = new MaxHeap<>();
        double[] nums = {1.5, 1.6, 2.3, 6.4, 9.3, 3.1, 8.2, 3.4};

        for(double n : nums) {
            maxHeap.add(n);
        }

        System.out.println(maxHeap);

        System.out.println(maxHeap.peek());

        System.out.println();

        while(!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll());
        }
    }
}
