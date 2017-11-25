/**
 *
 * @author joey
 */
public class GpaCompDescending implements Comparator<Student> {
    GpaComp gpac = new GpaComp();
    
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        return -1 * gpac.compare(t1, t2);
    }
    
}

