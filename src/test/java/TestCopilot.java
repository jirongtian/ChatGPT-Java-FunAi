import java.util.Arrays;

public class TestCopilot {

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 5, 4};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    //快速排序 加一下注释详细说明
    public static void quickSort(int[] arr, int left, int right) {
        // 递归结束条件
        if (left >= right) {
            return;
        }

        // 1. 选择基准值
        int pivot = partition(arr, left, right);

        // 2. 对基准值左边的元素进行快速排序
        quickSort(arr, left, pivot - 1);

        // 3. 对基准值右边的元素进行快速排序
        quickSort(arr, pivot, right);
    }

    // 选择基准值
    public static int partition(int[] arr, int left, int right) {
        // 选择第一个元素作为基准值
        int pivot = arr[left];
        int i = left;
        int j = right;

        // 从两边向中间扫描
        while (i <= j) {
            // 从左边找到第一个大于等于基准值的元素
            while (arr[i] < pivot) {
                i++;
            }
            // 从右边找到第一个小于等于基准值的元素
            while (arr[j] > pivot) {
                j--;
            }
            // 交换元素
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

    // 交换元素
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
