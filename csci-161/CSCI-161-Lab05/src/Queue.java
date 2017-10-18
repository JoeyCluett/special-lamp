/**
 *
 * @author joe
 */
public interface Queue<E> {
    /**
     * @return number of elements currently in the queue 
     */
    public int size();
    
    /**
     * @return tell if there are zero elements in the queue
     */
    boolean isEmpty();
    
    /**
     * @param e element to place in the queue 
     */
    void enqueue(E e);
    
    /**
     * @return return first element w/o removing it from the queue
     */
    E first();
    
    /**
     * @return remove element and return it
     */
    E dequeue();
}
