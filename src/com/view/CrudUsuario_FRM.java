package com.view;

import com.controller.CarteraJpaController;
import com.controller.ControladorAuxiliar;
import com.controller.DepartamentoJpaController;
import com.controller.UsuarioJpaController;
import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Cartera;
import com.entities.Departamento;
import com.entities.Usuario;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class CrudUsuario_FRM extends javax.swing.JFrame {

    UsuarioJpaController usuarioCtr = new UsuarioJpaController();
    Usuario usuario = new Usuario();

    DepartamentoJpaController departamentoCtr = new DepartamentoJpaController();
    Departamento departamento = new Departamento();

    CarteraJpaController carteraCtr = new CarteraJpaController();
    Cartera cartera = new Cartera();

    ControladorAuxiliar mainController = new ControladorAuxiliar();
    DefaultTableModel modeloServicios;

    String fotoUser;
    JFileChooser seleccionar = new JFileChooser();
    File archivo;
    byte[] imagen;
    FileInputStream entrada;
    FileOutputStream salida;
    ArrayList<String> telefonos = new ArrayList<>();
    Image foto;

    public CrudUsuario_FRM() {
        initComponents();
        getTablaUsuario();
        getComboBoxCiudad();
        cbTelefono.addItem("No hay");
    }

    public byte[] abrirArchivo(File archivo) {
        byte[] imagen = new byte[1240 * 1000];
        try {
            entrada = new FileInputStream(archivo);
            entrada.read(imagen);
        } catch (Exception e) {
        }
        return imagen;
    }

    public String guardarArchivo(File archivo, byte[] imagen) {
        String mensaje = null;
        try {
            salida = new FileOutputStream(archivo);
            salida.write(imagen);
            mensaje = "Archivo Guardado";

        } catch (Exception e) {
        }
        return mensaje;
    }

    private void setDefaultFoto() {

        fotoUser = "C:\\Users\\USER\\Documents\\NetBeansProjects\\BD_proyecto_eshop\\src\\fotos\\deafault.jpg";

        foto = getToolkit().getImage(fotoUser);

        foto = foto.getScaledInstance(250, 200, Image.SCALE_DEFAULT);

        labelFoto.setText("");
        labelFoto.setIcon(new ImageIcon(foto));
    }

    private void setUserFoto(Usuario user) {

        fotoUser = user.getRutaFoto();

        foto = getToolkit().getImage(fotoUser);
        if (fotoUser.length() > 40) {

            foto = foto.getScaledInstance(250, 200, Image.SCALE_DEFAULT);

            labelFoto.setText("");
            labelFoto.setIcon(new ImageIcon(foto));
        } else {
            setDefaultFoto();
        }
    }

    private void getTablaUsuario() {
        String col[] = {"NOMBRE", "USUARIO", "CONTRASEÑA", "EMAIL", "CIUDAD"};
        DefaultTableModel modelo = new DefaultTableModel(col, 0);
        Object obj[] = new Object[5];
        List ls;
        try {
            ls = usuarioCtr.findUsuarioEntities();
            for (int i = 0; i < ls.size(); i++) {
                usuario = (Usuario) ls.get(i);
                obj[0] = usuario.getNombre();
                obj[1] = usuario.getUsername();
                obj[2] = usuario.getPassword();
                obj[3] = usuario.getEmail();
                obj[4] = usuario.getDepartamentoUsuario().getNombre();
                modelo.addRow(obj);
            }
            this.jTablaUsuarios.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar persistencia");

        }

    }

    private void getComboBoxCiudad() {
        List ls;
        ls = departamentoCtr.findDepartamentoEntities();
        String value = "Seleccione una ciudad";
        cbCiudad.addItem(value);
        for (int i = 0; i < ls.size(); i++) {
            departamento = (Departamento) ls.get(i);
            value = departamento.getCodigo() + " " + departamento.getNombre();
            cbCiudad.addItem(value);
        }
    }

    private void getComboBoxTelefono(int idUser) {
        ArrayList<String> ls;
        ls = mainController.findTelefonosUser(idUser);
        String value = "";
        for (int i = 0; i < ls.size(); i++) {
            value = ls.get(i);
            cbTelefono.addItem(value);
            telefonos.add(value);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new org.edisoncor.gui.panel.PanelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaUsuarios = new javax.swing.JTable();
        panelRect1 = new org.edisoncor.gui.panel.PanelRect();
        jLabel1 = new javax.swing.JLabel();
        txtname = new org.edisoncor.gui.textField.TextFieldRectBackground();
        jLabel2 = new javax.swing.JLabel();
        txtUsename = new org.edisoncor.gui.textField.TextFieldRectBackground();
        jLabel3 = new javax.swing.JLabel();
        txtpassword = new org.edisoncor.gui.passwordField.PasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtemail = new org.edisoncor.gui.textField.TextFieldRectBackground();
        jLabel5 = new javax.swing.JLabel();
        cbCiudad = new org.edisoncor.gui.comboBox.ComboBoxRect();
        buttonAction3 = new org.edisoncor.gui.button.ButtonAction();
        buttonAction4 = new org.edisoncor.gui.button.ButtonAction();
        buttonAction1 = new org.edisoncor.gui.button.ButtonAction();
        buttonAction2 = new org.edisoncor.gui.button.ButtonAction();
        panelLlamada1 = new org.edisoncor.gui.panel.PanelLlamada();
        labelRound1 = new org.edisoncor.gui.label.LabelRound();
        btnCantPuntos = new org.edisoncor.gui.button.ButtonRound();
        jLabel6 = new javax.swing.JLabel();
        txtfoto = new org.edisoncor.gui.textField.TextFieldRectBackground();
        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        labelFoto = new javax.swing.JLabel();
        buttonAero1 = new org.edisoncor.gui.button.ButtonAero();
        jLabel7 = new javax.swing.JLabel();
        cbTelefono = new org.edisoncor.gui.comboBox.ComboBoxRect();
        btnTelefonos = new org.edisoncor.gui.button.ButtonAero();
        btnCLear = new org.edisoncor.gui.button.ButtonAction();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelRound1.setColorDeBorde(new java.awt.Color(51, 51, 51));
        panelRound1.setColorPrimario(new java.awt.Color(0, 0, 0));
        panelRound1.setColorSecundario(new java.awt.Color(0, 255, 51));

        jTablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTablaUsuarios);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        panelRect1.setColorDeBorde(new java.awt.Color(0, 255, 204));
        panelRect1.setColorPrimario(new java.awt.Color(51, 255, 0));
        panelRect1.setColorSecundario(new java.awt.Color(255, 255, 255));

        jLabel1.setText("NOMBRE");

        jLabel2.setText("USERNAME");

        jLabel3.setText("PASSWORD");

        jLabel4.setText("EMAIL");

        jLabel5.setText("CIUDAD");

        buttonAction3.setText("Borrar");
        buttonAction3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction3ActionPerformed(evt);
            }
        });

        buttonAction4.setText("Agregar");
        buttonAction4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction4ActionPerformed(evt);
            }
        });

        buttonAction1.setText("Buscar");
        buttonAction1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction1ActionPerformed(evt);
            }
        });

        buttonAction2.setText("Modificar");
        buttonAction2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAction2ActionPerformed(evt);
            }
        });

        panelLlamada1.setBackground(new java.awt.Color(255, 255, 255));
        panelLlamada1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "CARTERA"));
        panelLlamada1.setColorPrimario(new java.awt.Color(255, 255, 255));
        panelLlamada1.setColorSecundario(new java.awt.Color(204, 204, 204));

        labelRound1.setForeground(new java.awt.Color(0, 0, 0));
        labelRound1.setText("Cantidad de Puntos");
        labelRound1.setColorDeSombra(new java.awt.Color(204, 204, 204));

        btnCantPuntos.setBackground(new java.awt.Color(204, 255, 204));
        btnCantPuntos.setForeground(new java.awt.Color(0, 0, 0));
        btnCantPuntos.setText("Cantidad puntos");
        btnCantPuntos.setColorDeSombra(new java.awt.Color(153, 153, 153));
        btnCantPuntos.setEnabled(false);
        btnCantPuntos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCantPuntosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLlamada1Layout = new javax.swing.GroupLayout(panelLlamada1);
        panelLlamada1.setLayout(panelLlamada1Layout);
        panelLlamada1Layout.setHorizontalGroup(
            panelLlamada1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLlamada1Layout.createSequentialGroup()
                .addGroup(panelLlamada1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLlamada1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(labelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLlamada1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLlamada1Layout.setVerticalGroup(
            panelLlamada1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLlamada1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel6.setText("FOTO");

        txtfoto.setEnabled(false);

        panelImage1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelFoto.setText("foto");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        buttonAero1.setBackground(new java.awt.Color(51, 255, 204));
        buttonAero1.setText("SELECCIONAR FOTO");
        buttonAero1.setColorDeSombra(new java.awt.Color(102, 102, 102));
        buttonAero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAero1ActionPerformed(evt);
            }
        });

        jLabel7.setText("TELEFONO");

        btnTelefonos.setBackground(new java.awt.Color(51, 255, 204));
        btnTelefonos.setText("AÑADIR");
        btnTelefonos.setColorDeSombra(new java.awt.Color(102, 102, 102));
        btnTelefonos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTelefonosActionPerformed(evt);
            }
        });

        btnCLear.setText("LIMPIAR");
        btnCLear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCLearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRect1Layout = new javax.swing.GroupLayout(panelRect1);
        panelRect1.setLayout(panelRect1Layout);
        panelRect1Layout.setHorizontalGroup(
            panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRect1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelRect1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelRect1Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRect1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRect1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsename, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRect1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelRect1Layout.createSequentialGroup()
                        .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRect1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelLlamada1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(buttonAction2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonAction1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(buttonAction3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonAction4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRect1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCLear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(220, 220, 220))))
        );
        panelRect1Layout.setVerticalGroup(
            panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRect1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonAction4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAction3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(buttonAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAction1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelRect1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLlamada1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCLear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(panelRect1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsename, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRect1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelRect1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTelefonos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRect1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelRect1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAction4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction4ActionPerformed
        if (txtname.equals("") || txtUsename.equals("") || txtemail.equals("")
                || txtfoto.equals("") || txtpassword.equals("")
                || cbCiudad.getSelectedIndex() < 1 || cbTelefono.getSelectedItem().equals("No hay")) {
            JOptionPane.showMessageDialog(null, "Error con los datos");
        } else {
            usuario = new Usuario();
            usuario.setNombre(txtname.getText());
            usuario.setUsername(txtUsename.getText());
            usuario.setEmail(txtemail.getText());
            usuario.setRutaFoto(txtfoto.getText());
            usuario.setPassword(txtpassword.getText());
            usuario.setDepartamentoUsuario(departamentoCtr.findDepartamento(cbCiudad.getSelectedIndex()));
            usuarioCtr.create(usuario);
            //aqui guarda la foto
            mainController.insertarTelefonosUser(usuario.getCodigo(), telefonos);
            archivo = new File("C:\\Users\\USER\\Documents\\NetBeansProjects\\BD_proyecto_eshop\\src\\fotos\\" + usuario.getCodigo() + ".jpg");
            if (archivo.getName().endsWith("jpg") || archivo.getName().endsWith("jpeg")) {
                String respuesta = guardarArchivo(archivo, imagen);
                if (respuesta != null) {
                    JOptionPane.showMessageDialog(null, respuesta);
                } else {
                    JOptionPane.showMessageDialog(null, "Archivo no guardado");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Archivo guardado");
            }
        }
    }//GEN-LAST:event_buttonAction4ActionPerformed

    private void buttonAero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAero1ActionPerformed
        // TODO add your handling code here:
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg", "jpeg");
        JFileChooser archivo = new JFileChooser();
        archivo.addChoosableFileFilter(filtro);
        archivo.setDialogTitle("Abrir Archivo");
        File ruta = new File("C:/Users/USER/Pictures");
        archivo.setCurrentDirectory(ruta);
        int ventana = archivo.showOpenDialog(null);
        if (ventana == JFileChooser.APPROVE_OPTION) {
            File file = archivo.getSelectedFile();
            txtfoto.setText(String.valueOf(file));
            fotoUser = txtfoto.getText();
            foto = getToolkit().getImage(fotoUser);
            foto = foto.getScaledInstance(200, 180, Image.SCALE_DEFAULT);
            labelFoto.setText("");
            labelFoto.setIcon(new ImageIcon(foto));
            imagen = abrirArchivo(file);

        }


    }//GEN-LAST:event_buttonAero1ActionPerformed

    private void btnTelefonosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTelefonosActionPerformed
        // TODO add your handling code here:
        try {

            long aux = Long.parseLong(JOptionPane.showInputDialog("ingrese un numero de telefono de diez digitos"));
            String phone = aux + "";
            if (phone.length() == 10) {

                if (cbTelefono.getItemAt(0).equals("No hay")) {
                    cbTelefono.removeAllItems();
                }
                telefonos.add(phone);
                cbTelefono.addItem(phone);
            } else {

                JOptionPane.showMessageDialog(null, "El numero debe ser de 10 cifras");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Formato numerico incorrecto");
        }
    }//GEN-LAST:event_btnTelefonosActionPerformed

    private void btnCLearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCLearActionPerformed
        limpiar();
    }//GEN-LAST:event_btnCLearActionPerformed

    private void buttonAction3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction3ActionPerformed
        // TODO add your handling code here:

        try {
            int cod = Integer.parseInt(JOptionPane.showInputDialog("ingrese el codigo del usuario que desea eliminar"));
            Usuario user = usuarioCtr.findUsuario(cod);

            if (usuario != null) {
                try {
                    mainController.borrararTelefonosUser(user.getCodigo());
                    usuarioCtr.destroy(user.getCodigo());
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(CrudUsuario_FRM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CrudUsuario_FRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(CrudUsuario_FRM.class.getName()).log(Level.SEVERE, null, e);
        }

    }//GEN-LAST:event_buttonAction3ActionPerformed

    private void buttonAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction2ActionPerformed
        if (txtname.equals("") || txtUsename.equals("") || txtemail.equals("")
                || txtfoto.equals("") || txtpassword.equals("")
                || cbCiudad.getSelectedIndex() < 1 || cbTelefono.getSelectedItem().equals("No hay")) {
            JOptionPane.showMessageDialog(null, "Error con los datos");
        } else {
            if (usuario != null) {

                try {
                    usuario.setNombre(txtname.getText());
                    usuario.setRutaFoto(txtfoto.getText());
                    usuario.setPassword(txtpassword.getText());
                    usuario.setDepartamentoUsuario(departamentoCtr.findDepartamento(cbCiudad.getSelectedIndex()));
                    usuarioCtr.edit(usuario);
                    archivo = new File("C:\\Users\\USER\\Documents\\NetBeansProjects\\BD_proyecto_eshop\\src\\fotos\\" + usuario.getCodigo() + ".jpg");
//                        mainController.borrararTelefonosUser(usuario.getCodigo());
                    mainController.insertarTelefonosUser(usuario.getCodigo(), telefonos);
                    if (archivo.getName().endsWith("jpg") || archivo.getName().endsWith("jpeg")) {
                        String respuesta = guardarArchivo(archivo, imagen);
                        if (respuesta != null) {
                            JOptionPane.showMessageDialog(null, respuesta);
                        } else {
                            JOptionPane.showMessageDialog(null, "Foto no guardada o seleccionada");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Archivo guardado");
                    }
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CrudUsuario_FRM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(CrudUsuario_FRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_buttonAction2ActionPerformed

    private void buttonAction1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAction1ActionPerformed
        try {
            int cod = Integer.parseInt(JOptionPane.showInputDialog("ingrese el codigo del usuario que desea eliminar"));
            usuario = usuarioCtr.findUsuario(cod);

            if (usuario != null) {
                setUsuario(usuario);
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(null, "ERROR! no se encontraron datos");
        } catch (NullPointerException e) {

            JOptionPane.showMessageDialog(null, "ERROR! no se encontraron datos");
        }
    }//GEN-LAST:event_buttonAction1ActionPerformed

    private void btnCantPuntosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCantPuntosActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Tienes: " + "\n" + usuario.getCarteraList().get(0).getTotalPuntosDisponibles() + " Puntos disponibles. "
                + "\n" + usuario.getCarteraList().get(0).getTotalPuntosUsados() + " Puntos gastados. "
                + "\n" + usuario.getCarteraList().get(0).getTotalPuntosVencidos() + " Puntos vencidos. ");
    }//GEN-LAST:event_btnCantPuntosActionPerformed

    public void setUsuario(Usuario user) {
        txtUsename.setText(user.getUsername());
        txtemail.setText(user.getEmail());
        txtname.setText(user.getNombre());
        txtpassword.setText(user.getPassword());
        setUserFoto(user);
        cbTelefono.removeAllItems();
        getComboBoxTelefono(user.getCodigo());
        cbCiudad.setSelectedIndex(user.getDepartamentoUsuario().getCodigo());
        btnCantPuntos.setEnabled(true);
        btnCantPuntos.setText("Tienes: " + user.getCarteraList().get(0).getTotalPuntosDisponibles());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CrudUsuario_FRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrudUsuario_FRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrudUsuario_FRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrudUsuario_FRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrudUsuario_FRM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonAction btnCLear;
    private org.edisoncor.gui.button.ButtonRound btnCantPuntos;
    private org.edisoncor.gui.button.ButtonAero btnTelefonos;
    private org.edisoncor.gui.button.ButtonAction buttonAction1;
    private org.edisoncor.gui.button.ButtonAction buttonAction2;
    private org.edisoncor.gui.button.ButtonAction buttonAction3;
    private org.edisoncor.gui.button.ButtonAction buttonAction4;
    private org.edisoncor.gui.button.ButtonAero buttonAero1;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbCiudad;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablaUsuarios;
    private javax.swing.JLabel labelFoto;
    private org.edisoncor.gui.label.LabelRound labelRound1;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelLlamada panelLlamada1;
    private org.edisoncor.gui.panel.PanelRect panelRect1;
    private org.edisoncor.gui.panel.PanelRound panelRound1;
    private org.edisoncor.gui.textField.TextFieldRectBackground txtUsename;
    private org.edisoncor.gui.textField.TextFieldRectBackground txtemail;
    private org.edisoncor.gui.textField.TextFieldRectBackground txtfoto;
    private org.edisoncor.gui.textField.TextFieldRectBackground txtname;
    private org.edisoncor.gui.passwordField.PasswordField txtpassword;
    // End of variables declaration//GEN-END:variables

    private void limpiar() {
        cbTelefono.removeAll();
        txtUsename.setText("");
        txtemail.setText("");
        txtfoto.setText("");
        txtname.setText("");
        txtpassword.setText("");
        getComboBoxCiudad();
        cbCiudad.setSelectedIndex(0);

        cbTelefono.addItem("No hay");

        usuario = null;
        telefonos = new ArrayList<>();

        setDefaultFoto();
    }

}
