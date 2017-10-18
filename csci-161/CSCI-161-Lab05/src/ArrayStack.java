/**
 *
 * @author joe
 */
public class ArrayStack<T> implements Stack<T> {

    private static final int CAPACITY = 1000;
    private int count = 0;
    private T[] data = null;
    
    public ArrayStack() {
        this(CAPACITY);
    }
    
    public ArrayStack(int cap) {
        data = (T[])new Object[cap];
    }
    
    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    @Override
    public void push(T e) {
        if(count == data.length)
            throw new IllegalStateException("Not enough space in stack for new elements\nCapacity: " + data.length);
        data[count++] = e;
    }

    @Override
    public T top() {
        if(count > 0)
            return data[count-1];
        return null;
    }

    @Override
    public T pop() {
        if(count > 0)
            return data[--count];
        return null;
    }
    
    @Override
    public void clear() {
        
    }
    
}
