package FabianCh.ProyectoFacturaComplexivo.models.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Entity
@Table(name = "encabezado")
public class EncabezadoFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoe;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechac;

    private Double total;
    @ManyToOne
    private Cliente cliente;

    @Transient
    public List<DetalleF> items=new ArrayList<>();

    public EncabezadoFactura() {
        this.total=0.0;
        this.fechac=new Date();
    }

    public List<DetalleF> getItems() {
        return items;
    }

    public void setItems(List<DetalleF> items) {
        this.items = items;
    }

    public Long getCodigoe() {
        return codigoe;
    }

    public void setCodigoe(Long codigoe) {
        this.codigoe = codigoe;
    }

    public Date getFechac() {
        return fechac;
    }

    public void setFechac(Date fechac) {
        this.fechac = fechac;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
