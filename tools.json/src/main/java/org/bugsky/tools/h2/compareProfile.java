package org.bugsky.tools.h2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
 
public class compareProfile {
	private static final int DATA_NUM = 20000;
	private static String[] arrayOfString = new String[ DATA_NUM ];
	private static Connection conn;
	private static Statement stateMent;
	private static String[] searchWords = {"ios", "name", "bsdo", "sdo"};
	
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
	
	public static void initH2() throws SQLException, ClassNotFoundException
	{
        Class.forName("org.h2.Driver");
        conn = DriverManager.
            getConnection("jdbc:h2:~/test", "sa", "");
        stateMent = conn.createStatement();
	}
	
	public static void initData() throws SQLException
	{
		stateMent.execute("drop table if exists TEST ");
		stateMent.execute("create table TEST( name varchar(100)) ");
		for(int i=0; i<DATA_NUM; i++)
		{
			String str = getRandStr();
			arrayOfString[i] = str;
			stateMent.execute("insert into TEST values(' " +str+ "') ");
		}
		stateMent.execute("create index just on TEST(name) ");		
	}
	
	public static void testMem()
	{
        ArrayList<String> getS = new ArrayList<String>();
        Long start = printTime();
        Arrays.parallelSort( arrayOfString );
        Arrays.stream( arrayOfString ).forEach( 
            s -> { if(0<=s.indexOf(searchWords[0]))getS.add(s);} );
        Long end = printTime();
        System.out.println(end - start);
        System.out.println(getS.size());		
	}
	
	public static void testH2() throws SQLException
	{
        ArrayList<String> getS = new ArrayList<String>();
        Long start = printTime();
        ResultSet rs = stateMent.executeQuery("select name from TEST where name like '%" +searchWords[0]+ "%' ");
        while(rs.next())
        {
        	getS.add(rs.getString(1));
        }
        Long end = printTime();
        System.out.println(end - start);
        System.out.println(getS.size());		
	}	
	
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        
        initH2();
        
        initData();
        testMem();
        
        System.out.println("----------");
        
        testH2();
    }
}
