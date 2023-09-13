import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        
        int[] arr1 = new int[16];
        int[] arr2 = new int[32];
        int[] arr3 = new int[64];

        for (int[] arr : new int[][] { arr1, arr2, arr3 }) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) (Math.random() * 1000);
            }
        }

        MyArrayList<Integer> list1 = new MyArrayList<>();
        for (int i = 0; i < 16; i++) {
            list1.add((int) (Math.random() * 1000));
        }

        MyArrayList<Integer> list2 = new MyArrayList<>();
        for (int i = 0; i < 32; i++) {
            list2.add((int) (Math.random() * 1000));
        }

        MyArrayList<Integer> list3 = new MyArrayList<>();
        for (int i = 0; i < 64; i++) {
            list3.add((int) (Math.random() * 1000));
        }

        // sorting
        for (int[] arr : new int[][] { arr1, arr2, arr3 }) {
            for (int i = 0; i < arr.length - 2; i++) {
                int maxIndex = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < arr[maxIndex]) {
                        maxIndex = j;
                    }
                }

                int temp = arr[maxIndex];
                arr[maxIndex] = arr[i];
                arr[i] = temp;
            }
        }

        for (MyArrayList<Integer> list : List.of(list1, list2, list3)) {
            for (int i = 0; i < list.size() - 2; i++) {
                int maxIndex = i;
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(j) < list.get(maxIndex)) {
                        maxIndex = j;
                    }
                }

                int temp = list.get(maxIndex);
                list.set(maxIndex, list.get(i));
                list.set(i, temp);
            }
        }

        for (int[] arr : new int[][] { arr1, arr2, arr3 }) {
            // print array
            String str = "[";
            for(int i = 0; i < arr.length-1; i++) {
                str += arr[i] + ", ";
            }
            System.out.println(str + arr[arr.length - 1]+"]");

            //ask for number
            System.out.println("Enter a number to search for: ");
            int num = sc.nextInt();
            
            int index = Search.binarySearch(arr, num, 0, arr.length);
            System.out.println("The number was found at " + index + " in " + Search.passes() + " passes.");
            Search.resetPass();
        }
        
        //print lists
        for(MyArrayList<Integer> list : List.of(list1, list2, list3)) {
            System.out.println(list);
            
            //ask for number
            System.out.println("Enter a number to search for: ");
            int num = sc.nextInt();
            
            int index = Search.binarySearch(list, num, 0, list.size());
            System.out.println("The number was found at index " + index + " in " + Search.passes() + " passes.");
            Search.resetPass();
        }
        
        sc.close();
    }
}
