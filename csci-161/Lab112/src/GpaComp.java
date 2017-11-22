/**
 *
 * @author joey
 */
public class GpaComp implements Comparator<Student> {
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        if(t1.getGpa() > t2.getGpa())
            return 1;
        if(t2.getGpa() > t1.getGpa())
            return -1;
        return 0;
    }
}
