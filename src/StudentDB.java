import java.sql.*;
import java.util.ArrayList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Edward Alvarado
 */
public class StudentDB {
    //constants
    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/cis355a";
    private final String USER_NAME = "root";
    private final String PASSWORD = "chichi21";
            
    
    //behaviors
    
    //save a Student object to the database
    public void add(Student stu) throws ClassNotFoundException, SQLException{
        // ALWAYS use PreparedStatement to write to databases when we get input from users
        // to help prevent hacking. Injection attacks are very common against databases.
        // http://sqlzoo.net/hack
        
        //check for the driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        //connect to the database
        Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
        
        // write the Student record to the database
        String sqlStr = "INSERT INTO Students (StudentName, Test1, Test2, Test3) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, stu.getName());
        pstmt.setDouble(2, stu.getTest1());
        pstmt.setDouble(3, stu.getTest2());
        pstmt.setDouble(4, stu.getTest3());
        
        pstmt.execute();
        
        //close the connection
        conn.close();
    } //end of add method
    
    public ArrayList<Student> getAll() throws ClassNotFoundException, SQLException{
        // create an empty ArrayList
        ArrayList<Student> list = new ArrayList<Student>();   
        
        // check for the driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        //connect to the database
        Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
        
        // get records from the database
        String strSQL = "SELECT * FROM Students";
        Statement stmt = conn.createStatement();
        ResultSet rs =stmt.executeQuery(strSQL);
        
        while (rs.next())
        {
            int stuID = rs.getInt(1);
            String name = rs.getString(2);
            double test1 = rs.getDouble(3);
            double test2 = rs.getDouble(4);
            double test3 = rs.getDouble(5);
            
            Student stu = new Student(stuID, name, test1, test2, test3);
            
            list.add(stu);
        }
        
        //close the connection to the database       
        conn.close();
        
        //return the ArrayList
        return list;
    } // end of getAll method
    
    
}// end of StudentDB Class
