/**
 *
 * @author joe
 */
public class LinkedStack<T> implements Stack<T> {
    
    SinglyLinkedList<T> list = new SinglyLinkedList<>();
    
    @Override
    public int size() {
        return list.getCount();
    }

    @Override
    public boolean isEmpty() {
        return (list.getCount() == 0);
    }

    @Override
    public void push(T e) {
        list.addHead(e);
    }

    @Override
    public T top() {
        return list.peekHead();
    }

    @Override
    public T pop() {
        return list.removeHead();
    }

    @Override
    public void clear() {
        
    }
    
}
