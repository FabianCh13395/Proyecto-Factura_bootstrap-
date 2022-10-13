package FabianCh.ProyectoFacturaComplexivo.models.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;

@Component
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigop;

    @NotEmpty
    @Size(max=50)
    private String nombrep;

    @NotEmpty
    @Size(max = 250)
    private String descripcion;
    @NotNull
    @Min(1)
    @Max(500)
    private Integer stock;
    @NotNull
    private Double precio;

    public Producto(Long codigop, String nombrep, String descripcion, Integer stock, Double precio) {
        this.codigop = codigop;
        this.nombrep = nombrep;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
    }

    public Producto() {
    }

    public Long getCodigop() {
        return codigop;
    }

    public void setCodigop(Long codigop) {
        this.codigop = codigop;
    }

    public String getNombrep() {
        return nombrep;
    }

    public void setNombrep(String nombrep) {
        this.nombrep = nombrep;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
