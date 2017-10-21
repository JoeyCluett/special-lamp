/**
 *
 * @author joey
 * 
 * implemented as a queue with no end
 */
public class ArrayLeakyStack<E> implements LeakyStack<E> {
    // storage for elements
    E[] element_array;
    
    // updated with every insertion/deletion
    int num_elements = 0;
    
    public ArrayLeakyStack() {
        this(256); // default size
    }
    
    public ArrayLeakyStack(int capacity) {
        element_array = (E[])(new Object[capacity]);
    }
    
    private void shiftArray() {
        int s = element_array.length -1;
        for(int i = 0; i < s; i++) {
            element_array[i] = element_array[i+1];
        }
    }
    
    @Override
    public boolean push(E element) {
        if(num_elements == element_array.length) {
            shiftArray();
            num_elements--;
        }
            
        element_array[num_elements] = element;
        num_elements++;
        
        return true;
    }

    @Override
    public E pop() {
        if(num_elements == 0)
            return null;
        
        num_elements--;
        return element_array[num_elements];
    }

    @Override
    public int size() {
        return num_elements;
    }
    
    // Object method override
    @Override
    public String toString() {
        if(num_elements == 0)
            return "ArrayLeakyStack[::empty::]";
        String ret = "ArrayLeakyStack[ ";
        
        for(int i = 0; i < num_elements; i++) {
            ret += element_array[i].toString();
            ret += " ";
        }
        
        return ret + "]";
    }
    
}
