package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ConseillerOrientation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConseillerOrientation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConseillerOrientationRepository extends JpaRepository<ConseillerOrientation, Long> {}
