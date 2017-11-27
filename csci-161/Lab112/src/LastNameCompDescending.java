/**
 * @author joey
 */
public class LastNameCompDescending implements Comparator<Student> {
    private LastNameComp lnc = new LastNameComp();
    
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        return 1 * lnc.compare(t1, t2);
    }
    
}
