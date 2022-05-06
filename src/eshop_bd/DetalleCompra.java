/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshop_bd;

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
@Table(name = "detalle_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompra.findAll", query = "SELECT d FROM DetalleCompra d")
    , @NamedQuery(name = "DetalleCompra.findByCodigo", query = "SELECT d FROM DetalleCompra d WHERE d.codigo = :codigo")
    , @NamedQuery(name = "DetalleCompra.findBySubTotal", query = "SELECT d FROM DetalleCompra d WHERE d.subTotal = :subTotal")
    , @NamedQuery(name = "DetalleCompra.findByUnidades", query = "SELECT d FROM DetalleCompra d WHERE d.unidades = :unidades")})
public class DetalleCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "sub_total")
    private double subTotal;
    @Basic(optional = false)
    @Column(name = "unidades")
    private int unidades;
    @JoinColumn(name = "producto_compra_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Producto productoCompraCodigo;
    @JoinColumn(name = "compra_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Compra compraCodigo;

    public DetalleCompra() {
    }

    public DetalleCompra(Integer codigo) {
        this.codigo = codigo;
    }

    public DetalleCompra(Integer codigo, double subTotal, int unidades) {
        this.codigo = codigo;
        this.subTotal = subTotal;
        this.unidades = unidades;
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

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public Producto getProductoCompraCodigo() {
        return productoCompraCodigo;
    }

    public void setProductoCompraCodigo(Producto productoCompraCodigo) {
        this.productoCompraCodigo = productoCompraCodigo;
    }

    public Compra getCompraCodigo() {
        return compraCodigo;
    }

    public void setCompraCodigo(Compra compraCodigo) {
        this.compraCodigo = compraCodigo;
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
        if (!(object instanceof DetalleCompra)) {
            return false;
        }
        DetalleCompra other = (DetalleCompra) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.DetalleCompra[ codigo=" + codigo + " ]";
    }
    
}
