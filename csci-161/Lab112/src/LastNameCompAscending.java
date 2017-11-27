/**
 * @author joey
 */
public class LastNameCompAscending implements Comparator<Student> {
    private LastNameComp lnc = new LastNameComp(); // <-- a specialization of Comparator
                                                   // whereas this Class is Generic
    
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) { // calling function passes Object type
        return -1 * lnc.compare(t1, t2);         // which is cast to any other type
    }
    
}
