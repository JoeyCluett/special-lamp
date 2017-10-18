/**
 * @author joe
 */
public interface Stack<T> {
    public int size();
    public boolean isEmpty();
    public void push(T e);
    T top();
    T pop();
    public void clear();
}
