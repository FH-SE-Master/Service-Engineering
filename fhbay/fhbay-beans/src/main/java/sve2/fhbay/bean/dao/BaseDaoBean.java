package sve2.fhbay.bean.dao;

import sve2.fhbay.dao.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
public abstract class BaseDaoBean<T, I extends Serializable> implements BaseDao<T, I> {

    @PersistenceContext(unitName = "FhBayDb")
    private EntityManager em;

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDaoBean() {
        entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public void merge(T entity) {
        em.merge(entity);
    }

    public T findById(Long id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        return em.createQuery(String.format("SELECT e FROM %s e", entityClass.getSimpleName()), entityClass).getResultList();
    }


    @Override
    public void deleteAll() {
        findAll().forEach(em::remove);
    }

    protected EntityManager getEm() {
        return em;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
