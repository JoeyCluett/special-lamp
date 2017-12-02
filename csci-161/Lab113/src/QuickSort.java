/**
 *
 * @author joey
 */
public class QuickSort {
    
    /**
     * @param d array to sort
     * Quick sort algorithm, shown to be faster than other algorithms
     * tried in the past
     */
    public static void Sort(int[] d) {
        Sort(d, 0, d.length-1);
    }
    
    /**
     * @param arr
     * @param low
     * @param high 
     * QuickSort algorithm optimized for integers
     * http://www.geeksforgeeks.org/quick-sort/
     */
    private static void Sort(int[] arr, int low, int high) {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is 
              now at right place */
            int pi = partition(arr, low, high);
 
            // Recursively sort elements before
            // partition and after partition
            Sort(arr, low, pi-1);
            Sort(arr, pi+1, high);
        }
    }
    
    // rotate around high point
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; 
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot)
            {
                i++;
 
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
 
        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
 
        return i+1;
    }
    
}
