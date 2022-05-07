/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "cartera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cartera.findAll", query = "SELECT c FROM Cartera c")
    , @NamedQuery(name = "Cartera.findByCodigo", query = "SELECT c FROM Cartera c WHERE c.codigo = :codigo")})
public class Cartera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @OneToMany(mappedBy = "carteraPuntosCodigo")
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carteraCodigo")
    private List<Puntos> puntosList;
    @JoinColumn(name = "usuarios_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario usuariosCodigo;

    public Cartera() {
    }

    public Cartera(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Puntos> getPuntosList() {
        return puntosList;
    }

    public void setPuntosList(List<Puntos> puntosList) {
        this.puntosList = puntosList;
    }

    public Usuario getUsuariosCodigo() {
        return usuariosCodigo;
    }

    public void setUsuariosCodigo(Usuario usuariosCodigo) {
        this.usuariosCodigo = usuariosCodigo;
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
        if (!(object instanceof Cartera)) {
            return false;
        }
        Cartera other = (Cartera) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Cartera[ codigo=" + codigo + " ]";
    }
    
}
