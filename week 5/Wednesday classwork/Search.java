import java.util.List;

public class Search {
    private static int pass = 0;

    public static int binarySearch(int[] arr, int value, int startPos, int endPos) {
        pass++;
        if(startPos == endPos && arr[startPos] != value) {
            return -1;
        }
        if(startPos == endPos - 1 && arr[startPos] != value) {
            return -1;
        }
        int midPos = (startPos + endPos) / 2;
        if(arr[midPos] == value) {
            return midPos;
        } else if(arr[midPos] < value) {
            startPos = midPos;
            return binarySearch(arr, value, startPos, endPos);
        } else {
            endPos = midPos;
            return binarySearch(arr, value, startPos, endPos);
        }
    }

    //aMAXing
    public static int binarySearch(List<Integer> list, int value, int startPos, int endPos) {
        pass++;
        if(startPos == endPos && list.get(startPos) != value) {
            return -1;
        }
        if(startPos == endPos - 1 && list.get(startPos) != value) {
            return -1;
        }
        int midPos = (startPos + endPos) / 2;
        int midNum = list.get(midPos);
        if(midNum == value) {
            return midPos;
        } else if(midNum < value) {
            startPos = midPos;
            return binarySearch(list, value, startPos, endPos);
        } else {
            endPos = midPos;
            return binarySearch(list, value, startPos, endPos);
        }
    }

    public static void resetPass() {
        pass = 0;
    }

    public static int passes() {
        return pass;
    }
}
