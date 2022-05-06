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
import javax.persistence.ManyToMany;
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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByCodigo", query = "SELECT p FROM Producto p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Producto.findByDescuento", query = "SELECT p FROM Producto p WHERE p.descuento = :descuento")
    , @NamedQuery(name = "Producto.findByFechaLimite", query = "SELECT p FROM Producto p WHERE p.fechaLimite = :fechaLimite")
    , @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Producto.findByNombrePublicacion", query = "SELECT p FROM Producto p WHERE p.nombrePublicacion = :nombrePublicacion")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByUnidades", query = "SELECT p FROM Producto p WHERE p.unidades = :unidades")
    , @NamedQuery(name = "Producto.findByValorEnPuntos", query = "SELECT p FROM Producto p WHERE p.valorEnPuntos = :valorEnPuntos")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
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
    @Basic(optional = false)
    @Column(name = "precio")
    private float precio;
    @Basic(optional = false)
    @Column(name = "unidades")
    private int unidades;
    @Basic(optional = false)
    @Column(name = "valor_en_puntos")
    private int valorEnPuntos;
    @ManyToMany(mappedBy = "productoList")
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "productoCanjeCodigo")
    private List<DetalleCanje> detalleCanjeList;
    @JoinColumn(name = "ciudad_producto_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Ciudad ciudadProductoCodigo;
    @JoinColumn(name = "vendedor_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario vendedorCodigo;
    @OneToMany(mappedBy = "productocCodigo")
    private List<Comentario> comentarioList;
    @OneToMany(mappedBy = "productoCompraCodigo")
    private List<DetalleCompra> detalleCompraList;

    public Producto() {
    }

    public Producto(String codigo) {
        this.codigo = codigo;
    }

    public Producto(String codigo, String descripcion, float descuento, Date fechaLimite, String nombre, String nombrePublicacion, float precio, int unidades, int valorEnPuntos) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.fechaLimite = fechaLimite;
        this.nombre = nombre;
        this.nombrePublicacion = nombrePublicacion;
        this.precio = precio;
        this.unidades = unidades;
        this.valorEnPuntos = valorEnPuntos;
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

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getValorEnPuntos() {
        return valorEnPuntos;
    }

    public void setValorEnPuntos(int valorEnPuntos) {
        this.valorEnPuntos = valorEnPuntos;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<DetalleCanje> getDetalleCanjeList() {
        return detalleCanjeList;
    }

    public void setDetalleCanjeList(List<DetalleCanje> detalleCanjeList) {
        this.detalleCanjeList = detalleCanjeList;
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
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @XmlTransient
    public List<DetalleCompra> getDetalleCompraList() {
        return detalleCompraList;
    }

    public void setDetalleCompraList(List<DetalleCompra> detalleCompraList) {
        this.detalleCompraList = detalleCompraList;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Producto[ codigo=" + codigo + " ]";
    }
    
}
