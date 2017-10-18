/**
 * @author joe
 * @param <T> 
 */
public class SinglyLinkedList<T> {
   
    /**
     * @param <T> 
     */
    private class ListNode<T> {
        public ListNode<T> next;
        public T data;
        
        public ListNode(ListNode<T> next, T data) {
            this.next = next;
            this.data = data;
        }
    }
    
    private int count = 0;
    private ListNode<T> head;
    
    /**
     * Constructor
     */
    public SinglyLinkedList() {
        head = null;
    }
    
    /**
     * @return 
     */
    public int getCount() {
        return count;
    }
    
    /**
     * @return first element w/o removing it from the list
     */
    public T peekHead() {
        if(count == 0)
            return null;
        
        return head.data;
    }
    
    /**
     * @return return the last element w/o removing it from the list
     */
    public T peekTail() {
        if(count == 0)
            return null;
        
        ListNode<T> tmp = head;
        while(tmp.next != null)
            tmp = tmp.next;
        
        return tmp.data;
    }
    
    /**
     * @param data data to add to beginning of list
     */
    public void addHead(T data) {
        head = new ListNode(head, data);
        count++;
    }
    
    /**
     * @param data data to add to end of list
     */
    public void addTail(T data) {
        if(count == 0) {
            head = new ListNode(head, data);
            count++;
            return;
        } else {

            ListNode<T> tmp = head;

            while(tmp.next != null)
                tmp = tmp.next;

            tmp.next = new ListNode<>(null, data);
            count++;
        }
    }
    
    /**
     * @return removed data 
     */
    public T removeHead() {
        if(count == 0)
            return null;
        
        count--;
        
        T data = head.data;
        ListNode tmp = head.next;
        head = null; // assist gc
        head = tmp;
        return data;
    }
    
    /**
     * @return removed data
     */
    public T removeTail() {
        if(count == 0)
            return null;
        
        if(count == 1) {
            T data = head.data;
            head = null;
            count--;
            return data;
        }
        
        // iterate to end of list
        ListNode<T> tmp = head;
        while(tmp.next != null) {
            tmp = tmp.next;
        }
        T data = tmp.data;
        
        // need to apply null to end of list again
        ListNode<T> nullNode = head;
        while(nullNode.next != tmp) {
            nullNode = nullNode.next;
        }
        
        nullNode.next = null;
        
        count--;
        return data;
    }
}
