package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Carriere;
import com.mycompany.myapp.repository.CarriereRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CarriereResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarriereResourceIT {

    private static final String DEFAULT_NOM_CARIERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CARIERE = "BBBBBBBBBB";

    private static final String DEFAULT_PREREQUIS_ACADEMIQUES = "AAAAAAAAAA";
    private static final String UPDATED_PREREQUIS_ACADEMIQUES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carrieres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarriereRepository carriereRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarriereMockMvc;

    private Carriere carriere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carriere createEntity(EntityManager em) {
        Carriere carriere = new Carriere().nomCariere(DEFAULT_NOM_CARIERE).prerequisAcademiques(DEFAULT_PREREQUIS_ACADEMIQUES);
        return carriere;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carriere createUpdatedEntity(EntityManager em) {
        Carriere carriere = new Carriere().nomCariere(UPDATED_NOM_CARIERE).prerequisAcademiques(UPDATED_PREREQUIS_ACADEMIQUES);
        return carriere;
    }

    @BeforeEach
    public void initTest() {
        carriere = createEntity(em);
    }

    @Test
    @Transactional
    void createCarriere() throws Exception {
        int databaseSizeBeforeCreate = carriereRepository.findAll().size();
        // Create the Carriere
        restCarriereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isCreated());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeCreate + 1);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCariere()).isEqualTo(DEFAULT_NOM_CARIERE);
        assertThat(testCarriere.getPrerequisAcademiques()).isEqualTo(DEFAULT_PREREQUIS_ACADEMIQUES);
    }

    @Test
    @Transactional
    void createCarriereWithExistingId() throws Exception {
        // Create the Carriere with an existing ID
        carriere.setId(1L);

        int databaseSizeBeforeCreate = carriereRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarriereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarrieres() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        // Get all the carriereList
        restCarriereMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carriere.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCariere").value(hasItem(DEFAULT_NOM_CARIERE)))
            .andExpect(jsonPath("$.[*].prerequisAcademiques").value(hasItem(DEFAULT_PREREQUIS_ACADEMIQUES)));
    }

    @Test
    @Transactional
    void getCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        // Get the carriere
        restCarriereMockMvc
            .perform(get(ENTITY_API_URL_ID, carriere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carriere.getId().intValue()))
            .andExpect(jsonPath("$.nomCariere").value(DEFAULT_NOM_CARIERE))
            .andExpect(jsonPath("$.prerequisAcademiques").value(DEFAULT_PREREQUIS_ACADEMIQUES));
    }

    @Test
    @Transactional
    void getNonExistingCarriere() throws Exception {
        // Get the carriere
        restCarriereMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere
        Carriere updatedCarriere = carriereRepository.findById(carriere.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCarriere are not directly saved in db
        em.detach(updatedCarriere);
        updatedCarriere.nomCariere(UPDATED_NOM_CARIERE).prerequisAcademiques(UPDATED_PREREQUIS_ACADEMIQUES);

        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarriere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCariere()).isEqualTo(UPDATED_NOM_CARIERE);
        assertThat(testCarriere.getPrerequisAcademiques()).isEqualTo(UPDATED_PREREQUIS_ACADEMIQUES);
    }

    @Test
    @Transactional
    void putNonExistingCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carriere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarriereWithPatch() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere using partial update
        Carriere partialUpdatedCarriere = new Carriere();
        partialUpdatedCarriere.setId(carriere.getId());

        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCariere()).isEqualTo(DEFAULT_NOM_CARIERE);
        assertThat(testCarriere.getPrerequisAcademiques()).isEqualTo(DEFAULT_PREREQUIS_ACADEMIQUES);
    }

    @Test
    @Transactional
    void fullUpdateCarriereWithPatch() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere using partial update
        Carriere partialUpdatedCarriere = new Carriere();
        partialUpdatedCarriere.setId(carriere.getId());

        partialUpdatedCarriere.nomCariere(UPDATED_NOM_CARIERE).prerequisAcademiques(UPDATED_PREREQUIS_ACADEMIQUES);

        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getNomCariere()).isEqualTo(UPDATED_NOM_CARIERE);
        assertThat(testCarriere.getPrerequisAcademiques()).isEqualTo(UPDATED_PREREQUIS_ACADEMIQUES);
    }

    @Test
    @Transactional
    void patchNonExistingCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeDelete = carriereRepository.findAll().size();

        // Delete the carriere
        restCarriereMockMvc
            .perform(delete(ENTITY_API_URL_ID, carriere.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
