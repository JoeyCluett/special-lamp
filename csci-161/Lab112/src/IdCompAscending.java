/**
 * @author joey
 */
public class IdCompAscending implements Comparator<Student> {
    private IdComp ic = new IdComp();
    
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        return ic.compare(t1, t2);
    }
    
}
