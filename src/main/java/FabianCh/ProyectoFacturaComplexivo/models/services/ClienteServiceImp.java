package FabianCh.ProyectoFacturaComplexivo.models.services;

import FabianCh.ProyectoFacturaComplexivo.models.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import java.util.List;

@Service
public class ClienteServiceImp implements IClienteService{
    private IClienteService clienteService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return entityManager.createQuery("from Cliente").getResultList();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {

        if(cliente.getCodigo()!= null && cliente.getCodigo()>0){
            entityManager.merge(cliente);
        }else{
            entityManager.persist(cliente);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findOne(Long id) {
        return entityManager.find(Cliente.class,id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(findOne(id));
    }
}
