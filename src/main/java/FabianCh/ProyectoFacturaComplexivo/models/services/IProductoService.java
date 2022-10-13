package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> findAll();
    public void save(Producto producto);
    public Producto findOne(Long id);
    public void delete(Long id);
}
