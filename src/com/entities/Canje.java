/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EQUIPO
 */
@Entity
@Table(name = "canje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canje.findAll", query = "SELECT c FROM Canje c")
    , @NamedQuery(name = "Canje.findByCodigo", query = "SELECT c FROM Canje c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Canje.findByFechaCanje", query = "SELECT c FROM Canje c WHERE c.fechaCanje = :fechaCanje")})
public class Canje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "fecha_canje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCanje;
    @OneToMany(mappedBy = "canjeCodigo")
    private List<DetalleCanje> detalleCanjeList;
    @JoinColumn(name = "cliente_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario clienteCodigo;
    @JoinColumn(name = "detalle_entrega_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private DetalleEntrega detalleEntregaCodigo;
    @OneToMany(mappedBy = "canjeEntregarCodigo")
    private List<DetalleEntrega> detalleEntregaList;

    public Canje() {
    }

    public Canje(Integer codigo) {
        this.codigo = codigo;
    }

    public Canje(Integer codigo, Date fechaCanje) {
        this.codigo = codigo;
        this.fechaCanje = fechaCanje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCanje() {
        return fechaCanje;
    }

    public void setFechaCanje(Date fechaCanje) {
        this.fechaCanje = fechaCanje;
    }

    @XmlTransient
    public List<DetalleCanje> getDetalleCanjeList() {
        return detalleCanjeList;
    }

    public void setDetalleCanjeList(List<DetalleCanje> detalleCanjeList) {
        this.detalleCanjeList = detalleCanjeList;
    }

    public Usuario getClienteCodigo() {
        return clienteCodigo;
    }

    public void setClienteCodigo(Usuario clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public DetalleEntrega getDetalleEntregaCodigo() {
        return detalleEntregaCodigo;
    }

    public void setDetalleEntregaCodigo(DetalleEntrega detalleEntregaCodigo) {
        this.detalleEntregaCodigo = detalleEntregaCodigo;
    }

    @XmlTransient
    public List<DetalleEntrega> getDetalleEntregaList() {
        return detalleEntregaList;
    }

    public void setDetalleEntregaList(List<DetalleEntrega> detalleEntregaList) {
        this.detalleEntregaList = detalleEntregaList;
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
        if (!(object instanceof Canje)) {
            return false;
        }
        Canje other = (Canje) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Canje[ codigo=" + codigo + " ]";
    }
    
}
