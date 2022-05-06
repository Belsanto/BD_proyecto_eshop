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
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
    , @NamedQuery(name = "Compra.findByCodigo", query = "SELECT c FROM Compra c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Compra.findByFechaCompra", query = "SELECT c FROM Compra c WHERE c.fechaCompra = :fechaCompra")
    , @NamedQuery(name = "Compra.findByMedioPago", query = "SELECT c FROM Compra c WHERE c.medioPago = :medioPago")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompra;
    @Basic(optional = false)
    @Column(name = "medio_pago")
    private String medioPago;
    @JoinColumn(name = "comprador_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario compradorCodigo;
    @JoinColumn(name = "detalle_entrega_codigo", referencedColumnName = "codigo")
    @ManyToOne
    private DetalleEntrega detalleEntregaCodigo;
    @OneToMany(mappedBy = "compraCodigo")
    private List<DetalleCompra> detalleCompraList;
    @OneToMany(mappedBy = "compraEntregarCodigo")
    private List<DetalleEntrega> detalleEntregaList;

    public Compra() {
    }

    public Compra(Integer codigo) {
        this.codigo = codigo;
    }

    public Compra(Integer codigo, Date fechaCompra, String medioPago) {
        this.codigo = codigo;
        this.fechaCompra = fechaCompra;
        this.medioPago = medioPago;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public Usuario getCompradorCodigo() {
        return compradorCodigo;
    }

    public void setCompradorCodigo(Usuario compradorCodigo) {
        this.compradorCodigo = compradorCodigo;
    }

    public DetalleEntrega getDetalleEntregaCodigo() {
        return detalleEntregaCodigo;
    }

    public void setDetalleEntregaCodigo(DetalleEntrega detalleEntregaCodigo) {
        this.detalleEntregaCodigo = detalleEntregaCodigo;
    }

    @XmlTransient
    public List<DetalleCompra> getDetalleCompraList() {
        return detalleCompraList;
    }

    public void setDetalleCompraList(List<DetalleCompra> detalleCompraList) {
        this.detalleCompraList = detalleCompraList;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eshop_bd.Compra[ codigo=" + codigo + " ]";
    }
    
}
