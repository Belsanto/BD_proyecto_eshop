/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "puntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puntos.findAll", query = "SELECT p FROM Puntos p")
    , @NamedQuery(name = "Puntos.findByCodigo", query = "SELECT p FROM Puntos p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Puntos.findByCantidadDisponible", query = "SELECT p FROM Puntos p WHERE p.cantidadDisponible = :cantidadDisponible")
    , @NamedQuery(name = "Puntos.findByCantidadUsada", query = "SELECT p FROM Puntos p WHERE p.cantidadUsada = :cantidadUsada")
    , @NamedQuery(name = "Puntos.findByEstado", query = "SELECT p FROM Puntos p WHERE p.estado = :estado")
    , @NamedQuery(name = "Puntos.findByFechaInicio", query = "SELECT p FROM Puntos p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Puntos.findByFechaLimite", query = "SELECT p FROM Puntos p WHERE p.fechaLimite = :fechaLimite")})
public class Puntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "cantidad_disponible")
    private float cantidadDisponible;
    @Basic(optional = false)
    @Column(name = "cantidad_usada")
    private float cantidadUsada;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_limite")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @JoinColumn(name = "cartera_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Cartera carteraCodigo;

    public Puntos() {
    }

    public Puntos(Integer codigo) {
        this.codigo = codigo;
    }

    public Puntos(Integer codigo, float cantidadDisponible, float cantidadUsada, String estado, Date fechaInicio, Date fechaLimite) {
        this.codigo = codigo;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadUsada = cantidadUsada;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaLimite = fechaLimite;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public float getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(float cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public float getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(float cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Cartera getCarteraCodigo() {
        return carteraCodigo;
    }

    public void setCarteraCodigo(Cartera carteraCodigo) {
        this.carteraCodigo = carteraCodigo;
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
        if (!(object instanceof Puntos)) {
            return false;
        }
        Puntos other = (Puntos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Puntos[ codigo=" + codigo + " ]";
    }
    
}
