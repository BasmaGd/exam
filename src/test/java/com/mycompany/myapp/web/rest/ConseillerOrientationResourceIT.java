package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ConseillerOrientation;
import com.mycompany.myapp.repository.ConseillerOrientationRepository;
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
 * Integration tests for the {@link ConseillerOrientationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConseillerOrientationResourceIT {

    private static final String DEFAULT_NOM_CONSEILLER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CONSEILLER = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE_EXPERTISE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE_EXPERTISE = "BBBBBBBBBB";

    private static final String DEFAULT_COORDONNEES = "AAAAAAAAAA";
    private static final String UPDATED_COORDONNEES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conseiller-orientations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConseillerOrientationRepository conseillerOrientationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConseillerOrientationMockMvc;

    private ConseillerOrientation conseillerOrientation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConseillerOrientation createEntity(EntityManager em) {
        ConseillerOrientation conseillerOrientation = new ConseillerOrientation()
            .nomConseiller(DEFAULT_NOM_CONSEILLER)
            .prenom(DEFAULT_PRENOM)
            .domaineExpertise(DEFAULT_DOMAINE_EXPERTISE)
            .coordonnees(DEFAULT_COORDONNEES);
        return conseillerOrientation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConseillerOrientation createUpdatedEntity(EntityManager em) {
        ConseillerOrientation conseillerOrientation = new ConseillerOrientation()
            .nomConseiller(UPDATED_NOM_CONSEILLER)
            .prenom(UPDATED_PRENOM)
            .domaineExpertise(UPDATED_DOMAINE_EXPERTISE)
            .coordonnees(UPDATED_COORDONNEES);
        return conseillerOrientation;
    }

    @BeforeEach
    public void initTest() {
        conseillerOrientation = createEntity(em);
    }

    @Test
    @Transactional
    void createConseillerOrientation() throws Exception {
        int databaseSizeBeforeCreate = conseillerOrientationRepository.findAll().size();
        // Create the ConseillerOrientation
        restConseillerOrientationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isCreated());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeCreate + 1);
        ConseillerOrientation testConseillerOrientation = conseillerOrientationList.get(conseillerOrientationList.size() - 1);
        assertThat(testConseillerOrientation.getNomConseiller()).isEqualTo(DEFAULT_NOM_CONSEILLER);
        assertThat(testConseillerOrientation.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testConseillerOrientation.getDomaineExpertise()).isEqualTo(DEFAULT_DOMAINE_EXPERTISE);
        assertThat(testConseillerOrientation.getCoordonnees()).isEqualTo(DEFAULT_COORDONNEES);
    }

    @Test
    @Transactional
    void createConseillerOrientationWithExistingId() throws Exception {
        // Create the ConseillerOrientation with an existing ID
        conseillerOrientation.setId(1L);

        int databaseSizeBeforeCreate = conseillerOrientationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConseillerOrientationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConseillerOrientations() throws Exception {
        // Initialize the database
        conseillerOrientationRepository.saveAndFlush(conseillerOrientation);

        // Get all the conseillerOrientationList
        restConseillerOrientationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conseillerOrientation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomConseiller").value(hasItem(DEFAULT_NOM_CONSEILLER)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].domaineExpertise").value(hasItem(DEFAULT_DOMAINE_EXPERTISE)))
            .andExpect(jsonPath("$.[*].coordonnees").value(hasItem(DEFAULT_COORDONNEES)));
    }

    @Test
    @Transactional
    void getConseillerOrientation() throws Exception {
        // Initialize the database
        conseillerOrientationRepository.saveAndFlush(conseillerOrientation);

        // Get the conseillerOrientation
        restConseillerOrientationMockMvc
            .perform(get(ENTITY_API_URL_ID, conseillerOrientation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conseillerOrientation.getId().intValue()))
            .andExpect(jsonPath("$.nomConseiller").value(DEFAULT_NOM_CONSEILLER))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.domaineExpertise").value(DEFAULT_DOMAINE_EXPERTISE))
            .andExpect(jsonPath("$.coordonnees").value(DEFAULT_COORDONNEES));
    }

    @Test
    @Transactional
    void getNonExistingConseillerOrientation() throws Exception {
        // Get the conseillerOrientation
        restConseillerOrientationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConseillerOrientation() throws Exception {
        // Initialize the database
        conseillerOrientationRepository.saveAndFlush(conseillerOrientation);

        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();

        // Update the conseillerOrientation
        ConseillerOrientation updatedConseillerOrientation = conseillerOrientationRepository
            .findById(conseillerOrientation.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedConseillerOrientation are not directly saved in db
        em.detach(updatedConseillerOrientation);
        updatedConseillerOrientation
            .nomConseiller(UPDATED_NOM_CONSEILLER)
            .prenom(UPDATED_PRENOM)
            .domaineExpertise(UPDATED_DOMAINE_EXPERTISE)
            .coordonnees(UPDATED_COORDONNEES);

        restConseillerOrientationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConseillerOrientation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConseillerOrientation))
            )
            .andExpect(status().isOk());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
        ConseillerOrientation testConseillerOrientation = conseillerOrientationList.get(conseillerOrientationList.size() - 1);
        assertThat(testConseillerOrientation.getNomConseiller()).isEqualTo(UPDATED_NOM_CONSEILLER);
        assertThat(testConseillerOrientation.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testConseillerOrientation.getDomaineExpertise()).isEqualTo(UPDATED_DOMAINE_EXPERTISE);
        assertThat(testConseillerOrientation.getCoordonnees()).isEqualTo(UPDATED_COORDONNEES);
    }

    @Test
    @Transactional
    void putNonExistingConseillerOrientation() throws Exception {
        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();
        conseillerOrientation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConseillerOrientationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conseillerOrientation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConseillerOrientation() throws Exception {
        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();
        conseillerOrientation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseillerOrientationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConseillerOrientation() throws Exception {
        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();
        conseillerOrientation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseillerOrientationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConseillerOrientationWithPatch() throws Exception {
        // Initialize the database
        conseillerOrientationRepository.saveAndFlush(conseillerOrientation);

        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();

        // Update the conseillerOrientation using partial update
        ConseillerOrientation partialUpdatedConseillerOrientation = new ConseillerOrientation();
        partialUpdatedConseillerOrientation.setId(conseillerOrientation.getId());

        partialUpdatedConseillerOrientation.prenom(UPDATED_PRENOM).domaineExpertise(UPDATED_DOMAINE_EXPERTISE);

        restConseillerOrientationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConseillerOrientation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConseillerOrientation))
            )
            .andExpect(status().isOk());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
        ConseillerOrientation testConseillerOrientation = conseillerOrientationList.get(conseillerOrientationList.size() - 1);
        assertThat(testConseillerOrientation.getNomConseiller()).isEqualTo(DEFAULT_NOM_CONSEILLER);
        assertThat(testConseillerOrientation.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testConseillerOrientation.getDomaineExpertise()).isEqualTo(UPDATED_DOMAINE_EXPERTISE);
        assertThat(testConseillerOrientation.getCoordonnees()).isEqualTo(DEFAULT_COORDONNEES);
    }

    @Test
    @Transactional
    void fullUpdateConseillerOrientationWithPatch() throws Exception {
        // Initialize the database
        conseillerOrientationRepository.saveAndFlush(conseillerOrientation);

        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();

        // Update the conseillerOrientation using partial update
        ConseillerOrientation partialUpdatedConseillerOrientation = new ConseillerOrientation();
        partialUpdatedConseillerOrientation.setId(conseillerOrientation.getId());

        partialUpdatedConseillerOrientation
            .nomConseiller(UPDATED_NOM_CONSEILLER)
            .prenom(UPDATED_PRENOM)
            .domaineExpertise(UPDATED_DOMAINE_EXPERTISE)
            .coordonnees(UPDATED_COORDONNEES);

        restConseillerOrientationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConseillerOrientation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConseillerOrientation))
            )
            .andExpect(status().isOk());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
        ConseillerOrientation testConseillerOrientation = conseillerOrientationList.get(conseillerOrientationList.size() - 1);
        assertThat(testConseillerOrientation.getNomConseiller()).isEqualTo(UPDATED_NOM_CONSEILLER);
        assertThat(testConseillerOrientation.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testConseillerOrientation.getDomaineExpertise()).isEqualTo(UPDATED_DOMAINE_EXPERTISE);
        assertThat(testConseillerOrientation.getCoordonnees()).isEqualTo(UPDATED_COORDONNEES);
    }

    @Test
    @Transactional
    void patchNonExistingConseillerOrientation() throws Exception {
        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();
        conseillerOrientation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConseillerOrientationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conseillerOrientation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConseillerOrientation() throws Exception {
        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();
        conseillerOrientation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseillerOrientationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConseillerOrientation() throws Exception {
        int databaseSizeBeforeUpdate = conseillerOrientationRepository.findAll().size();
        conseillerOrientation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseillerOrientationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conseillerOrientation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConseillerOrientation in the database
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConseillerOrientation() throws Exception {
        // Initialize the database
        conseillerOrientationRepository.saveAndFlush(conseillerOrientation);

        int databaseSizeBeforeDelete = conseillerOrientationRepository.findAll().size();

        // Delete the conseillerOrientation
        restConseillerOrientationMockMvc
            .perform(delete(ENTITY_API_URL_ID, conseillerOrientation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConseillerOrientation> conseillerOrientationList = conseillerOrientationRepository.findAll();
        assertThat(conseillerOrientationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
