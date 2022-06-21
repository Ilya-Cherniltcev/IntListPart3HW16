package pro.sky.homework2_14.IntListPart3HW16.services;

import org.springframework.stereotype.Service;
import pro.sky.homework2_14.IntListPart3HW16.data.GenerateArray;
import pro.sky.homework2_14.IntListPart3HW16.interfaces.IntegerList;
import pro.sky.homework2_14.IntListPart3HW16.exceptions.AbsentElementException;
import pro.sky.homework2_14.IntListPart3HW16.exceptions.EmptyArrayException;
import pro.sky.homework2_14.IntListPart3HW16.exceptions.IndexOutOfArrayException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class IntegerListImpl implements IntegerList {
    private GenerateArray generateArray;
    private Integer[] strList;
    //    private Integer[] sortedArray;
    private int length = 0;

    public IntegerListImpl(int len) {
        generateArray = new GenerateArray();
        this.strList = generateArray.getRandomArray(len);
        this.length = len;
    }

    public IntegerListImpl() {
        this.strList = new Integer[15];
    }

    @Override
    // *****  показать все заполненные элементы массива *****
    public Integer[] getAll() {
        return Arrays.stream(strList)
                .limit(length)
                .toArray(Integer[]::new);
    }

    // *****  расширяем массив в 1.5 раза *****
    private void grow() {
        int l = (int) (strList.length * 1.5);
        strList = Arrays.copyOf(strList, l);
    }

    @Override
    // Добавление элемента.  Вернуть добавленный элемент в качестве результата выполнения.
    public Integer add(Integer item) {
        if (strList.length == length) {
            grow();
        }
//        if (strList.length == 0) {
//            strList = new Integer[1];
//        } else {
//            strList = Arrays.copyOf(strList, strList.length + 1);
//        }
        strList[length] = item;
        length++;
        return item;
    }

    // Добавление элемента на определенную позицию списка.
    // Если выходит за пределы фактического количества элементов или массива,
    // выбросить исключение.  Вернуть добавленный элемент в качестве результата выполнения.
    @Override
    public Integer add(int index, Integer item) {
        validateIndex(index);
        if (strList.length == length) {
            grow();
        }

        if (index != 0) {
            Integer[] firstPart = new Integer[index];
            System.arraycopy(strList, 0, firstPart, 0, index);
            int lastLength = length - index;
            Integer[] lastPart = new Integer[lastLength];
            System.arraycopy(strList, index, lastPart, 0, lastLength);
            System.arraycopy(firstPart, 0, strList, 0, firstPart.length);
            strList[index] = item;
            System.arraycopy(lastPart, 0, strList, index + 1, lastLength);
        } else {
            Integer[] lastPart = new Integer[length];
            System.arraycopy(strList, 0, lastPart, 0, length);
            strList[0] = item;
            System.arraycopy(lastPart, 0, strList, 1, length);
        }
        length++;
        return item;
    }

    // Установить элемент на определенную позицию, затерев существующий.
    // Выбросить исключение, если индекс больше
    // фактического количества элементов или выходит за пределы массива.
    @Override
    public Integer set(int index, Integer item) {
        validateEmptyArray();
        validateIndex(index);
        strList[index] = item;
        return item;
    }

    // Удаление элемента.  Вернуть удаленный элемент
    // или исключение, если подобный  элемент отсутствует в списке.
    @Override
    public Integer remove(Integer item) {
        validateEmptyArray();
        for (int i = 0; i < length; i++) {
            if (strList[i].equals(item)) {
                Integer tempStr = removeId(i);
                return item;
            }
        }
        throw new AbsentElementException("Element is absent");
    }

    // Удаление элемента по индексу.  Вернуть удаленный элемент
    // или исключение, если подобный  элемент отсутствует в списке.
    @Override
    public Integer removeId(int index) {
        validateEmptyArray();
        validateIndex(index);
        Integer item = strList[index];
        //  length--;
        // ***************** если удаляемый элемент на любой другой позиции, кроме 0 позиции ********
        if (index != 0) {
            Integer[] firstPart = new Integer[index];
            System.arraycopy(strList, 0, firstPart, 0, index);
            int lastLength = length - index - 1;
            Integer[] lastPart = new Integer[lastLength];
            System.arraycopy(strList, index + 1, lastPart, 0, lastLength);
            // strList = new Integer[length];
            System.arraycopy(firstPart, 0, strList, 0, firstPart.length);
            System.arraycopy(lastPart, 0, strList, index, lastLength);
        } else {
            // ***************** если удаляемый элемент на 0 позиции ********
            Integer[] lastPart = new Integer[length - 1];
            System.arraycopy(strList, 1, lastPart, 0, lastPart.length);
            // strList = new Integer[length];
            System.arraycopy(lastPart, 0, strList, 0, lastPart.length);
        }
        length--;
        return item;
    }

    // Проверка на существование элемента.  Вернуть true/false;
    @Override
    public boolean contains(Integer item) {
        validateEmptyArray();
        int element = (int) item;
        //  ---- возвращаем отсортированный массив. Методом - сортировка слиянием   -----
        int[] sortArr = new int[length];
        int[] strListInt = Arrays.stream(strList)
                .mapToInt(Integer::intValue)
                .limit(length)
                .toArray();
        System.arraycopy(strListInt, 0, sortArr, 0, length);
        mergeSort(sortArr);
        boolean isExist = binarySearch(sortArr, element);
        return isExist;
    }

    private boolean binarySearch(int[] sortArr, int element) {
        // ++++++++++   метод бинарного поиска +++++++
        int min = 0;
        int max = sortArr.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            // int sortArrMid = sortArr[middle].intValue();
            int sortArrMid = sortArr[middle];
            if (element == sortArrMid) {
                return true;
            }
            if (element < sortArrMid) {
                max = middle - 1;
            } else {
                min = middle + 1;
            }
        }
        return false;
    }

    // Поиск элемента.  Вернуть индекс элемента
    // или -1 в случае отсутствия.
    @Override
    public int indexOf(Integer item) {
        validateEmptyArray();
        int isExistIndex = -1;
        for (int i = 0; i < length; i++) {
            if (strList[i].equals(item)) {
                isExistIndex = i;
                break;
            }
        }
        return isExistIndex;
    }


    // Найти последний встречающийся элемент.  Вернуть индекс элемента
    // или -1 в случае отсутствия.
    @Override
    public int lastIndexOf(Integer item) {
        validateEmptyArray();
        int isExistIndex = -1;
        for (int i = length - 1; i >= 0; i--) {
            if (strList[i].equals(item)) {
                isExistIndex = i;
                break;
            }
        }
        return isExistIndex;
    }

    // Получить элемент по индексу.  Вернуть элемент или исключение,
    // если выходит за рамки фактического  количества элементов.
    @Override
    public Integer get(int index) {
        validateEmptyArray();
        validateIndex(index);
        Integer item = strList[index];
        return item;
    }


    // Сравнить текущий список с другим.  Вернуть true/false или исключение,
    // если передан null.
    @Override
    public boolean equals(IntegerList otherList) {
        validateEmptyOtherList(otherList);
        Integer[] otherStringArray = otherList.getAll();
        boolean isEquals = false;
        if (otherStringArray.length == length) {
            for (int i = 0; i < length; i++) {
                if (!otherStringArray[i].equals(strList[i])) {
                    isEquals = false;
                    break;
                }
                isEquals = true;
            }
        }
        return isEquals;
    }

    // Вернуть фактическое количество элементов.
    @Override
    public int size() {
        return length;
    }

    // Вернуть true,  если элементов в списке нет,
    // иначе false.
    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    // Удалить все элементы из списка.
    @Override
    public void clear() {
        strList = new Integer[15];
        length = 0;
    }

    // Создать новый массив  из строк в списке и вернуть его.
    @Override
    public Integer[] toArray() {
        Integer[] newArray = new Integer[length];
        System.arraycopy(strList, 0, newArray, 0, length);
        return newArray;
    }

    private void validateIndex(int index) {
        if (index < 0 || index > length) {
            throw new IndexOutOfArrayException("Out of range");
        }
    }

    private void validateEmptyArray() {
        if (length == 0) {
            throw new EmptyArrayException("The list is empty");
        }
    }

    private void validateEmptyOtherList(IntegerList otherList) {
        if (otherList == null) {
            throw new EmptyArrayException("The list is empty");
        }
    }

    public String toString(Integer[] tempList) {
        return Arrays.toString(tempList);
    }

    //=======================================================================
    //=======================================================================
    //=======================================================================
    //==============                СОРТИРОВКИ              ================
    //======================================================================
    //======================================================================
    // ***** метод сортировки 0-й ****************
    // ******     СОРТИРОВКА  СЛИЯНИЕМ    ***********************
    public void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    public static void merge(int[] arr, int[] left, int[] right) {
        int mainP = 0;
        int leftP = 0;
        int rightP = 0;
        while (leftP < left.length && rightP < right.length) {
            if (left[leftP] <= right[rightP]) {
                arr[mainP++] = left[leftP++];
            } else {
                arr[mainP++] = right[rightP++];
            }
        }
        while (leftP < left.length) {
            arr[mainP++] = left[leftP++];
        }
        while (rightP < right.length) {
            arr[mainP++] = right[rightP++];
        }
    }

    // ***** метод сортировки 1-й ******************* (в сгенерировнном массиве) ****
    // ******   СОРТИРОВКА ВСТАВКОЙ  ***********************
    public Integer[] sortArrayByInserting() {
        Integer[] sortedArray = new Integer[length];
        System.arraycopy(strList, 0, sortedArray, 0, length);
        for (int i = 1; i < sortedArray.length; i++) {
            int temp = sortedArray[i];
            int j = i;
            while (j > 0 && sortedArray[j - 1] >= temp) {
                sortedArray[j] = sortedArray[j - 1];
                j--;
            }
            sortedArray[j] = temp;
        }
        return sortedArray;
    }

    // ***** метод сортировки 2-й ******************* (в сгенерировнном массиве) ****
    // ******   Пузырьковая сортировка ************************************************
    public Integer[] bubbleSortingOfArray() {
        Integer[] sortedArray = new Integer[length];
        System.arraycopy(strList, 0, sortedArray, 0, length);
        for (int i = 0; i < sortedArray.length - 1; i++) {
            for (int j = 0; j < sortedArray.length - 1 - i; j++) {
                if (sortedArray[j] > sortedArray[j + 1]) {
                    swapElements(sortedArray, j, j + 1);
                }
            }
        }
        return sortedArray;
    }

    // -----------
    private void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    // ***** метод сортировки 3-й ******************* (в сгенерировнном массиве) ****
    // ******    сортировка выбором    ************************************************
    public Integer[] sortArrayByChoicing() {
        Integer[] sortedArray = new Integer[length];
        System.arraycopy(strList, 0, sortedArray, 0, length);
        for (int i = 0; i < sortedArray.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < sortedArray.length; j++) {
                if (sortedArray[j] < sortedArray[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(sortedArray, i, minElementIndex);
        }
        return sortedArray;
    }
}
