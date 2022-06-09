/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.controller.DeliveryJpaController;
import com.controller.DepartamentoJpaController;
import com.controller.ControladorAuxiliar;
import com.controller.Conexiones;
import com.controller.RutaJpaController;
import com.controller.exceptions.NonexistentEntityException;
import com.entities.Delivery;
import com.entities.Departamento;
import com.entities.Ruta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LEIDY OSPINA
 */
public class CrudRuta_FRM extends javax.swing.JFrame {

    
    DepartamentoJpaController departamentoCtr = new DepartamentoJpaController();
    Departamento departamento = new Departamento();
    
    DeliveryJpaController deliveryCtr = new DeliveryJpaController();
    Delivery delivery = new Delivery();
    
    RutaJpaController rutaCtr = new RutaJpaController();
     Ruta ruta = new Ruta();
    
    public CrudRuta_FRM() {
        initComponents();
        getComboBoxEmpresa();
        getComboBox();
        getTablaRutas();
    }
    
    private void getComboBoxEmpresa() {
        List ls;
        ls = deliveryCtr.findDeliveryEntities();
        String value = "Seleccione una empresa";
         cb_Empresa.addItem(value);
        for (int i = 0; i < ls.size(); i++) {
            delivery = (Delivery) ls.get(i);
            value = delivery.getCodigo() + " " + delivery.getNombre();
            cb_Empresa.addItem(value);
        }
    }
    
    
    private void getComboBox() {
        List ls;
        ls = departamentoCtr.findDepartamentoEntities();
        String value = "Seleccione una ciudad";
         cb_ciudad.addItem(value);
        for (int i = 0; i < ls.size(); i++) {
            departamento = (Departamento) ls.get(i);
            value = departamento.getCodigo() + " " + departamento.getNombre();
            cb_ciudad.addItem(value);
        }
    }
    
