package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ConseillerOrientationTestSamples.*;
import static com.mycompany.myapp.domain.CoursTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.FiliereEtudesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FiliereEtudesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FiliereEtudes.class);
        FiliereEtudes filiereEtudes1 = getFiliereEtudesSample1();
        FiliereEtudes filiereEtudes2 = new FiliereEtudes();
        assertThat(filiereEtudes1).isNotEqualTo(filiereEtudes2);

        filiereEtudes2.setId(filiereEtudes1.getId());
        assertThat(filiereEtudes1).isEqualTo(filiereEtudes2);

        filiereEtudes2 = getFiliereEtudesSample2();
        assertThat(filiereEtudes1).isNotEqualTo(filiereEtudes2);
    }

    @Test
    void etudiantTest() throws Exception {
        FiliereEtudes filiereEtudes = getFiliereEtudesRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        filiereEtudes.addEtudiant(etudiantBack);
        assertThat(filiereEtudes.getEtudiants()).containsOnly(etudiantBack);
        assertThat(etudiantBack.getNomFiliere()).isEqualTo(filiereEtudes);

        filiereEtudes.removeEtudiant(etudiantBack);
        assertThat(filiereEtudes.getEtudiants()).doesNotContain(etudiantBack);
        assertThat(etudiantBack.getNomFiliere()).isNull();

        filiereEtudes.etudiants(new HashSet<>(Set.of(etudiantBack)));
        assertThat(filiereEtudes.getEtudiants()).containsOnly(etudiantBack);
        assertThat(etudiantBack.getNomFiliere()).isEqualTo(filiereEtudes);

        filiereEtudes.setEtudiants(new HashSet<>());
        assertThat(filiereEtudes.getEtudiants()).doesNotContain(etudiantBack);
        assertThat(etudiantBack.getNomFiliere()).isNull();
    }

    @Test
    void coursRequisTest() throws Exception {
        FiliereEtudes filiereEtudes = getFiliereEtudesRandomSampleGenerator();
        Cours coursBack = getCoursRandomSampleGenerator();

        filiereEtudes.addCoursRequis(coursBack);
        assertThat(filiereEtudes.getCoursRequis()).containsOnly(coursBack);

        filiereEtudes.removeCoursRequis(coursBack);
        assertThat(filiereEtudes.getCoursRequis()).doesNotContain(coursBack);

        filiereEtudes.coursRequis(new HashSet<>(Set.of(coursBack)));
        assertThat(filiereEtudes.getCoursRequis()).containsOnly(coursBack);

        filiereEtudes.setCoursRequis(new HashSet<>());
        assertThat(filiereEtudes.getCoursRequis()).doesNotContain(coursBack);
    }

    @Test
    void nomConseillerTest() throws Exception {
        FiliereEtudes filiereEtudes = getFiliereEtudesRandomSampleGenerator();
        ConseillerOrientation conseillerOrientationBack = getConseillerOrientationRandomSampleGenerator();

        filiereEtudes.setNomConseiller(conseillerOrientationBack);
        assertThat(filiereEtudes.getNomConseiller()).isEqualTo(conseillerOrientationBack);

        filiereEtudes.nomConseiller(null);
        assertThat(filiereEtudes.getNomConseiller()).isNull();
    }
}
