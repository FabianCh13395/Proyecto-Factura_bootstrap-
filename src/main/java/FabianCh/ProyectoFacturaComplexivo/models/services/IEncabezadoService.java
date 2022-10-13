package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.EncabezadoFactura;

import java.util.List;

public interface IEncabezadoService {
    public List<EncabezadoFactura> findAll();

    public EncabezadoFactura save(EncabezadoFactura factura);

    public EncabezadoFactura findOne(Long id);

    public void delete(Long id);
}
