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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByCodigo", query = "SELECT u FROM Usuario u WHERE u.codigo = :codigo")
    , @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByRutaFoto", query = "SELECT u FROM Usuario u WHERE u.rutaFoto = :rutaFoto")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "ruta_foto")
    private String rutaFoto;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @JoinTable(name = "usuario_productos_favoritos", joinColumns = {
        @JoinColumn(name = "usuarios_potenciales_codigo", referencedColumnName = "codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "productos_favoritos_codigo", referencedColumnName = "codigo")})
    @ManyToMany
    private List<Producto> productoList;
    @OneToMany(mappedBy = "compradorCodigo")
    private List<Compra> compraList;
    @OneToMany(mappedBy = "vendedorCodigo")
    private List<ProductoSubasta> productoSubastaList;
    @OneToMany(mappedBy = "clienteCodigo")
    private List<Canje> canjeList;
    @OneToMany(mappedBy = "vendedorCodigo")
    private List<Producto> productoList1;
    @OneToMany(mappedBy = "userComentCodigo")
    private List<Comentario> comentarioList;
    @OneToMany(mappedBy = "usuarioSubastaCodigo")
    private List<SubastaUsuario> subastaUsuarioList;
    @JoinColumn(name = "ciudad_usuario_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Ciudad ciudadUsuarioCodigo;

    public Usuario() {
    }

    public Usuario(Integer codigo) {
        this.codigo = codigo;
    }

    public Usuario(Integer codigo, String email, String nombre, String password, String username) {
        this.codigo = codigo;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.username = username;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<ProductoSubasta> getProductoSubastaList() {
        return productoSubastaList;
    }

    public void setProductoSubastaList(List<ProductoSubasta> productoSubastaList) {
        this.productoSubastaList = productoSubastaList;
    }

    @XmlTransient
    public List<Canje> getCanjeList() {
        return canjeList;
    }

    public void setCanjeList(List<Canje> canjeList) {
        this.canjeList = canjeList;
    }

    @XmlTransient
    public List<Producto> getProductoList1() {
        return productoList1;
    }

    public void setProductoList1(List<Producto> productoList1) {
        this.productoList1 = productoList1;
    }

    @XmlTransient
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @XmlTransient
    public List<SubastaUsuario> getSubastaUsuarioList() {
        return subastaUsuarioList;
    }

    public void setSubastaUsuarioList(List<SubastaUsuario> subastaUsuarioList) {
        this.subastaUsuarioList = subastaUsuarioList;
    }

    public Ciudad getCiudadUsuarioCodigo() {
        return ciudadUsuarioCodigo;
    }

    public void setCiudadUsuarioCodigo(Ciudad ciudadUsuarioCodigo) {
        this.ciudadUsuarioCodigo = ciudadUsuarioCodigo;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Usuario[ codigo=" + codigo + " ]";
    }
    
}
