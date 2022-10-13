package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ProductoServiceImp implements IProductoService{
    private IProductoService productoService;
    @PersistenceContext
    private EntityManager entity;


    @Transactional(readOnly = true)
    @Override
    public List<Producto> findAll() {

        return entity.createQuery("from Producto").getResultList();
    }

    @Transactional
    @Override
    public void save(Producto producto) {
        if(producto.getCodigop()!= null && producto.getCodigop()>0){
            entity.merge(producto);
        }else{
            entity.persist(producto);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findOne(Long id) {

        return entity.find(Producto.class,id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        entity.remove(findOne(id));
    }
}
