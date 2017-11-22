/**
 * @author joey
 * limited functionality dynamic array
 */
public class Vector<T> {     
    private T[] __arr = (T[])(new Object[16]);
    private int __arr_size = 0;
    
    private void resizeArray() {
        T[] tmp = (T[])(new Object[__arr.length * 2]);
        
        for(int i = 0; i < __arr.length; i++) {
            tmp[i] = __arr[i];
        }
        __arr = tmp;
    }
    
    // in true C++ STL fashion
    public void push_back(T element) {
        if(__arr_size == __arr.length)
            resizeArray();
        __arr[__arr_size] = element;
        __arr_size++;
    }
    
    public boolean contains(T element) {
        for(int i = 0; i < __arr_size; i++) {
            if(__arr[i].equals(element))
                return true;
        }
        return false;
    }
    
    // constructor does nothing
    public Vector() { }
    
}
