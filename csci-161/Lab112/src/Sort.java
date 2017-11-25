import java.util.Arrays;

/**
 * @author joe
 */
public class Sort {
    // used with Bubble, Insertion, and Selection sort methods
    private static final int bubbleSortAmt = 100000; // lower than 100000 for testing purposes
    
    public static void radixSort(Object[] o, Comparator c1, Comparator c2) {
        quickSort(o, c2);
        quickSort(o, c1);
    }
    
    public static void radixSort(Object[] o, Comparator c1, Comparator c2, Comparator c3) {
        radixSort(o, c2, c3);
        quickSort(o, c1);
    }
    
    public static void radixSort(Object[] o, Comparator c1, Comparator c2, Comparator c3, Comparator c4) {
        radixSort(o, c2, c3, c4);
        quickSort(o, c1);
    }
    
    private static void quickSortInPlace(Object[] o, Comparator comp, int a, int b) {
        if(a >= b)
            return;
        
        int left = a;
        int right = b-1;
        Object pivot = o[b];
        Object tmp;
        
        while(left <= right) {
            while(left <= right && comp.compare(o[left], pivot) < 0)
                left++;
            while(left <= right && comp.compare(o[right], pivot) > 0)
                right--;
            
            if(left <= right) {
                tmp = o[left];
                o[left] = o[right];
                o[right] = tmp;
                
                left++;
                right--;   
            }
        }
        
        tmp = o[left];
        o[left] = o[b];
        o[b] = tmp;
        
        // recursive function calls
        quickSortInPlace(o, comp, a, left-1);
        quickSortInPlace(o, comp, left+1, b);
    }
    
    public static void quickSort(Object[] o, Comparator comp) {
        quickSortInPlace(o, comp, 0, o.length-1);
    }
    
    // for very large arrays of data, the working stack will tend to grow pretty large
    public static void mergeSort(Object[] o, Comparator comp) {
        int n = o.length;
        if(n < 2)
            return;
        
        int mid = n/2;
        
        Object[] S1 = Arrays.copyOfRange(o, 0, mid);
        Object[] S2 = Arrays.copyOfRange(o, mid, n);
        
        mergeSort(S1, comp);
        mergeSort(S2, comp);
        
        merge(S1, S2, o, comp);
    }
    
    private static void merge(Object[] S1, Object[] S2, Object[] S, Comparator comp) {
        int i = 0, j = 0;
        while(i+j < S.length) {
            if(j == S2.length || (i < S1.length && comp.compare(S1[i], S2[j]) < 0))
                S[i+j] = S1[i++];
            else
                S[i+j] = S2[j++];
        }
    }
    
    public static void selectionSort(Object[] o, Comparator comp) {
        for(int i = 0; i < bubbleSortAmt-1; i++) {
            
            int j = i;
            for(int k = i+1; k < bubbleSortAmt; k++) {
                if(comp.compare(o[k], o[j]) < 0)
                    j = k;
            }
            
            // swap object references
            Object tmp = o[j];
            o[j] = o[i];
            o[i] = tmp;
        }
    }
    
    public static void insertionSort(Object[] o, Comparator comp) {
        Object key;
        int j;
        for(int i = 0; i < bubbleSortAmt; i++) {
            key = o[i];
            j = i-1;
            
            while(j >= 0 && comp.compare(o[j], key) < 0) {
                o[j+1] = o[j];
                j--;
            }
            
            o[j+1] = key;
        }
    }
    
    // bubble sort is not required to run entire array
    public static void simpleBubbleSort(Object[] o, Comparator comp) { 
        
        Object[] s = o; // had to make a late-stage correction. my apologies
        
        for(int i = 0; i < bubbleSortAmt; i++) {
            for(int j = 0; j < bubbleSortAmt-1; j++) {
                if(comp.compare(s[j], s[j+1]) > 0) {
                    // swap by using temp variable
                    Object s_tmp = s[j];
                    s[j] = s[j+1];
                    s[j+1] = s_tmp;
                }
            }
        }
    }
}
