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
 * @author EQUIPO
 */
@Entity
@Table(name = "comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Comentario.findByCodigo", query = "SELECT c FROM Comentario c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Comentario.findByCalificacion", query = "SELECT c FROM Comentario c WHERE c.calificacion = :calificacion")
    , @NamedQuery(name = "Comentario.findByFechaComentario", query = "SELECT c FROM Comentario c WHERE c.fechaComentario = :fechaComentario")
    , @NamedQuery(name = "Comentario.findByMensaje", query = "SELECT c FROM Comentario c WHERE c.mensaje = :mensaje")
    , @NamedQuery(name = "Comentario.findByRespuesta", query = "SELECT c FROM Comentario c WHERE c.respuesta = :respuesta")})
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "calificacion")
    private int calificacion;
    @Basic(optional = false)
    @Column(name = "fecha_comentario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComentario;
    @Basic(optional = false)
    @Column(name = "mensaje")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "respuesta")
    private String respuesta;
    @JoinColumn(name = "productoc_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Producto productocCodigo;
    @JoinColumn(name = "user_coment_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario userComentCodigo;

    public Comentario() {
    }

    public Comentario(Integer codigo) {
        this.codigo = codigo;
    }

    public Comentario(Integer codigo, int calificacion, Date fechaComentario, String mensaje, String respuesta) {
        this.codigo = codigo;
        this.calificacion = calificacion;
        this.fechaComentario = fechaComentario;
        this.mensaje = mensaje;
        this.respuesta = respuesta;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Producto getProductocCodigo() {
        return productocCodigo;
    }

    public void setProductocCodigo(Producto productocCodigo) {
        this.productocCodigo = productocCodigo;
    }

    public Usuario getUserComentCodigo() {
        return userComentCodigo;
    }

    public void setUserComentCodigo(Usuario userComentCodigo) {
        this.userComentCodigo = userComentCodigo;
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
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Comentario[ codigo=" + codigo + " ]";
    }
    
}
