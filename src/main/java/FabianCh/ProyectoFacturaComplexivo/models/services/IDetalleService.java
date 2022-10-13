package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.DetalleF;

import java.util.List;

public interface IDetalleService {
    public List<DetalleF> findAll();
    public void save(DetalleF detalleF);
    public DetalleF findOne(Long id);
    public void delete(Long id);
}
