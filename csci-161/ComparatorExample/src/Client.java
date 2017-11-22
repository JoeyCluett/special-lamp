
public class Client {

    public static void main(String[] args) {
        
        Athlete[] competator = new Athlete[ 6 ];        
        competator[ 0 ] = new Athlete( "Joe", "100 meter sprint", "bronze" );
        competator[ 1 ] = new Athlete( "Tom", "100 meter sprint", "silver" );
        competator[ 2 ] = new Athlete( "Jane", "100 meter free style", "silver" );
        competator[ 3 ] = new Athlete( "Penny", "100 meter free style", "bronze");     
        competator[ 4 ] = new Athlete( "Sue", "100 meter free style", "gold" ); 
        competator[ 5 ] = new Athlete( "Fred", "100 meter sprint", "gold");
        
        System.out.println("Unsorted array: ");
        printArray( competator );
        System.out.println();
       
        Comparator comp = new MedalComparator();
        
        Sort.bubbleSort( competator, comp );
        
        System.out.println("Sorted array: ");
        printArray( competator );
        System.out.println();
                
    }
                    
    public static void printArray( Athlete[] data )
    {
        for ( int i = 0; i < data.length; i++ )
            System.out.println( data[i] );
    }
    
}
