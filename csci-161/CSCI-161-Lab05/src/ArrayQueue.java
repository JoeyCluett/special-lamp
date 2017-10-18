/**
 * @author joe
 * 
 */
public class ArrayQueue<E> implements Queue<E> {

    private E[] data;
    private int f = 0;
    private int sz;
    private static final int CAPACITY = 1000;
    
    /**
     * constructor w/ a default capacity
     */
    public ArrayQueue() { this(CAPACITY); }
    
    /**
     * constructor with a specified capacity
     * @param capacity max number of items in the ArrayQueue
     */
    public ArrayQueue(int capacity) {
        this.data = (E[])new Object[capacity];
    }
    
    @Override
    public int size() {
        return sz;
    }

    @Override
    public boolean isEmpty() {
        return (sz == 0);
    }

    @Override
    public void enqueue(E e) {
        if(sz == data.length)
            throw new IllegalStateException("Queue is full");
        int avail = (f + sz) % data.length;
        data[avail] = e;
        sz++;
    }

    @Override
    public E first() {
        if(isEmpty())
            return null;
        return data[f];
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            return null;
        E answer = data[f];
        data[f] = null;
        f = (f + 1) % data.length;
        sz--;
        return answer;
    }    
}
