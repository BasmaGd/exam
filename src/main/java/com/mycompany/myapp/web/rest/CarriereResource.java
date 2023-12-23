package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Carriere;
import com.mycompany.myapp.repository.CarriereRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Carriere}.
 */
@RestController
@RequestMapping("/api/carrieres")
@Transactional
public class CarriereResource {

    private final Logger log = LoggerFactory.getLogger(CarriereResource.class);

    private static final String ENTITY_NAME = "carriere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarriereRepository carriereRepository;

    public CarriereResource(CarriereRepository carriereRepository) {
        this.carriereRepository = carriereRepository;
    }

    /**
     * {@code POST  /carrieres} : Create a new carriere.
     *
     * @param carriere the carriere to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carriere, or with status {@code 400 (Bad Request)} if the carriere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Carriere> createCarriere(@RequestBody Carriere carriere) throws URISyntaxException {
        log.debug("REST request to save Carriere : {}", carriere);
        if (carriere.getId() != null) {
            throw new BadRequestAlertException("A new carriere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carriere result = carriereRepository.save(carriere);
        return ResponseEntity
            .created(new URI("/api/carrieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrieres/:id} : Updates an existing carriere.
     *
     * @param id the id of the carriere to save.
     * @param carriere the carriere to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carriere,
     * or with status {@code 400 (Bad Request)} if the carriere is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carriere couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Carriere> updateCarriere(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Carriere carriere
    ) throws URISyntaxException {
        log.debug("REST request to update Carriere : {}, {}", id, carriere);
        if (carriere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carriere.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carriereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Carriere result = carriereRepository.save(carriere);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carriere.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carrieres/:id} : Partial updates given fields of an existing carriere, field will ignore if it is null
     *
     * @param id the id of the carriere to save.
     * @param carriere the carriere to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carriere,
     * or with status {@code 400 (Bad Request)} if the carriere is not valid,
     * or with status {@code 404 (Not Found)} if the carriere is not found,
     * or with status {@code 500 (Internal Server Error)} if the carriere couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Carriere> partialUpdateCarriere(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Carriere carriere
    ) throws URISyntaxException {
        log.debug("REST request to partial update Carriere partially : {}, {}", id, carriere);
        if (carriere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carriere.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carriereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Carriere> result = carriereRepository
            .findById(carriere.getId())
            .map(existingCarriere -> {
                if (carriere.getNomCariere() != null) {
                    existingCarriere.setNomCariere(carriere.getNomCariere());
                }
                if (carriere.getPrerequisAcademiques() != null) {
                    existingCarriere.setPrerequisAcademiques(carriere.getPrerequisAcademiques());
                }

                return existingCarriere;
            })
            .map(carriereRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carriere.getId().toString())
        );
    }

    /**
     * {@code GET  /carrieres} : get all the carrieres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrieres in body.
     */
    @GetMapping("")
    public List<Carriere> getAllCarrieres() {
        log.debug("REST request to get all Carrieres");
        return carriereRepository.findAll();
    }

    /**
     * {@code GET  /carrieres/:id} : get the "id" carriere.
     *
     * @param id the id of the carriere to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carriere, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Carriere> getCarriere(@PathVariable("id") Long id) {
        log.debug("REST request to get Carriere : {}", id);
        Optional<Carriere> carriere = carriereRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(carriere);
    }

    /**
     * {@code DELETE  /carrieres/:id} : delete the "id" carriere.
     *
     * @param id the id of the carriere to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarriere(@PathVariable("id") Long id) {
        log.debug("REST request to delete Carriere : {}", id);
        carriereRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
