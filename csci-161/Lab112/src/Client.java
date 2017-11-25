import java.util.Random; // lots of pseudo-random numbers to generate

/**
 * @author joe
 */
public class Client { // ... what if EVERYTHING was an object?
    public static Random r           = new Random();
    public static Student students[] = new Student[1000000];
    public static boolean[] intFlag  = new boolean[10000000]; // flags for the integers to ensure unique ID
    public static String[] sList     = new String[10];
    public static float[] fList      = new float[20]; // split by 5% chunks
    
    // times for individual tests
    public static long merge_sort_ascending_id       = -1; // benchmark 1
    public static long quick_sort_ascending_gpa      = -1; // benchmark 2
    public static long bubble_sort_descending_id     = -1; // benchmark 3
    public static long insertion_sort_descending_gpa = -1; // benchmark 4
    public static long selection_sort_standing       = -1; // benchmark 5
    public static long radix_sort_complex            = -1; // benchmark 6
    
    public static void main(String[] args) {
        try {
            for(int i = 0; i < 1000000; i++)
                students[i] = new Student();

            fillStandingList();
            fillGpaList();

            System.out.println("Generating random student info");
            fillStudentArray(1000000);
            
            Student[] working_set;
            
            // BENCHMARK 1
            working_set = copyElementsTo(students, 1000000);
            System.out.println("\nTesting merge sort w/ ascending ID...");
            merge_sort_ascending_id = System.nanoTime();
            Sort.mergeSort(working_set, new IdCompAscending());
            merge_sort_ascending_id = System.nanoTime() - merge_sort_ascending_id;
            System.out.println("    Delta time: " + (merge_sort_ascending_id / 1000000) + " ms");
            working_set = null; // free for garbage collector
            
            // BENCHMARK 2
            working_set = copyElementsTo(students, 1000000);
            System.out.println("\nTesting quick sort w/ ascending GPA...");
            quick_sort_ascending_gpa = System.nanoTime(); // start time
            Sort.quickSort(working_set, new GpaCompAscending());
            quick_sort_ascending_gpa = System.nanoTime() - quick_sort_ascending_gpa; // delta time
            System.out.println("    Delta time: " + (quick_sort_ascending_gpa / 1000000) + " ms"); // nano seconds to milliseconds
            working_set = null;
            
            // BENCHMARK 3
            working_set = copyElementsTo(students, 100000);
            System.out.println("\nTesting bubble sort w/ descending ID...");
            bubble_sort_descending_id = System.nanoTime();
            Sort.simpleBubbleSort(working_set, new IdCompDescending());
            bubble_sort_descending_id = System.nanoTime() - bubble_sort_descending_id;
            System.out.println("    Delta time: " + (bubble_sort_descending_id / 1000000) + " ms");
            working_set = null;
            
            // BENCHMARK 4
            working_set = copyElementsTo(students, 100000);
            System.out.println("\nTesting insertion sort w/ descending GPA...");
            insertion_sort_descending_gpa = System.nanoTime();
            Sort.insertionSort(working_set, new GpaCompDescending());
            insertion_sort_descending_gpa = System.nanoTime() - insertion_sort_descending_gpa;
            System.out.println("    Delta time: " + (insertion_sort_descending_gpa / 1000000) + " ms");
            working_set = null;
            
            // BENCHMARK 5
            working_set = copyElementsTo(students, 100000);
            System.out.println("\nTesting selection sort w/ standing...");
            selection_sort_standing = System.nanoTime();
            Sort.selectionSort(working_set, new StandingComp());
            selection_sort_standing = System.nanoTime() - selection_sort_standing;
            System.out.println("    Delta time: " + (selection_sort_standing / 1000000) + " ms");
            working_set = null;
            
            // BENCHMARK 6
            working_set = copyElementsTo(students, 1000000);
            System.out.println("\nTesting radix sort with 5 keys...");
            radix_sort_complex = System.nanoTime();
            Sort.radixSort(working_set, 
                    new StandingComp(),
                    new GpaCompDescending(),
                    new LastNameCompAscending(),
                    new FirstNameComp());
            radix_sort_complex = System.nanoTime() - radix_sort_complex;
            System.out.println("    Delta time: " + (radix_sort_complex / 1000000) + " ms");
            working_set = null;
            
            //Sort.mergeSort(students, new IdCompAscending());
            //Sort.quickSort(students, new GpaCompAscending());
            //Sort.simpleBubbleSort(students, new IdCompDescending());
            //Sort.insertionSort(students, new GpaCompDescending());
            //Sort.selectionSort(students, new StandingComp());
            
            printResults();
            
        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * @param s array to copy data from
     * @param num number of elements to copy from source array (s)
     * @return deep-copied array
     */
    public static Student[] copyElementsTo(Student[] s, int num) {
        Student[] tmp = new Student[num];
        for(int i = 0; i < num; i++) // manual array copy
            tmp[i] = s[i].getCopy(); // deep copy of each Student to ensure
        return tmp;                  // the same working set for each algorithm
    }
    
    /**
     * print results of benchmarks
     */
    public static void printResults() {
        String vertical_seperator = "--------------------------------------------";
        
        System.out.println(vertical_seperator);
        System.out.println("|    Sort        |   N       |     Time    |");
        System.out.println(vertical_seperator); 
        System.out.printf("| Merge          | 1,000,000 | %8d ms |\n", merge_sort_ascending_id/1000000);
        System.out.println(vertical_seperator);
        System.out.printf("| Quick Sort     | 1,000,000 | %8d ms |\n", quick_sort_ascending_gpa/1000000);
        System.out.println(vertical_seperator);
        System.out.printf("| Bubble Sort    | 100,000   | %8d ms |\n", bubble_sort_descending_id/1000000);
        System.out.println(vertical_seperator);
        System.out.printf("| Insertion Sort | 100,000   | %8d ms |\n", insertion_sort_descending_gpa/1000000);
        System.out.println(vertical_seperator);
        System.out.printf("| Selection Sort | 100,000   | %8d ms |\n", selection_sort_standing/1000000);
        System.out.println(vertical_seperator);
        System.out.printf("| Radix Sort     | 1,000,000 | %8d ms |\n", radix_sort_complex/1000000);
        System.out.println(vertical_seperator);
    }
    
    public static void printFirstElements() {
        for(int i = 0; i < 10; i++)
            System.out.println(students[i]);
        System.out.println("\n\n");
    }
    
    /**
     * @param num number of entries to refill 
     * 
     */
    public static void fillStudentArray(int num) {
        for(int i = 0; i < num; i++) {
            students[i].setFname(getRandomString());
            students[i].setLname(getRandomString());
            students[i].setId(getUniqueRandomInt());
            students[i].setStanding(getDistributedStanding());
            students[i].setGpa(getDistributedGpa());
        }
    }
    
    /**
     * @return properly distributed GPA
     */
    public static float getDistributedGpa() {
        int tmp = Math.abs(r.nextInt()) % 20; // unsigned ints ftw
        float fTmp = fList[tmp] + (r.nextFloat() - 0.01f);
        if(fTmp > 4.0f)
            return 4.0f;
        
        if(fTmp < 0.0f)
            return fTmp+0.01f;
        
        return fTmp;
    }
    
    /**
     * @return String representation of standing
     * access standing LUT with randomly generated unsigned integer
     */
    public static String getDistributedStanding() {
        int tmp = Math.abs(r.nextInt()) % 10;
        return sList[tmp];
    }
        
    /**
     * reset ID LUT to false
     */
    public void resetIdFlags() {
        for(int i = 0; i < 10000000; i++)
            intFlag[i] = false;
    }
    
    /**
     * 
     * @return integer that is unique as of most recent LUT reset
     */
    public static int getUniqueRandomInt() {
        int tmp = 0;
        do {
            tmp = Math.abs(r.nextInt()) % 10000000;
        } while(intFlag[tmp]); // ..lack of static local variables in Java
        intFlag[tmp] = true;
        return tmp;
    }
    
    /**
     * 
     * @return string with proper formatting 
     */
    public static String getRandomString() {
        String tmp = "";
        
        // random length 10 .. 15
        int rLength = 10 + (Math.abs(r.nextInt()) % 5);
    
        // first character is UPPERCASE
        tmp += (char)(65 + (Math.abs(r.nextInt()) % 26));
        
        // rest are lowercase
        for(int i = 0; i < rLength-1; i++) {
            tmp += (char)(97 + (Math.abs(r.nextInt()) % 26));
        }
        
        return tmp;
    }
    
    /**
     * fill standing LUT with properly distributed values
     */
    public static void fillStandingList() {
        // use as few memory-allocated references as possible
        
        sList[0] = "freshman";  // 40% freshman
        sList[1] = sList[0];
        sList[2] = sList[0];
        sList[3] = sList[0];
        
        sList[4] = "sophomore"; // 30% sophomores
        sList[5] = sList[4];
        sList[6] = sList[4];
        
        sList[7] = "junior";    // 20% juniors
        sList[8] = sList[7];
        
        sList[9] = "senior";    // 10% seniors
    }
    
    /**
     * fill GPA LUT with properly distributed values
     */
    public static void fillGpaList() {
        fList[0] = 0.0f;
        
        for(int i = 1; i < 5; i++)
            fList[i] = 1.0f;
        
        for(int i = 5; i < 15; i++)
            fList[i] = 2.0f;
        
        for(int i = 15; i < 19; i++)
            fList[i] = 3.0f;
        
        fList[19] = 4.0f;
        
    }
    
}
