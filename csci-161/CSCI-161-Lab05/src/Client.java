/**
 * @author joe
 */
public class Client {
    
    public static final int MAX_RUNS = 1;
    
    // find average time of multiple runs together
    public static void main(String[] args) {
        for(int i = 100; i <= 100000; i *= 10) {
            double arrayTotal = 0.0;
            double linkTotal  = 0.0;
        
            for(int j = 0; j < MAX_RUNS; j++) {
                arrayTotal += arrayBasedTest(i);
                linkTotal  += linkBasedTest(i);
            }
            
            arrayTotal /= (double)MAX_RUNS;
            linkTotal  /= (double)MAX_RUNS;
            
            System.out.println("" + i + " objects");
            System.out.printf("    Array based: %10f seconds\n", arrayTotal/1000000000.0);
            System.out.printf("    Link based:  %10f seconds\n", linkTotal /1000000000.0);
        }
    }
    
    public static double arrayBasedTest(int ELEMENTS) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(ELEMENTS);
        ArrayStack<Integer> stack = new ArrayStack<>(ELEMENTS);
        
        long start_time = System.nanoTime();
        
        // place elements on the queue
        for(int i = 0; i < ELEMENTS; i++)
            queue.enqueue(i);
        
        // take elements from queue and put them on the stack
        while(queue.size() != 0)
            stack.push(queue.dequeue());
        
        // take elements from stack and put them back on the queue
        while(stack.size() != 0)
            queue.enqueue(stack.pop());
        
        long end_time = System.nanoTime();
        double delta = (double)(end_time - start_time);
        
        return delta;
    }
    
    public static double linkBasedTest(int ELEMENTS) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        LinkedStack<Integer> stack = new LinkedStack<>();
        
        long start_time = System.nanoTime();
        
        // place elements on the queue
        for(int i = 0; i < ELEMENTS; i++)
            queue.enqueue(i);
        
        // take elements from queue and put them on the stack
        while(queue.size() != 0)
            stack.push(queue.dequeue());
        
        // take elements from stack and put them back on the queue
        while(stack.size() != 0)
            queue.enqueue(stack.pop());
        
        long end_time = System.nanoTime();
        double delta = (double)(end_time - start_time);
    
        return delta;
    }
}
