/**
 * @author joey
 * lexicographically text first names of students
 */
public class FirstNameComp implements Comparator<Student> {
    /**
     * @param t1 Student 1
     * @param t2 Student 2
     * @return comparison
     */
    @Override
    public int compare(Student t1, Student t2) {
        return t1.getFname().compareTo(t2.getFname());
    }
}
