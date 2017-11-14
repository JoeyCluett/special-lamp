import java.util.Random; // for generating random numbers

/**
 *
 * @author joey
 */
public class Client {
    public static final int N = 8800;
    
    public static void main(String[] args) {
        // reallly wishing for typedef stuff right here:
        TreeMap<Integer, Integer> unbalancedSearchTree  = new TreeMap<>();
        AVLTreeMap<Integer, Integer> balancedSearchTree = new AVLTreeMap<>(); 
        
        testBalancedTree(balancedSearchTree);
        System.out.println("");
        testUnbalancedTree(unbalancedSearchTree);
    }
    
    public static int getHeightOfTree(TreeMap<Integer, Integer> tm) {
        
        Position<Entry<Integer,Integer>> proot = tm.root();
        int height = findHeight(proot, tm, 0);
        
        return height;
        
    }
    
    // recursively find height of given tree
    public static int findHeight(
            Position<Entry<Integer,Integer>> p, 
            TreeMap<Integer, Integer> tm, 
            int current_height) {
        
        // track every left child first
        if(tm.left(p) != null) {
            int tmp_height = findHeight(tm.left(p), tm, current_height+1);
            
            if(tmp_height > current_height)
                current_height = tmp_height;
        }
        
        if(tm.right(p) != null) {
            int tmp_height = findHeight(tm.right(p), tm, current_height+1);
            
            // only care if it is greater
            if(tmp_height > current_height)
                current_height = tmp_height;
        }
        
        return current_height;
    }
    
    public static void testUnbalancedTree(TreeMap<Integer, Integer> ubst) {
        Random r = new Random();
        
        System.out.println("== Unbalanced Tree ==");
        System.out.println("Before any insertions: ");
        System.out.println("Tree map size:   " + ubst.size());
        System.out.println("Tree map height: " + getHeightOfTree(ubst));
    
        for(int i = 0; i < 8800; i++) 
            ubst.put(i, r.nextInt()); // unique key each time
    
        System.out.println("After " + N + " insertions: ");
        System.out.println("Tree map size:   " + ubst.size());
        System.out.println("Tree map height: " + getHeightOfTree(ubst));
    }
    
    public static void testBalancedTree(AVLTreeMap<Integer, Integer> bst) {
        Random r = new Random();
        
        System.out.println("== Balanced Tree ==");
        System.out.println("Before any insertions: ");
        System.out.println("Tree map size:   " + bst.size());
        
        // height of balanced search tree is log base-2 of height of purely unbalanced tree
        // which is what recursive findHeight function returns
        int treeHeight = getHeightOfTree(bst);
        if(treeHeight != 0)
            treeHeight = (int)(Math.log((double)treeHeight) / Math.log(2.0));
        
        System.out.println("Tree map height: " + treeHeight);
        
        // key,value insertion routine
        for(int i = 0; i < 8800; i++)
            bst.put(i, r.nextInt()); // unique key each time
        
        System.out.println("After " + N + " insertions: ");
        System.out.println("Tree map size:   " + bst.size());
        treeHeight = (int)(Math.log((double)getHeightOfTree(bst)) / Math.log(2.0));
        System.out.println("Tree map height: " + treeHeight);
        
    }
    
}
