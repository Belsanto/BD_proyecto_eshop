/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshop_bd;

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
@Table(name = "subasta_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubastaUsuario.findAll", query = "SELECT s FROM SubastaUsuario s")
    , @NamedQuery(name = "SubastaUsuario.findByCodigo", query = "SELECT s FROM SubastaUsuario s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "SubastaUsuario.findByFechaSubasta", query = "SELECT s FROM SubastaUsuario s WHERE s.fechaSubasta = :fechaSubasta")
    , @NamedQuery(name = "SubastaUsuario.findByValor", query = "SELECT s FROM SubastaUsuario s WHERE s.valor = :valor")})
public class SubastaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "fecha_subasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSubasta;
    @Basic(optional = false)
    @Column(name = "valor")
    private float valor;
    @JoinColumn(name = "usuario_subasta_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario usuarioSubastaCodigo;
    @JoinColumn(name = "subasta_producto_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Subasta subastaProductoCodigo;

    public SubastaUsuario() {
    }

    public SubastaUsuario(Integer codigo) {
        this.codigo = codigo;
    }

    public SubastaUsuario(Integer codigo, Date fechaSubasta, float valor) {
        this.codigo = codigo;
        this.fechaSubasta = fechaSubasta;
        this.valor = valor;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaSubasta() {
        return fechaSubasta;
    }

    public void setFechaSubasta(Date fechaSubasta) {
        this.fechaSubasta = fechaSubasta;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Usuario getUsuarioSubastaCodigo() {
        return usuarioSubastaCodigo;
    }

    public void setUsuarioSubastaCodigo(Usuario usuarioSubastaCodigo) {
        this.usuarioSubastaCodigo = usuarioSubastaCodigo;
    }

    public Subasta getSubastaProductoCodigo() {
        return subastaProductoCodigo;
    }

    public void setSubastaProductoCodigo(Subasta subastaProductoCodigo) {
        this.subastaProductoCodigo = subastaProductoCodigo;
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
        if (!(object instanceof SubastaUsuario)) {
            return false;
        }
        SubastaUsuario other = (SubastaUsuario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.SubastaUsuario[ codigo=" + codigo + " ]";
    }
    
}
