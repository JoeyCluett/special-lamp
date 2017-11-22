import java.util.Random; // lots of pseudo-random numbers to generate

/**
 * @author joe
 */
public class Client { // ... what if EVERYTHING was an object?
    public static Random r             = new Random();
    public static Student students[]   = new Student[1000000];
    public static boolean[] intFlag    = new boolean[10000000]; // flags for the integers to ensure unique ID
    public static String[] sList       = new String[10];
    public static float[] fList        = new float[20]; // split by 5% chunks
    
    public static void main(String[] args) {
        try {
            //students = new Student[1000000];
            for(int i = 0; i < 1000000; i++)
                students[i] = new Student();

            fillStandingList();
            fillGpaList();

            System.out.println("Generating random student info");
            fillStudentArray(1000000);
            
            Sort.simpleBubbleSort(students, new IdCompDescending());
            
        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    public static void printFirstElements() {
        for(int i = 0; i < 10; i++)
            System.out.println(students[i]);
        System.out.println("\n\n");
    }
    
    // num is there because we wont always need to redo the entire array, just the sorted part
    // this will help cut down execution time
    public static void fillStudentArray(int num) {
        for(int i = 0; i < num; i++) {
            students[i].setFname(getRandomString());
            students[i].setLname(getRandomString());
            students[i].setId(getUniqueRandomInt());
            students[i].setStanding(getDistributedStanding());
            students[i].setGpa(getDistributedGpa());
        }
    }

    public static float getDistributedGpa() {
        int tmp = Math.abs(r.nextInt()) % 20; // unsigned ints ftw
        float fTmp = fList[tmp] + (r.nextFloat() - 0.01f);
        if(fTmp > 4.0f)
            return 4.0f;
        return fTmp;
    }
    
    public static String getDistributedStanding() {
        int tmp = Math.abs(r.nextInt()) % 10;
        return sList[tmp];
    }
        
    public void resetIdFlags() {
        for(int i = 0; i < 10000000; i++)
            intFlag[i] = false;
    }
    
    public static int getUniqueRandomInt() {
        int tmp = 0;
        do {
            tmp = Math.abs(r.nextInt()) % 10000000;
        } while(intFlag[tmp]); // ..lack of static local variables in Java
        intFlag[tmp] = true;
        return tmp;
    }
    
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
