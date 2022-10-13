package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.DetalleF;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class DetalleServiceImp implements IDetalleService{
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    @Override
    public List<DetalleF> findAll() {
        return em.createQuery("from DetalleF").getResultList();
    }

    @Transactional
    @Override
    public void save(DetalleF detalleF) {
        if(detalleF.getCodigoD()!=null && detalleF.getCodigoD()>0){
            em.merge(detalleF);
        }else{
            em.persist(detalleF);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DetalleF findOne(Long id) {
        return em.find(DetalleF.class,id);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        em.remove(findOne(id));
    }
}
