package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FiliereEtudes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FiliereEtudes entity.
 *
 * When extending this class, extend FiliereEtudesRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface FiliereEtudesRepository extends FiliereEtudesRepositoryWithBagRelationships, JpaRepository<FiliereEtudes, Long> {
    default Optional<FiliereEtudes> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<FiliereEtudes> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<FiliereEtudes> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
