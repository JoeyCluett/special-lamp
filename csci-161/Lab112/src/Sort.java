/**
 * @author joe
 */
public class Sort {
    // used with Bubble, Insertion, and Selection sort methods
    private static final int bubbleSortAmt = 100000;
    
    
    
    // bubble sort is not required to run entire array
    public static void simpleBubbleSort(Object[] o, Comparator comp) { 
        if(!(o instanceof Student[]))
            throw new IllegalArgumentException("simpleBubbleSort: Array not of type Student");
        
        Student[] s = (Student[])o;
        
        for(int i = 0; i < bubbleSortAmt; i++) {
            for(int j = 0; j < bubbleSortAmt-1; j++) {
                if(comp.compare(s[j], s[j+1]) < 0) {
                    // swap by using temp variable
                    Student s_tmp = s[j];
                    s[j] = s[j+1];
                    s[j+1] = s_tmp;
                }
            }
        }
    }
}
