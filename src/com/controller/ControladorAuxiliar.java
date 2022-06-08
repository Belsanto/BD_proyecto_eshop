/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entities.Cartera;
import com.entities.Departamento;
import com.entities.Usuario;
import java.awt.Desktop;
import java.awt.HeadlessException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author USER
 */
public class ControladorAuxiliar implements Serializable {

    UsuarioJpaController usuarioCtr;
    Usuario usuario;

    DepartamentoJpaController departamentoCtr;
    Departamento departamento;

    CarteraJpaController carteraCtr;
    Cartera cartera;

    Connection con = null;
    Statement stSentenciaSQL;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement st;

    Conexiones metodosPool = new Conexiones();

    public static String ip = "localhost";
    public static String puerto = "3306";

    public static Connection conn;
    public static String driver = "com.mysql.jdbc.Driver";
    public static String user = "root";
    public static String password = "";
    public String url = "jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd";

    ////////////////////////Esta es la primer conexion
    public ControladorAuxiliar() {

        conn = null;
        cartera = new Cartera();
        carteraCtr = new CarteraJpaController();
        departamento = new Departamento();
        departamentoCtr = new DepartamentoJpaController();
        usuarioCtr = new UsuarioJpaController();
        usuario = new Usuario();
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

        ArrayList<String> datosT = new ArrayList<>();
        String datos[] = new String[4];

        try {

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datosT.add(datos[1]);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrarUser" + e);

            return null;
        }
        return datosT;
    }

    public Usuario findUserByEmail(String email) {

        String sql = "SELECT * FROM usuario WHERE email='" + email + "'";

        String datos[] = new String[4];

        try {

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

            }
            JOptionPane.showMessageDialog(null, "Usuario con codigo: " + datos[0]
                    + "\n Ha sido encontrado correctamente");
            email = datos[0];

            return usuarioCtr.findUsuario(Integer.parseInt(email));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrarUser" + e);

            return null;
        }
    }

    public Usuario findUserByUsername(String user) {

        String sql = "SELECT * FROM usuario WHERE username='" + user + "'";

        String datos[] = new String[4];

        try {

            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

            }
            JOptionPane.showMessageDialog(null, "Usuario con codigo: " + datos[0]
                    + "\n Ha encontrado correctamente");
            user = datos[0];
            return usuarioCtr.findUsuario(Integer.parseInt(user));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrarUser" + e);

            return null;
        }
    }

    public void insertarCartera(int userId) {
        try {
            String sql = "INSERT INTO cartera (codigo, usuario_cartera_codigo) VALUES (?,?)";
            //AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, userId + "");
            pst.setString(2, userId + "");

            pst.executeUpdate();

        } catch (SQLException | HeadlessException ex) {
            System.out.println("Error en FrmControlaT " + ex);
            JOptionPane.showMessageDialog(null, ex);

        }

    }

    public void insertarTelefonosUser(int userId, ArrayList<String> telefonos) {
        for (int i = 0; i < telefonos.size(); i++) {
            try {
                String sql = "INSERT INTO usuario_num_telefono (usuario_codigo, num_telefono) VALUES (?,?)";
                //AQUI HAY UNA CONEXION
                con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
                pst = con.prepareStatement(sql);
                pst.setString(1, userId + "");
                pst.setString(2, telefonos.get(i));

                pst.executeUpdate();

            } catch (SQLException | HeadlessException ex) {
                System.out.println("Error en FrmControlaT " + ex);
                JOptionPane.showMessageDialog(null, ex);

            }
        }
    }

    public void borrararTelefonosUser(int userId) {

        try {
            String sql = "DELETE FROM `usuario_num_telefono` WHERE usuario_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, userId + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    
    public void borrararCarteraUser(int userId) {

        try {
            String sql = "DELETE FROM `cartera` WHERE usuario_cartera_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, userId + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararComentarioProducto(String codigo) {

        try {
            String sql = "DELETE FROM `comentario` WHERE productoc_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararComentarioUser(int codigo) {

        try {
            String sql = "DELETE FROM `comentario` WHERE user_coment_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararCompraUsuario(int codigo) {

        try {
            String sql = "DELETE FROM `compra` WHERE comprador_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararDetallecanjeProducto(String codigo) {

        try {
            String sql = "DELETE FROM `detalle_canje` WHERE producto_canje_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararDetallecompraProducto(String codigo) {

        try {
            String sql = "DELETE FROM `detalle_compra` WHERE producto_compra_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararcategoriaProducto(String codigo) {

        try {
            String sql = "DELETE FROM `producto_categorias` WHERE producto_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararImagenesProducto(String codigo) {

        try {
            String sql = "DELETE FROM `producto_imagen_ruta` WHERE producto_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, codigo + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    
    public void borrararProductosUser(int userId) {

        try {
            String sql = "DELETE FROM `producto` WHERE vendedor_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, userId + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararProductoSubastaUser(int userId) {

        try {
            String sql = "DELETE FROM `producto_subasta` WHERE vendedor_codigo=?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, userId + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    public void borrararSubastasUser(int userId) {

        try {
            String sql = "DELETE FROM `subasta_usuario` WHERE usuario_subasta_codigo =?"; /// AQUI HAY UNA CONEXION
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/eshop_proyectobd", user, password);
            pst = con.prepareStatement(sql);
            pst.setString(1, userId + "");

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Eliminado " + con);

        } catch (SQLException | HeadlessException ex) {
            System.out.println("error al eliminar" + ex);
            JOptionPane.showMessageDialog(null, ex);

        }
    }
    
    

}
