package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FiliereEtudes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FiliereEtudesRepositoryWithBagRelationships {
    Optional<FiliereEtudes> fetchBagRelationships(Optional<FiliereEtudes> filiereEtudes);

    List<FiliereEtudes> fetchBagRelationships(List<FiliereEtudes> filiereEtudes);

    Page<FiliereEtudes> fetchBagRelationships(Page<FiliereEtudes> filiereEtudes);
}
