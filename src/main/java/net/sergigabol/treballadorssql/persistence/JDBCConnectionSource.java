/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.treballadorssql.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gabalca
 */
public class JDBCConnectionSource {
    
    private static final String url = "jdbc:derby://localhost:1527/prova";
    private static final String user = "sergi";
    private static final String password = "sergi";
    
    //TODO llegir això d'un fitxer de propeitats
    
    public Connection getConnection() throws SQLException{
        
        return DriverManager.getConnection(url, user, password);
        
    }
    
}
