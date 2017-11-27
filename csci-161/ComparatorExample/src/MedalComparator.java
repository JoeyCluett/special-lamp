
public class MedalComparator implements Comparator<Athlete>{
    
    public int compare( Athlete a, Athlete b ){        
        
        if ( a == null || b == null )  
        {
            //throw null pointer exception
        }
                
        int returnValue = 0;
        
        String aMedal = a.getMedal();
        String bMedal = b.getMedal();      
        
        switch ( aMedal )
        {
            case "gold" : 
            {
                switch ( bMedal )
                {
                    case "gold"   : returnValue = 0; break;                    
                    case "silver" : returnValue = 1; break;
                    case "bronze" : returnValue = 1; break;               
                } 
                break;
            }
            case "silver" :
            {
                switch ( bMedal )
                {
                    case "gold"   : returnValue = -1; break;                    
                    case "silver" : returnValue =  0; break;
                    case "bronze" : returnValue =  1; break;
                }
                break;
            }
            case "bronze" :
            {
                switch ( bMedal )
                {
                    case "gold"   : returnValue = -1; break;                    
                    case "silver" : returnValue = -1; break;
                    case "bronze" : returnValue =  0; break;
                }      
                break;
            }
            default :
            {
                //throw invalid medal exception
            }            
        }
        
        return returnValue;
    }            
}
