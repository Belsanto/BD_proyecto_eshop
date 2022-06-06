/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.Serializable;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

    
public class Conexiones implements Serializable {

     public DataSource dataSource;
    
    public static String ip = "localhost";
    
    public String db = "eshop_proyectobd";
    public String puerto = "3306";
    public String url = "jdbc:mysql://"+ip+":"+puerto+"/"+db;
    public String user = "localhost";
    public String pass = "";
    
    
    public Conexiones(){
        
        inicializaDataSource();
        
}
    
    private void inicializaDataSource(){
        
        BasicDataSource basicDataSource = new BasicDataSource();
        
        basicDataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxActive(50);
        
        dataSource = basicDataSource;
        
        
    }
    
    
}
