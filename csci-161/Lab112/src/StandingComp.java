/**
 * @author joey
 */
public class StandingComp implements Comparator<Student> {
    private int getIntFromString(String standing) {
        if(standing.equals("senior")) {
            return 0;
        } else if(standing.equals("junior")) {
            return 1;
        } else if(standing.equals("sophomore")) {
            return 2;
        } else if(standing.equals("freshman")) {
            return 3;
        }
        
        return -1;
    }
    
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        int t1_num = getIntFromString(t1.getStanding());
        int t2_num = getIntFromString(t2.getStanding());
        
        if(t1_num < t2_num)
            return -1;
        if(t2_num < t1_num)
            return 1;
        return 0;
    }
}
