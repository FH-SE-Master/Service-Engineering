package sve2.fhbay.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/07/17
 */
public interface BaseDao<T, I extends Serializable> {

    void persist(T entity);

    void merge(T entity);

    T findById(I id);

    List<T> findAll();
}
