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
@Table(name = "producto_subasta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoSubasta.findAll", query = "SELECT p FROM ProductoSubasta p")
    , @NamedQuery(name = "ProductoSubasta.findByCodigo", query = "SELECT p FROM ProductoSubasta p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "ProductoSubasta.findByDescripcion", query = "SELECT p FROM ProductoSubasta p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "ProductoSubasta.findByFechaLimite", query = "SELECT p FROM ProductoSubasta p WHERE p.fechaLimite = :fechaLimite")
    , @NamedQuery(name = "ProductoSubasta.findByNombre", query = "SELECT p FROM ProductoSubasta p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "ProductoSubasta.findByNombrePublicacion", query = "SELECT p FROM ProductoSubasta p WHERE p.nombrePublicacion = :nombrePublicacion")})
public class ProductoSubasta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_limite")
    @Temporal(TemporalType.DATE)
    private Date fechaLimite;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "nombre_publicacion")
    private String nombrePublicacion;
    @JoinColumn(name = "ciudad_producto_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Ciudad ciudadProductoCodigo;
    @JoinColumn(name = "vendedor_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario vendedorCodigo;
    @OneToMany(mappedBy = "productoEnSubastaCodigo")
    private List<Subasta> subastaList;

    public ProductoSubasta() {
    }

    public ProductoSubasta(String codigo) {
        this.codigo = codigo;
    }

    public ProductoSubasta(String codigo, String descripcion, Date fechaLimite, String nombre, String nombrePublicacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.nombre = nombre;
        this.nombrePublicacion = nombrePublicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrePublicacion() {
        return nombrePublicacion;
    }

    public void setNombrePublicacion(String nombrePublicacion) {
        this.nombrePublicacion = nombrePublicacion;
    }

    public Ciudad getCiudadProductoCodigo() {
        return ciudadProductoCodigo;
    }

    public void setCiudadProductoCodigo(Ciudad ciudadProductoCodigo) {
        this.ciudadProductoCodigo = ciudadProductoCodigo;
    }

    public Usuario getVendedorCodigo() {
        return vendedorCodigo;
    }

    public void setVendedorCodigo(Usuario vendedorCodigo) {
        this.vendedorCodigo = vendedorCodigo;
    }

    @XmlTransient
    public List<Subasta> getSubastaList() {
        return subastaList;
    }

    public void setSubastaList(List<Subasta> subastaList) {
        this.subastaList = subastaList;
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
        if (!(object instanceof ProductoSubasta)) {
            return false;
        }
        ProductoSubasta other = (ProductoSubasta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.ProductoSubasta[ codigo=" + codigo + " ]";
    }
    
}
