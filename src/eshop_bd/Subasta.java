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
@Table(name = "subasta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subasta.findAll", query = "SELECT s FROM Subasta s")
    , @NamedQuery(name = "Subasta.findByCodigo", query = "SELECT s FROM Subasta s WHERE s.codigo = :codigo")
    , @NamedQuery(name = "Subasta.findByFechaLimite", query = "SELECT s FROM Subasta s WHERE s.fechaLimite = :fechaLimite")
    , @NamedQuery(name = "Subasta.findByPuja", query = "SELECT s FROM Subasta s WHERE s.puja = :puja")})
public class Subasta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "fecha_limite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLimite;
    @Basic(optional = false)
    @Column(name = "puja")
    private float puja;
    @OneToMany(mappedBy = "subastaProductoCodigo")
    private List<SubastaUsuario> subastaUsuarioList;
    @JoinColumn(name = "producto_en_subasta_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private ProductoSubasta productoEnSubastaCodigo;

    public Subasta() {
    }

    public Subasta(Integer codigo) {
        this.codigo = codigo;
    }

    public Subasta(Integer codigo, Date fechaLimite, float puja) {
        this.codigo = codigo;
        this.fechaLimite = fechaLimite;
        this.puja = puja;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public float getPuja() {
        return puja;
    }

    public void setPuja(float puja) {
        this.puja = puja;
    }

    @XmlTransient
    public List<SubastaUsuario> getSubastaUsuarioList() {
        return subastaUsuarioList;
    }

    public void setSubastaUsuarioList(List<SubastaUsuario> subastaUsuarioList) {
        this.subastaUsuarioList = subastaUsuarioList;
    }

    public ProductoSubasta getProductoEnSubastaCodigo() {
        return productoEnSubastaCodigo;
    }

    public void setProductoEnSubastaCodigo(ProductoSubasta productoEnSubastaCodigo) {
        this.productoEnSubastaCodigo = productoEnSubastaCodigo;
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
        if (!(object instanceof Subasta)) {
            return false;
        }
        Subasta other = (Subasta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Subasta[ codigo=" + codigo + " ]";
    }
    
}
