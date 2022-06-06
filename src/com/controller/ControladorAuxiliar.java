/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ControladorAuxiliar implements Serializable {

    Connection con = null;
    Statement stSentenciaSQL;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement st;

    Conexiones metodosPool = new Conexiones();

    public static String sesionUsuario;

    public static String ip = "localhost";
    public static String puerto = "3306";

    public static Connection conn;
    public static String driver = "com.mysql.jdbc.Driver";
    public static String user = "root";
    public static String password = "12345";
    public String url = "jdbc:mysql://" + ip + ":" + puerto + "/turnero";

    ////////////////////////Esta es la primer conexion
    public ControladorAuxiliar() {

        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("CONEXION EXITOSA B");
            }

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("Error al conectar" + e);
        }
    }

    public ResultSet consulta(String sql) throws SQLException {
        try {
            rs = stSentenciaSQL.executeQuery(sql);//Para sentencias select unicamente

        } catch (SQLException e) {
            throw e;
        }
        return rs;
    }

    public void ejecutar(String sql) throws SQLException {
        try {
            stSentenciaSQL.execute(sql);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    //Este metodo retorna la conexion
    public Connection getConnection() {

        return conn;
    }

    // Este metodo prueba la conexion con la bd
    public boolean probarConexion() {

        Conexiones metodospool = new Conexiones();
        java.sql.Connection cn;

        try {

            cn = metodospool.dataSource.getConnection();

            if (cn != null) {

                JOptionPane.showMessageDialog(null, "Conectado con: " + url);

            }
            return true;

        } catch (SQLException e) {

            System.out.println(e + "ERROR, de coneccion");
            return false;
        }

    }

    // Este metodo desconecta la bd
    public void desconectar() {

        conn = null;
        if (conn == null) {

            System.out.println("CONEXION TERMINADA");

        }

    }

    // IMPRESORAS
    public void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }

    
////////////////////////////
    //Para 
    public ArrayList<String> findTelefonosUser(int idUser) {

        String sql = "SELECT * FROM usuario_num_telefono WHERE usuario_codigo='" + idUser + "'";

        ArrayList<String> datos = new ArrayList<>();
        
        try {

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                datos.add(rs.getString(1));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar telefonos" + e);

        }
        return datos;
    }
}