     private void getTablaRutas() {
        String col[] = {"Código", "Estado", "Fecha Inicio", "Fecha Fin", "Ciudad", "Empresa"};
        DefaultTableModel modelo = new DefaultTableModel(col, 0);
        Object obj[] = new Object[6];
        List ls;
        try {
            ls = rutaCtr.findRutaEntities(); 
            for (int i = 0; i < ls.size(); i++) {
                ruta = (Ruta) ls.get(i);
                obj[0] = ruta.getCodigo();
                obj[1] = ruta.getEstado();
                obj[2] = ruta.getFechaInicioEntrega();
                obj[3] = ruta.getFechaFinEntrega();
                obj[4] = ruta.getDepartamentoCodigo().getNombre();
                obj[5] = delivery.getNombre();
                modelo.addRow(obj);
            }
            this.jTableRutas.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar persistencia");

        }
        
        

    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        panelRoundTranslucidoComplete1 = new org.edisoncor.gui.panel.PanelRoundTranslucidoComplete();
        jLabel1 = new javax.swing.JLabel();
        txt_codigo = new org.edisoncor.gui.textField.TextFieldRectIcon();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_estado = new org.edisoncor.gui.textField.TextFieldRectIcon();
        bt_modificar = new org.edisoncor.gui.button.ButtonRound();
        bt_crear = new org.edisoncor.gui.button.ButtonRound();
        bt_eliminar = new org.edisoncor.gui.button.ButtonRound();
        bt_buscar = new org.edisoncor.gui.button.ButtonRound();
        jLabel10 = new javax.swing.JLabel();
        cb_Empresa = new org.edisoncor.gui.comboBox.ComboBoxRect();
        canvas1 = new java.awt.Canvas();
        bt_limpiar = new org.edisoncor.gui.button.ButtonRound();
        jLabel11 = new javax.swing.JLabel();
        cb_ciudad = new org.edisoncor.gui.comboBox.ComboBoxRect();
        cd_fechaInicio = new com.toedter.calendar.JDateChooser();
        cd_fechaFin = new com.toedter.calendar.JDateChooser();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        labelMetric2 = new org.edisoncor.gui.label.LabelMetric();
        panelRoundTranslucido1 = new org.edisoncor.gui.panel.PanelRoundTranslucido();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRutas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ruta.jpeg"))); // NOI18N
        panelImage1.setInheritsPopupMenu(true);

        panelRoundTranslucidoComplete1.setBackground(new java.awt.Color(255, 255, 255));
        panelRoundTranslucidoComplete1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelRoundTranslucidoComplete1.setForeground(new java.awt.Color(255, 255, 255));
        panelRoundTranslucidoComplete1.setColorDeBorde(new java.awt.Color(204, 204, 204));
        panelRoundTranslucidoComplete1.setColorPrimario(new java.awt.Color(0, 0, 0));
        panelRoundTranslucidoComplete1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Código:");

        txt_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codigoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Estado:");

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha Inicio:");

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha Entrega:");

        txt_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_estadoActionPerformed(evt);
            }
        });

        bt_modificar.setText("Modificar");
        bt_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_modificarActionPerformed(evt);
            }
        });

        bt_crear.setText("Crear ");
        bt_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_crearActionPerformed(evt);
            }
        });

        bt_eliminar.setText("Eliminar");
        bt_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_eliminarActionPerformed(evt);
            }
        });

        bt_buscar.setText("Buscar");
        bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Empresa:");

        cb_Empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_EmpresaActionPerformed(evt);
            }
        });

        bt_limpiar.setText("Limpiar");
        bt_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_limpiarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ciudad:");

        cb_ciudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ciudadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRoundTranslucidoComplete1Layout = new javax.swing.GroupLayout(panelRoundTranslucidoComplete1);
        panelRoundTranslucidoComplete1.setLayout(panelRoundTranslucidoComplete1Layout);
        panelRoundTranslucidoComplete1Layout.setHorizontalGroup(
            panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(bt_crear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(bt_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(bt_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bt_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(bt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(351, 351, 351))))
            .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(90, 90, 90)
                        .addComponent(cb_Empresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(389, 389, 389))
                    .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                        .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cd_fechaInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_codigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_estado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cb_ciudad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cd_fechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(387, 387, 387))))
        );
        panelRoundTranslucidoComplete1Layout.setVerticalGroup(
            panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_estado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cd_fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cd_fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRoundTranslucidoComplete1Layout.createSequentialGroup()
                        .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cb_ciudad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(9, 9, 9))
                    .addComponent(cb_Empresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelRoundTranslucidoComplete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_crear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelMetric1.setForeground(new java.awt.Color(0, 204, 153));
        labelMetric1.setText("e-Shop");
        labelMetric1.setColorDeSombra(new java.awt.Color(204, 255, 204));
        labelMetric1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N

        labelMetric2.setText("Agregar Ruta:");
        labelMetric2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        jTableRutas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableRutas);

        javax.swing.GroupLayout panelRoundTranslucido1Layout = new javax.swing.GroupLayout(panelRoundTranslucido1);
        panelRoundTranslucido1.setLayout(panelRoundTranslucido1Layout);
        panelRoundTranslucido1Layout.setHorizontalGroup(
            panelRoundTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRoundTranslucido1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        panelRoundTranslucido1Layout.setVerticalGroup(
            panelRoundTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRoundTranslucido1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelMetric2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelRoundTranslucidoComplete1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImage1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(298, 298, 298))
                    .addComponent(panelRoundTranslucido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMetric2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelRoundTranslucidoComplete1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRoundTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigoActionPerformed

    private void bt_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_modificarActionPerformed
        if (txt_codigo.equals("") || txt_estado.equals("") || cd_fechaInicio.equals("")
            || cd_fechaInicio.equals("") || cb_ciudad.getSelectedIndex() < 1 || cb_Empresa.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(null, "Error con los datos");
        } else {
            if (ruta != null) {

                try {
                    ruta.setCodigo(Integer.parseInt(txt_codigo.getText()));
                    ruta.setEstado(txt_estado.getText());
                    ruta.setFechaInicioEntrega(cd_fechaInicio.getDate());
                    ruta.setFechaFinEntrega(cd_fechaFin.getDate());
                    ruta.setDepartamentoCodigo(departamentoCtr.findDepartamento(cb_ciudad.getSelectedIndex()));
                    ruta.setEmpresaCodigo(deliveryCtr.findDelivery(cb_Empresa.getSelectedIndex()));
                    rutaCtr.edit(ruta);

                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CrudUsuario_FRM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(CrudRuta_FRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_bt_modificarActionPerformed

    private void bt_crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_crearActionPerformed
        if (txt_codigo.equals("") || txt_estado.equals("") || cd_fechaInicio.equals("")
            || cd_fechaInicio.equals("") || cb_ciudad.getSelectedIndex() < 1 || cb_Empresa.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(null, "Error con los datos");
        } else {
            if (ruta != null) {

                try {
                    ruta.setCodigo(Integer.parseInt(txt_codigo.getText()));
                    ruta.setEstado(txt_estado.getText());
                    ruta.setFechaInicioEntrega(cd_fechaInicio.getDate());
                    ruta.setFechaFinEntrega(cd_fechaFin.getDate());
                    ruta.setDepartamentoCodigo(departamentoCtr.findDepartamento(cb_ciudad.getSelectedIndex()));
                    ruta.setEmpresaCodigo(deliveryCtr.findDelivery(cb_Empresa.getSelectedIndex()));
                    rutaCtr.create(ruta);

            } catch (Exception ex) {
                Logger.getLogger(CrudProducto_FRM.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_bt_crearActionPerformed
    }
    private void bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarActionPerformed
        try {
            int cod = Integer.parseInt(JOptionPane.showInputDialog("ingrese el codigo del usuario"));
            ruta = rutaCtr.findRuta(cod);

            if (ruta != null) {
                setRuta(ruta);
            }

        } catch (NumberFormatException | NullPointerException e) {

            JOptionPane.showMessageDialog(null, "ERROR! no se encontraron datos");
        }
    }//GEN-LAST:event_bt_buscarActionPerformed

    private void cb_EmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_EmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_EmpresaActionPerformed

    private void bt_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_limpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_bt_limpiarActionPerformed

    private void cb_ciudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ciudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ciudadActionPerformed

    private void bt_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_eliminarActionPerformed

    }//GEN-LAST:event_bt_eliminarActionPerformed

    private void txt_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_estadoActionPerformed

    public void setRuta(Ruta rut) {
        txt_codigo.setText(Integer.toString(rut.getCodigo()));
        txt_estado.setText(rut.getEstado());
        cb_Empresa.setSelectedIndex(rut.getEmpresaCodigo().getCodigo());
        cb_ciudad.setSelectedIndex(rut.getDepartamentoCodigo().getCodigo());
        cd_fechaInicio.setDate(rut.getFechaInicioEntrega());
        cd_fechaFin.setDate(rut.getFechaFinEntrega());
        
        
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
            java.util.logging.Logger.getLogger(Ruta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ruta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ruta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ruta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrudRuta_FRM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonRound bt_buscar;
    private org.edisoncor.gui.button.ButtonRound bt_crear;
    private org.edisoncor.gui.button.ButtonRound bt_eliminar;
    private org.edisoncor.gui.button.ButtonRound bt_limpiar;
    private org.edisoncor.gui.button.ButtonRound bt_modificar;
    private java.awt.Canvas canvas1;
    private org.edisoncor.gui.comboBox.ComboBoxRect cb_Empresa;
    private org.edisoncor.gui.comboBox.ComboBoxRect cb_ciudad;
    private com.toedter.calendar.JDateChooser cd_fechaFin;
    private com.toedter.calendar.JDateChooser cd_fechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRutas;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelMetric labelMetric2;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    private org.edisoncor.gui.panel.PanelRoundTranslucido panelRoundTranslucido1;
    private org.edisoncor.gui.panel.PanelRoundTranslucidoComplete panelRoundTranslucidoComplete1;
    private org.edisoncor.gui.textField.TextFieldRectIcon txt_codigo;
    private org.edisoncor.gui.textField.TextFieldRectIcon txt_estado;
    // End of variables declaration//GEN-END:variables
private void limpiar() {
        txt_codigo.setText("");
        txt_estado.setText("");
        cd_fechaInicio.setDate(null);
        cd_fechaFin.setDate(null);
        getComboBoxEmpresa();
        cb_Empresa.setSelectedIndex(0);
        getComboBox();
        cb_ciudad.setSelectedIndex(0);
        
        ruta = null;        

    }
}
