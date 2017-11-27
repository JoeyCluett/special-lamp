/**
 * @author joe
 */
public class IdComp implements Comparator<Student> {

    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        if(t1.getId() < t2.getId())
            return -1;
        if(t1.getId() > t2.getId())
            return 1;
        return 0;
    }
    
}
