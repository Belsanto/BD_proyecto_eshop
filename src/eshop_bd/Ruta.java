/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshop_bd;

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
@Table(name = "ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ruta.findAll", query = "SELECT r FROM Ruta r")
    , @NamedQuery(name = "Ruta.findByCodigo", query = "SELECT r FROM Ruta r WHERE r.codigo = :codigo")
    , @NamedQuery(name = "Ruta.findByEstado", query = "SELECT r FROM Ruta r WHERE r.estado = :estado")
    , @NamedQuery(name = "Ruta.findByFechaFinEntrega", query = "SELECT r FROM Ruta r WHERE r.fechaFinEntrega = :fechaFinEntrega")
    , @NamedQuery(name = "Ruta.findByFechaInicioEntrega", query = "SELECT r FROM Ruta r WHERE r.fechaInicioEntrega = :fechaInicioEntrega")})
public class Ruta implements Serializable {

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
    @Column(name = "fecha_fin_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinEntrega;
    @Basic(optional = false)
    @Column(name = "fecha_inicio_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioEntrega;
    @JoinColumn(name = "empresa_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Delivery empresaCodigo;
    @OneToMany(mappedBy = "rutaCodigo")
    private List<DetalleEntrega> detalleEntregaList;

    public Ruta() {
    }

    public Ruta(Integer codigo) {
        this.codigo = codigo;
    }

    public Ruta(Integer codigo, String estado, Date fechaFinEntrega, Date fechaInicioEntrega) {
        this.codigo = codigo;
        this.estado = estado;
        this.fechaFinEntrega = fechaFinEntrega;
        this.fechaInicioEntrega = fechaInicioEntrega;
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

    public Date getFechaFinEntrega() {
        return fechaFinEntrega;
    }

    public void setFechaFinEntrega(Date fechaFinEntrega) {
        this.fechaFinEntrega = fechaFinEntrega;
    }

    public Date getFechaInicioEntrega() {
        return fechaInicioEntrega;
    }

    public void setFechaInicioEntrega(Date fechaInicioEntrega) {
        this.fechaInicioEntrega = fechaInicioEntrega;
    }

    public Delivery getEmpresaCodigo() {
        return empresaCodigo;
    }

    public void setEmpresaCodigo(Delivery empresaCodigo) {
        this.empresaCodigo = empresaCodigo;
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
        if (!(object instanceof Ruta)) {
            return false;
        }
        Ruta other = (Ruta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Ruta[ codigo=" + codigo + " ]";
    }
    
}
