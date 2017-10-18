/**
 * @author joe
 */
public interface DoubleStack<E> {
    
    /**
     * @param element element to add to blue stack
     * @return bool whether the operation was successful
     */
    boolean redPush(E element);
    
    /**
     * @param element E stored here on success
     * @return whether the operation was successful
     */
    boolean redPop(E element);
    
    /**
     * @return current number of elements in red stack
     */
    int redSize();
    
    /**
     * @param element element to add to blue stack
     * @return bool whether the operation was successful
     */
    boolean bluePush(E element);
   
    /**
     * @param element E stored here on success
     * @return whether the operation was successful
     */
    boolean bluePop(E element);

    /**
     * @return current number of elements in blue stack
     */
    int blueSize();
}






