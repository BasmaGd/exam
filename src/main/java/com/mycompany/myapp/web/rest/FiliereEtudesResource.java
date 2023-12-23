package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FiliereEtudes;
import com.mycompany.myapp.repository.FiliereEtudesRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.FiliereEtudes}.
 */
@RestController
@RequestMapping("/api/filiere-etudes")
@Transactional
public class FiliereEtudesResource {

    private final Logger log = LoggerFactory.getLogger(FiliereEtudesResource.class);

    private static final String ENTITY_NAME = "filiereEtudes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FiliereEtudesRepository filiereEtudesRepository;

    public FiliereEtudesResource(FiliereEtudesRepository filiereEtudesRepository) {
        this.filiereEtudesRepository = filiereEtudesRepository;
    }

    /**
     * {@code POST  /filiere-etudes} : Create a new filiereEtudes.
     *
     * @param filiereEtudes the filiereEtudes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filiereEtudes, or with status {@code 400 (Bad Request)} if the filiereEtudes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FiliereEtudes> createFiliereEtudes(@RequestBody FiliereEtudes filiereEtudes) throws URISyntaxException {
        log.debug("REST request to save FiliereEtudes : {}", filiereEtudes);
        if (filiereEtudes.getId() != null) {
            throw new BadRequestAlertException("A new filiereEtudes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FiliereEtudes result = filiereEtudesRepository.save(filiereEtudes);
        return ResponseEntity
            .created(new URI("/api/filiere-etudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filiere-etudes/:id} : Updates an existing filiereEtudes.
     *
     * @param id the id of the filiereEtudes to save.
     * @param filiereEtudes the filiereEtudes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filiereEtudes,
     * or with status {@code 400 (Bad Request)} if the filiereEtudes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filiereEtudes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FiliereEtudes> updateFiliereEtudes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FiliereEtudes filiereEtudes
    ) throws URISyntaxException {
        log.debug("REST request to update FiliereEtudes : {}, {}", id, filiereEtudes);
        if (filiereEtudes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filiereEtudes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filiereEtudesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FiliereEtudes result = filiereEtudesRepository.save(filiereEtudes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filiereEtudes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /filiere-etudes/:id} : Partial updates given fields of an existing filiereEtudes, field will ignore if it is null
     *
     * @param id the id of the filiereEtudes to save.
     * @param filiereEtudes the filiereEtudes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filiereEtudes,
     * or with status {@code 400 (Bad Request)} if the filiereEtudes is not valid,
     * or with status {@code 404 (Not Found)} if the filiereEtudes is not found,
     * or with status {@code 500 (Internal Server Error)} if the filiereEtudes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FiliereEtudes> partialUpdateFiliereEtudes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FiliereEtudes filiereEtudes
    ) throws URISyntaxException {
        log.debug("REST request to partial update FiliereEtudes partially : {}, {}", id, filiereEtudes);
        if (filiereEtudes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filiereEtudes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filiereEtudesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FiliereEtudes> result = filiereEtudesRepository
            .findById(filiereEtudes.getId())
            .map(existingFiliereEtudes -> {
                if (filiereEtudes.getNomFiliere() != null) {
                    existingFiliereEtudes.setNomFiliere(filiereEtudes.getNomFiliere());
                }
                if (filiereEtudes.getDescription() != null) {
                    existingFiliereEtudes.setDescription(filiereEtudes.getDescription());
                }

                return existingFiliereEtudes;
            })
            .map(filiereEtudesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filiereEtudes.getId().toString())
        );
    }

    /**
     * {@code GET  /filiere-etudes} : get all the filiereEtudes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filiereEtudes in body.
     */
    @GetMapping("")
    public List<FiliereEtudes> getAllFiliereEtudes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all FiliereEtudes");
        if (eagerload) {
            return filiereEtudesRepository.findAllWithEagerRelationships();
        } else {
            return filiereEtudesRepository.findAll();
        }
    }

    /**
     * {@code GET  /filiere-etudes/:id} : get the "id" filiereEtudes.
     *
     * @param id the id of the filiereEtudes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filiereEtudes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FiliereEtudes> getFiliereEtudes(@PathVariable("id") Long id) {
        log.debug("REST request to get FiliereEtudes : {}", id);
        Optional<FiliereEtudes> filiereEtudes = filiereEtudesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(filiereEtudes);
    }

    /**
     * {@code DELETE  /filiere-etudes/:id} : delete the "id" filiereEtudes.
     *
     * @param id the id of the filiereEtudes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliereEtudes(@PathVariable("id") Long id) {
        log.debug("REST request to delete FiliereEtudes : {}", id);
        filiereEtudesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
