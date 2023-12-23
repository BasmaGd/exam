package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FiliereEtudes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FiliereEtudesRepositoryWithBagRelationshipsImpl implements FiliereEtudesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<FiliereEtudes> fetchBagRelationships(Optional<FiliereEtudes> filiereEtudes) {
        return filiereEtudes.map(this::fetchCoursRequis);
    }

    @Override
    public Page<FiliereEtudes> fetchBagRelationships(Page<FiliereEtudes> filiereEtudes) {
        return new PageImpl<>(
            fetchBagRelationships(filiereEtudes.getContent()),
            filiereEtudes.getPageable(),
            filiereEtudes.getTotalElements()
        );
    }

    @Override
    public List<FiliereEtudes> fetchBagRelationships(List<FiliereEtudes> filiereEtudes) {
        return Optional.of(filiereEtudes).map(this::fetchCoursRequis).orElse(Collections.emptyList());
    }

    FiliereEtudes fetchCoursRequis(FiliereEtudes result) {
        return entityManager
            .createQuery(
                "select filiereEtudes from FiliereEtudes filiereEtudes left join fetch filiereEtudes.coursRequis where filiereEtudes.id = :id",
                FiliereEtudes.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<FiliereEtudes> fetchCoursRequis(List<FiliereEtudes> filiereEtudes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, filiereEtudes.size()).forEach(index -> order.put(filiereEtudes.get(index).getId(), index));
        List<FiliereEtudes> result = entityManager
            .createQuery(
                "select filiereEtudes from FiliereEtudes filiereEtudes left join fetch filiereEtudes.coursRequis where filiereEtudes in :filiereEtudes",
                FiliereEtudes.class
            )
            .setParameter("filiereEtudes", filiereEtudes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
