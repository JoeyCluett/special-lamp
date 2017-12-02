/**
 *
 * @author joey
 */
public class StringList {
    public class StringNode {
        public StringNode next = null;
        public String str;
        
        public StringNode(String str, StringNode next) {
            this.next = next;
            this.str = str;
        }
    }
    
    StringNode head = null;
    private int size = 0;
    
    public void addString(String str) {
        head = new StringNode(str, head);
        size++;
    }
    
    public String getString() {
        String ret = head.str;
        head = head.next;
        size--;
        return ret;
    }
    
    public int getSize() {
        return size;
    }
    
    public String at(int index) {
        StringNode tmp = head;
        for(int i = 0; i < index; i++)
            tmp = tmp.next;
        return tmp.str;
    }
    
}
