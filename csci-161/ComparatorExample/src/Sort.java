
public class Sort {

    public static <K> void bubbleSort( K[] data, Comparator<K> comp )
    {
        for ( int i = 0; i < data.length; i++ )
        {
            for ( int j = 0; j < data.length - 1; j++ )
            {
                if ( comp.compare( data[j], data[j+1] ) <= 0 )
                {
                    K temp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = temp;
                }
            }
        }
    }    
}
