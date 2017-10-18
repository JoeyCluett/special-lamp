/**
 *
 * @author joe
 */
public class ArrayDoubleStack<E> implements DoubleStack<E> {
    // top is the number of elements currently in the stack
    private E[] redStack;  int redStackTop = 0;
    private E[] blueStack; int blueStackTop = 0;
    
    /**
     * default constructor, instantiates both stacks with capacity of 1000 elements
     */
    public ArrayDoubleStack() {
        this(1000, 1000);
    }
    
    public ArrayDoubleStack(int redCapacity, int blueCapacity) {
        redStack  = (E[])new Object[redCapacity];
        blueStack = (E[])new Object[blueCapacity];
    }
    
    private boolean arrayPush(E[] arr, Integer top, E element) {
        if(top == arr.length)
            return false;
        
        arr[top] = element;
        top++;
        return true;
    }
    
    private boolean arrayPop(E[] arr, Integer top, E element) {
        if(top == 0) {
            element = null;
            return false;
        }
        
        top--;
        element = arr[top];
        return true;
    }
    
    @Override
    public boolean redPush(E element) {
        return arrayPush(redStack, redStackTop, element);
    }

    @Override
    public boolean redPop(E element) {
        return arrayPop(redStack, redStackTop, element);
    }

    @Override
    public int redSize() {
        return redStackTop;
    }
    
    @Override
    public boolean bluePush(E element) {
        return arrayPush(blueStack, blueStackTop, element);
    }

    @Override
    public boolean bluePop(E element) {
        return arrayPop(blueStack, blueStackTop, element);
    }
    
    @Override
    public int blueSize() {
        return blueStackTop;
    }
}
