/**
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
    
    private boolean arrayPush(E[] arr, int top, E element) {
        if(top == arr.length)
            return false;
        
        arr[top] = element;
        top++;
        return true;
    }
    
    private E arrayPop(E[] arr, int top) {
        if(top == 0) {
            return null;
        }
        
        top--;
        return arr[top];
    }
    
    @Override
    public boolean redPush(E element) {
        if(arrayPush(redStack, redStackTop, element)) {
            redStackTop++;
            return true;
        }
        return false;
    }

    @Override
    public E redPop() {
        return arrayPop(redStack, redStackTop);
    }

    @Override
    public int redSize() {
        return redStackTop;
    }
    
    @Override
    public boolean bluePush(E element) {
        if(arrayPush(blueStack, blueStackTop, element)) {
            blueStackTop++;
            return true;
        }
        return false;
    }

    @Override
    public E bluePop() {
        return arrayPop(blueStack, blueStackTop);
    }
    
    @Override
    public int blueSize() {
        return blueStackTop;
    }
}
