package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FiliereEtudes;
import com.mycompany.myapp.repository.FiliereEtudesRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FiliereEtudesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FiliereEtudesResourceIT {

    private static final String DEFAULT_NOM_FILIERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FILIERE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/filiere-etudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FiliereEtudesRepository filiereEtudesRepository;

    @Mock
    private FiliereEtudesRepository filiereEtudesRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFiliereEtudesMockMvc;

    private FiliereEtudes filiereEtudes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FiliereEtudes createEntity(EntityManager em) {
        FiliereEtudes filiereEtudes = new FiliereEtudes().nomFiliere(DEFAULT_NOM_FILIERE).description(DEFAULT_DESCRIPTION);
        return filiereEtudes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FiliereEtudes createUpdatedEntity(EntityManager em) {
        FiliereEtudes filiereEtudes = new FiliereEtudes().nomFiliere(UPDATED_NOM_FILIERE).description(UPDATED_DESCRIPTION);
        return filiereEtudes;
    }

    @BeforeEach
    public void initTest() {
        filiereEtudes = createEntity(em);
    }

    @Test
    @Transactional
    void createFiliereEtudes() throws Exception {
        int databaseSizeBeforeCreate = filiereEtudesRepository.findAll().size();
        // Create the FiliereEtudes
        restFiliereEtudesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filiereEtudes)))
            .andExpect(status().isCreated());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeCreate + 1);
        FiliereEtudes testFiliereEtudes = filiereEtudesList.get(filiereEtudesList.size() - 1);
        assertThat(testFiliereEtudes.getNomFiliere()).isEqualTo(DEFAULT_NOM_FILIERE);
        assertThat(testFiliereEtudes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFiliereEtudesWithExistingId() throws Exception {
        // Create the FiliereEtudes with an existing ID
        filiereEtudes.setId(1L);

        int databaseSizeBeforeCreate = filiereEtudesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFiliereEtudesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filiereEtudes)))
            .andExpect(status().isBadRequest());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFiliereEtudes() throws Exception {
        // Initialize the database
        filiereEtudesRepository.saveAndFlush(filiereEtudes);

        // Get all the filiereEtudesList
        restFiliereEtudesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiereEtudes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFiliere").value(hasItem(DEFAULT_NOM_FILIERE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFiliereEtudesWithEagerRelationshipsIsEnabled() throws Exception {
        when(filiereEtudesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFiliereEtudesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(filiereEtudesRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFiliereEtudesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(filiereEtudesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFiliereEtudesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(filiereEtudesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFiliereEtudes() throws Exception {
        // Initialize the database
        filiereEtudesRepository.saveAndFlush(filiereEtudes);

        // Get the filiereEtudes
        restFiliereEtudesMockMvc
            .perform(get(ENTITY_API_URL_ID, filiereEtudes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filiereEtudes.getId().intValue()))
            .andExpect(jsonPath("$.nomFiliere").value(DEFAULT_NOM_FILIERE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFiliereEtudes() throws Exception {
        // Get the filiereEtudes
        restFiliereEtudesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFiliereEtudes() throws Exception {
        // Initialize the database
        filiereEtudesRepository.saveAndFlush(filiereEtudes);

        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();

        // Update the filiereEtudes
        FiliereEtudes updatedFiliereEtudes = filiereEtudesRepository.findById(filiereEtudes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFiliereEtudes are not directly saved in db
        em.detach(updatedFiliereEtudes);
        updatedFiliereEtudes.nomFiliere(UPDATED_NOM_FILIERE).description(UPDATED_DESCRIPTION);

        restFiliereEtudesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFiliereEtudes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFiliereEtudes))
            )
            .andExpect(status().isOk());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
        FiliereEtudes testFiliereEtudes = filiereEtudesList.get(filiereEtudesList.size() - 1);
        assertThat(testFiliereEtudes.getNomFiliere()).isEqualTo(UPDATED_NOM_FILIERE);
        assertThat(testFiliereEtudes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFiliereEtudes() throws Exception {
        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();
        filiereEtudes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFiliereEtudesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filiereEtudes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filiereEtudes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFiliereEtudes() throws Exception {
        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();
        filiereEtudes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFiliereEtudesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filiereEtudes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFiliereEtudes() throws Exception {
        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();
        filiereEtudes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFiliereEtudesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filiereEtudes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFiliereEtudesWithPatch() throws Exception {
        // Initialize the database
        filiereEtudesRepository.saveAndFlush(filiereEtudes);

        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();

        // Update the filiereEtudes using partial update
        FiliereEtudes partialUpdatedFiliereEtudes = new FiliereEtudes();
        partialUpdatedFiliereEtudes.setId(filiereEtudes.getId());

        partialUpdatedFiliereEtudes.nomFiliere(UPDATED_NOM_FILIERE).description(UPDATED_DESCRIPTION);

        restFiliereEtudesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiliereEtudes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiliereEtudes))
            )
            .andExpect(status().isOk());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
        FiliereEtudes testFiliereEtudes = filiereEtudesList.get(filiereEtudesList.size() - 1);
        assertThat(testFiliereEtudes.getNomFiliere()).isEqualTo(UPDATED_NOM_FILIERE);
        assertThat(testFiliereEtudes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFiliereEtudesWithPatch() throws Exception {
        // Initialize the database
        filiereEtudesRepository.saveAndFlush(filiereEtudes);

        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();

        // Update the filiereEtudes using partial update
        FiliereEtudes partialUpdatedFiliereEtudes = new FiliereEtudes();
        partialUpdatedFiliereEtudes.setId(filiereEtudes.getId());

        partialUpdatedFiliereEtudes.nomFiliere(UPDATED_NOM_FILIERE).description(UPDATED_DESCRIPTION);

        restFiliereEtudesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFiliereEtudes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFiliereEtudes))
            )
            .andExpect(status().isOk());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
        FiliereEtudes testFiliereEtudes = filiereEtudesList.get(filiereEtudesList.size() - 1);
        assertThat(testFiliereEtudes.getNomFiliere()).isEqualTo(UPDATED_NOM_FILIERE);
        assertThat(testFiliereEtudes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFiliereEtudes() throws Exception {
        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();
        filiereEtudes.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFiliereEtudesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, filiereEtudes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filiereEtudes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFiliereEtudes() throws Exception {
        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();
        filiereEtudes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFiliereEtudesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filiereEtudes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFiliereEtudes() throws Exception {
        int databaseSizeBeforeUpdate = filiereEtudesRepository.findAll().size();
        filiereEtudes.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFiliereEtudesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(filiereEtudes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FiliereEtudes in the database
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFiliereEtudes() throws Exception {
        // Initialize the database
        filiereEtudesRepository.saveAndFlush(filiereEtudes);

        int databaseSizeBeforeDelete = filiereEtudesRepository.findAll().size();

        // Delete the filiereEtudes
        restFiliereEtudesMockMvc
            .perform(delete(ENTITY_API_URL_ID, filiereEtudes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FiliereEtudes> filiereEtudesList = filiereEtudesRepository.findAll();
        assertThat(filiereEtudesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
