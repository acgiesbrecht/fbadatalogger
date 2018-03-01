package com.chortitzer.cin.fba.fbadatalogger.model.dao.fba;

import com.chortitzer.cin.fba.fbadatalogger.model.dao.AbstractDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractDaoBalanceadosImp<T> implements AbstractDao<T> {
    protected Class<T> entityClass;

    @Inject
    @PgBalanceados
    private EntityManager em;

    public AbstractDaoBalanceadosImp(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Retrieves the meta-model for a certain entity.
     *
     * @return the meta-model of a certain entity.
     */
    protected EntityType<T> getMetaModel() {
        return getEntityManager().getMetamodel().entity(entityClass);
    }

    public void persist(T entity) {
        beginTransaction();
        getEntityManager().persist(entity);
        commitTransaction();
    }

    public void merge(T entity) {
        beginTransaction();
        getEntityManager().merge(entity);
        commitTransaction();
    }

    public void remove(T entity) {
        beginTransaction();
        getEntityManager().remove(getEntityManager().merge(entity));
        commitTransaction();
    }

    @Transactional
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Transactional
    public List<T> findAll() {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder()
                .createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Transactional
    public List<T> getListFromQuery(String jpqlQuery) {
        return (List<T>) getEntityManager().createQuery(jpqlQuery).getResultList();
    }

    public void beginTransaction() {
        try {
            getEntityManager().getTransaction().begin();
        } catch (IllegalStateException e) {
            rollBackTransaction();
        }
    }

    public void commitTransaction() {
        try {
            getEntityManager().getTransaction().commit();
        } catch (IllegalStateException | RollbackException e) {
            rollBackTransaction();
        }
    }

    private void rollBackTransaction() {
        try {
            getEntityManager().getTransaction().rollback();
        } catch (IllegalStateException | PersistenceException e) {
            e.printStackTrace();
        }
    }

}
