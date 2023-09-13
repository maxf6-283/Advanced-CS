public class Runner {
    public static void main(String[] args) {
        MyArrayList<Integer> arrList = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            arrList.add((int) (Math.random() * 100));
        }
        
        System.out.println(arrList);
        
        int totalOperations = 0;
        int timesToTest = 100;
        
        for (int time = 0; time < timesToTest; time++) {
            arrList = new MyArrayList<>();
            for (int i = 0; i < 1000; i++) {
                arrList.add((int) (Math.random() * 100));
            }
            for (int i = arrList.size() - 1; i > 2; i--) {
                for (int j = 0; j < i; j++) {
                    if (arrList.get(j) > arrList.get(j + 1)) {
                        int temp = arrList.get(j);
                        arrList.set(j, arrList.get(j + 1));
                        arrList.set(j + 1, temp);
                        totalOperations ++;
                    }
                }
            }
        }

        System.out.println(arrList);

        System.out.println(totalOperations/timesToTest);
    }
}
