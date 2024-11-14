public class Runner {
    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>();

        for(int i = 0; i < 10; i++) {
            heap.add((int)(Math.random() * 100 + 1));
        }

        System.out.println(heap);

        System.out.println(heap.peek());

        while(!heap.isEmpty()) {
            System.out.println(heap.poll());
        }
    }
}
