/**
 * @author joey
 * lexicographically test last names of students, ascending order
 */
public class LastNameComp implements Comparator<Student> {
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        return t1.getLname().compareTo(t2.getLname());
    }
}
