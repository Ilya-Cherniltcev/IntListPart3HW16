package pro.sky.homework2_14.IntListPart3HW16.data;

public class GenerateArray {

    public Integer[] getRandomArray(int size) {
        java.util.Random random = new java.util.Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }
}
