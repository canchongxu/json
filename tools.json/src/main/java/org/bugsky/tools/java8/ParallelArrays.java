package org.bugsky.tools.java8;


import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
 
public class ParallelArrays {
	
	public static Long printTime()
	{
        final Clock clock = Clock.systemUTC();
        return clock.millis();		
	}
	
	public static String getRandStr()
	{
		String retstr = "";
		for(int i=0; i<20; i++)
		{
			char c = (char) ('a' + ThreadLocalRandom.current().nextInt( 1000000 )%26);
			retstr += c;
		}
		return retstr;
	}
	
    public static void main( String[] args ) {
        String[] arrayOfString = new String[ 20000 ];        
         
        Arrays.parallelSetAll( arrayOfString, 
            index -> getRandStr() );
        Arrays.stream( arrayOfString ).limit(10).forEach( 
                s -> System.out.println(s) );

        ArrayList<String> getS = new ArrayList<String>();
        Long start = printTime();
        Arrays.parallelSort( arrayOfString );
        Arrays.stream( arrayOfString ).forEach( 
            s -> { if(0<=s.indexOf("ios"))getS.add(s);} );
        Long end = printTime();
        System.out.println(end - start);
        System.out.println(getS.size());
    }
}
