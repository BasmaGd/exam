package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ConseillerOrientation;
import com.mycompany.myapp.repository.ConseillerOrientationRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ConseillerOrientation}.
 */
@RestController
@RequestMapping("/api/conseiller-orientations")
@Transactional
public class ConseillerOrientationResource {

    private final Logger log = LoggerFactory.getLogger(ConseillerOrientationResource.class);

    private static final String ENTITY_NAME = "conseillerOrientation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConseillerOrientationRepository conseillerOrientationRepository;

    public ConseillerOrientationResource(ConseillerOrientationRepository conseillerOrientationRepository) {
        this.conseillerOrientationRepository = conseillerOrientationRepository;
    }

    /**
     * {@code POST  /conseiller-orientations} : Create a new conseillerOrientation.
     *
     * @param conseillerOrientation the conseillerOrientation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conseillerOrientation, or with status {@code 400 (Bad Request)} if the conseillerOrientation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConseillerOrientation> createConseillerOrientation(@RequestBody ConseillerOrientation conseillerOrientation)
        throws URISyntaxException {
        log.debug("REST request to save ConseillerOrientation : {}", conseillerOrientation);
        if (conseillerOrientation.getId() != null) {
            throw new BadRequestAlertException("A new conseillerOrientation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConseillerOrientation result = conseillerOrientationRepository.save(conseillerOrientation);
        return ResponseEntity
            .created(new URI("/api/conseiller-orientations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conseiller-orientations/:id} : Updates an existing conseillerOrientation.
     *
     * @param id the id of the conseillerOrientation to save.
     * @param conseillerOrientation the conseillerOrientation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conseillerOrientation,
     * or with status {@code 400 (Bad Request)} if the conseillerOrientation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conseillerOrientation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConseillerOrientation> updateConseillerOrientation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConseillerOrientation conseillerOrientation
    ) throws URISyntaxException {
        log.debug("REST request to update ConseillerOrientation : {}, {}", id, conseillerOrientation);
        if (conseillerOrientation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conseillerOrientation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conseillerOrientationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConseillerOrientation result = conseillerOrientationRepository.save(conseillerOrientation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conseillerOrientation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conseiller-orientations/:id} : Partial updates given fields of an existing conseillerOrientation, field will ignore if it is null
     *
     * @param id the id of the conseillerOrientation to save.
     * @param conseillerOrientation the conseillerOrientation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conseillerOrientation,
     * or with status {@code 400 (Bad Request)} if the conseillerOrientation is not valid,
     * or with status {@code 404 (Not Found)} if the conseillerOrientation is not found,
     * or with status {@code 500 (Internal Server Error)} if the conseillerOrientation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConseillerOrientation> partialUpdateConseillerOrientation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConseillerOrientation conseillerOrientation
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConseillerOrientation partially : {}, {}", id, conseillerOrientation);
        if (conseillerOrientation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conseillerOrientation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conseillerOrientationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConseillerOrientation> result = conseillerOrientationRepository
            .findById(conseillerOrientation.getId())
            .map(existingConseillerOrientation -> {
                if (conseillerOrientation.getNomConseiller() != null) {
                    existingConseillerOrientation.setNomConseiller(conseillerOrientation.getNomConseiller());
                }
                if (conseillerOrientation.getPrenom() != null) {
                    existingConseillerOrientation.setPrenom(conseillerOrientation.getPrenom());
                }
                if (conseillerOrientation.getDomaineExpertise() != null) {
                    existingConseillerOrientation.setDomaineExpertise(conseillerOrientation.getDomaineExpertise());
                }
                if (conseillerOrientation.getCoordonnees() != null) {
                    existingConseillerOrientation.setCoordonnees(conseillerOrientation.getCoordonnees());
                }

                return existingConseillerOrientation;
            })
            .map(conseillerOrientationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conseillerOrientation.getId().toString())
        );
    }

    /**
     * {@code GET  /conseiller-orientations} : get all the conseillerOrientations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conseillerOrientations in body.
     */
    @GetMapping("")
    public List<ConseillerOrientation> getAllConseillerOrientations() {
        log.debug("REST request to get all ConseillerOrientations");
        return conseillerOrientationRepository.findAll();
    }

    /**
     * {@code GET  /conseiller-orientations/:id} : get the "id" conseillerOrientation.
     *
     * @param id the id of the conseillerOrientation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conseillerOrientation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConseillerOrientation> getConseillerOrientation(@PathVariable("id") Long id) {
        log.debug("REST request to get ConseillerOrientation : {}", id);
        Optional<ConseillerOrientation> conseillerOrientation = conseillerOrientationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conseillerOrientation);
    }

    /**
     * {@code DELETE  /conseiller-orientations/:id} : delete the "id" conseillerOrientation.
     *
     * @param id the id of the conseillerOrientation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConseillerOrientation(@PathVariable("id") Long id) {
        log.debug("REST request to delete ConseillerOrientation : {}", id);
        conseillerOrientationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
