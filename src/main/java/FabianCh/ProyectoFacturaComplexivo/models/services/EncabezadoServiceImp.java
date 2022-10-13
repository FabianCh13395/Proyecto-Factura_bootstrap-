package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.EncabezadoFactura;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class EncabezadoServiceImp implements IEncabezadoService{
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<EncabezadoFactura> findAll() {
        return em.createQuery("from EncabezadoFactura").getResultList();
    }

    @Override
    @Transactional
    public EncabezadoFactura save(EncabezadoFactura factura) {
        if(factura.getCodigoe()!=null && factura.getCodigoe()>0){
            em.merge(factura);
        }else{
            em.persist(factura);
            Long id=factura.getCodigoe();
            return factura;
        }
        return factura;
    }
    @Transactional(readOnly = true)
    @Override
    public EncabezadoFactura findOne(Long id) {
        return em.find(EncabezadoFactura.class,id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        em.remove(findOne(id));
    }
}
