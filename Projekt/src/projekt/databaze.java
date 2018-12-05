/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import java.sql.*;

/**
 *
 * @author acer
 */
public class databaze {
    
    private Connection pripojit;
    private Statement st;
    private ResultSet vys;
    
    public databaze()
    {
        try 
        {
                Class.forName("com.mysql.jdbc.Driver");
                pripojit=DriverManager.getConnection("jdbc:mysql://localhost/java?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
                st=(Statement) pripojit.createStatement();
        }
        
        catch(Exception ex)
        {       
            System.out.println("Chyba: " +ex);
        }
    }
    
    public void vlozitVysledek(String vysledek)
    {
        try 
        {
             //   st.executeUpdate("INSERT INTO data(vysledek) VALUES ("+vysledek+")");               
                String sql= "INSERT INTO data(vysledek) VALUES(?)";
                PreparedStatement ps = pripojit.prepareStatement(sql);
                ps.setString(1, vysledek);               
                int spust = ps.executeUpdate();
        }
        
        catch(Exception ex)
        {       
           System.out.println("Chyba: " +ex);
        }
    }
        
    }
    

