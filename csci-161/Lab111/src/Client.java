/**
 *
 * @author joey
 */
public class Client {
    public static void main(String[] args) {
        // reallly wishing for typedef stuff right here:
        TreeMap<Integer, Integer> unbalancedSearchTree  = new TreeMap<>();
        AVLTreeMap<Integer, Integer> balancedSearchTree = new AVLTreeMap<>();   
    }
    
    public static void testUnbalancedTree(TreeMap<Integer, Integer> ubst) {
        System.out.println("== Unbalanced Tree ==");
        System.out.println("Before any insertions: ");
        System.out.println("Tree map size:   " + ubst.size());
        System.out.println("Tree map height: " + ubst.depth());
    }
    
    public static void testBalancedTree(AVLTreeMap<Integer, Integer> bst) {
        
    }
    
}
