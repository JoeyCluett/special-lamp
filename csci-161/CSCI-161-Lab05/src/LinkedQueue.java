/**
 *
 * @author joe
 */
public class LinkedQueue<E> implements Queue<E> {

    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public LinkedQueue() { ; }
    
    @Override
    public int size() {
        return list.getCount();
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public void enqueue(E e) {
        list.addTail(e);
    }

    @Override
    public E first() {
        return list.peekHead();
    }

    @Override
    public E dequeue() {
        return list.removeHead();
    }    
}
