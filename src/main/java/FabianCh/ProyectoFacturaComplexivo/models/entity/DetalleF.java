package FabianCh.ProyectoFacturaComplexivo.models.entity;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "detalle")
public class DetalleF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoD;

    @NotNull
    @Min(1)
    @Max(1000)
    private Integer cantidad;

    @NotNull
    private Double subtotal;
    @ManyToOne
    @JoinColumn(name = "codigoe")
    private EncabezadoFactura factura;

    @ManyToOne
    @JoinColumn(name = "codigop")
    private Producto producto;

    public DetalleF() {
        this.setSubtotal(0.0);
    }

    public Long getCodigoD() {
        return codigoD;
    }

    public void setCodigoD(Long codigoD) {
        this.codigoD = codigoD;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public EncabezadoFactura getFactura() {
        return factura;
    }

    public void setFactura(EncabezadoFactura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
