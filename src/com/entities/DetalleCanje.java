/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EQUIPO
 */
@Entity
@Table(name = "detalle_canje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCanje.findAll", query = "SELECT d FROM DetalleCanje d")
    , @NamedQuery(name = "DetalleCanje.findByCodigo", query = "SELECT d FROM DetalleCanje d WHERE d.codigo = :codigo")
    , @NamedQuery(name = "DetalleCanje.findBySubTotal", query = "SELECT d FROM DetalleCanje d WHERE d.subTotal = :subTotal")})
public class DetalleCanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "sub_total")
    private double subTotal;
    @JoinColumn(name = "canje_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Canje canjeCodigo;
    @JoinColumn(name = "producto_canje_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Producto productoCanjeCodigo;

    public DetalleCanje() {
    }

    public DetalleCanje(Integer codigo) {
        this.codigo = codigo;
    }

    public DetalleCanje(Integer codigo, double subTotal) {
        this.codigo = codigo;
        this.subTotal = subTotal;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Canje getCanjeCodigo() {
        return canjeCodigo;
    }

    public void setCanjeCodigo(Canje canjeCodigo) {
        this.canjeCodigo = canjeCodigo;
    }

    public Producto getProductoCanjeCodigo() {
        return productoCanjeCodigo;
    }

    public void setProductoCanjeCodigo(Producto productoCanjeCodigo) {
        this.productoCanjeCodigo = productoCanjeCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCanje)) {
            return false;
        }
        DetalleCanje other = (DetalleCanje) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.DetalleCanje[ codigo=" + codigo + " ]";
    }
    
}
