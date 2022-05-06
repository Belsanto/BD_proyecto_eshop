/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshop_bd;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EQUIPO
 */
@Entity
@Table(name = "detalle_entrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleEntrega.findAll", query = "SELECT d FROM DetalleEntrega d")
    , @NamedQuery(name = "DetalleEntrega.findByCodigo", query = "SELECT d FROM DetalleEntrega d WHERE d.codigo = :codigo")
    , @NamedQuery(name = "DetalleEntrega.findByEstado", query = "SELECT d FROM DetalleEntrega d WHERE d.estado = :estado")
    , @NamedQuery(name = "DetalleEntrega.findByPrecio", query = "SELECT d FROM DetalleEntrega d WHERE d.precio = :precio")})
public class DetalleEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "precio")
    private double precio;
    @OneToMany(mappedBy = "detalleEntregaCodigo")
    private List<Compra> compraList;
    @OneToMany(mappedBy = "detalleEntregaCodigo")
    private List<Canje> canjeList;
    @JoinColumn(name = "compra_entregar_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Compra compraEntregarCodigo;
    @JoinColumn(name = "ruta_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Ruta rutaCodigo;
    @JoinColumn(name = "canje_entregar_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Canje canjeEntregarCodigo;

    public DetalleEntrega() {
    }

    public DetalleEntrega(Integer codigo) {
        this.codigo = codigo;
    }

    public DetalleEntrega(Integer codigo, String estado, double precio) {
        this.codigo = codigo;
        this.estado = estado;
        this.precio = precio;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<Canje> getCanjeList() {
        return canjeList;
    }

    public void setCanjeList(List<Canje> canjeList) {
        this.canjeList = canjeList;
    }

    public Compra getCompraEntregarCodigo() {
        return compraEntregarCodigo;
    }

    public void setCompraEntregarCodigo(Compra compraEntregarCodigo) {
        this.compraEntregarCodigo = compraEntregarCodigo;
    }

    public Ruta getRutaCodigo() {
        return rutaCodigo;
    }

    public void setRutaCodigo(Ruta rutaCodigo) {
        this.rutaCodigo = rutaCodigo;
    }

    public Canje getCanjeEntregarCodigo() {
        return canjeEntregarCodigo;
    }

    public void setCanjeEntregarCodigo(Canje canjeEntregarCodigo) {
        this.canjeEntregarCodigo = canjeEntregarCodigo;
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
        if (!(object instanceof DetalleEntrega)) {
            return false;
        }
        DetalleEntrega other = (DetalleEntrega) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.DetalleEntrega[ codigo=" + codigo + " ]";
    }
    
}
