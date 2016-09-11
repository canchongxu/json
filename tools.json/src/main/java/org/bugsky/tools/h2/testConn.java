package org.bugsky.tools.h2;

import java.sql.*;
public class testConn {
    public static void main(String[] a)
            throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
            getConnection("jdbc:h2:~/test", "sa", "");
        // add application code here
        
        conn.close();
    }
}