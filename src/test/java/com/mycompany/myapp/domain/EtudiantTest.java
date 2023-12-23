package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
import static com.mycompany.myapp.domain.CoursTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.FiliereEtudesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtudiantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etudiant.class);
        Etudiant etudiant1 = getEtudiantSample1();
        Etudiant etudiant2 = new Etudiant();
        assertThat(etudiant1).isNotEqualTo(etudiant2);

        etudiant2.setId(etudiant1.getId());
        assertThat(etudiant1).isEqualTo(etudiant2);

        etudiant2 = getEtudiantSample2();
        assertThat(etudiant1).isNotEqualTo(etudiant2);
    }

    @Test
    void nomCariereTest() throws Exception {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        Carriere carriereBack = getCarriereRandomSampleGenerator();

        etudiant.setNomCariere(carriereBack);
        assertThat(etudiant.getNomCariere()).isEqualTo(carriereBack);

        etudiant.nomCariere(null);
        assertThat(etudiant.getNomCariere()).isNull();
    }

    @Test
    void nomFiliereTest() throws Exception {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        FiliereEtudes filiereEtudesBack = getFiliereEtudesRandomSampleGenerator();

        etudiant.setNomFiliere(filiereEtudesBack);
        assertThat(etudiant.getNomFiliere()).isEqualTo(filiereEtudesBack);

        etudiant.nomFiliere(null);
        assertThat(etudiant.getNomFiliere()).isNull();
    }

    @Test
    void nomCoursTest() throws Exception {
        Etudiant etudiant = getEtudiantRandomSampleGenerator();
        Cours coursBack = getCoursRandomSampleGenerator();

        etudiant.setNomCours(coursBack);
        assertThat(etudiant.getNomCours()).isEqualTo(coursBack);

        etudiant.nomCours(null);
        assertThat(etudiant.getNomCours()).isNull();
    }
}
