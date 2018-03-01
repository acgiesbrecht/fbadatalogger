package com.chortitzer.cin.fba.fbadatalogger.model.dao.fba;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@PgBalanceados
public class EntityManagerProducerBalanceados {

    @Produces
    @PgBalanceados
    @ApplicationScoped
    public EntityManager createEntityManager() {
        return Persistence
                .createEntityManagerFactory("PU_FBA")
                .createEntityManager();
    }


    public void close(
            @Disposes @PgBalanceados EntityManager entityManager) {
        entityManager.close();
    }

}
