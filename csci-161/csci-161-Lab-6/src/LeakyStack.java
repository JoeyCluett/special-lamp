/**
 *
 * @author joey
 */
public interface LeakyStack<E> {
    /**
     * @param element to push onto the stack
     * @return whether the operation was successful
     */
    public boolean push(E element);
    
    /**
     * @return the popped element or null if failed 
     */
    public E pop();
    
    /**
     * @return number of elements currently on stack
     */
    public int size();
}
