package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CoursTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static com.mycompany.myapp.domain.FiliereEtudesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CoursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cours.class);
        Cours cours1 = getCoursSample1();
        Cours cours2 = new Cours();
        assertThat(cours1).isNotEqualTo(cours2);

        cours2.setId(cours1.getId());
        assertThat(cours1).isEqualTo(cours2);

        cours2 = getCoursSample2();
        assertThat(cours1).isNotEqualTo(cours2);
    }

    @Test
    void etudiantTest() throws Exception {
        Cours cours = getCoursRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        cours.addEtudiant(etudiantBack);
        assertThat(cours.getEtudiants()).containsOnly(etudiantBack);
        assertThat(etudiantBack.getNomCours()).isEqualTo(cours);

        cours.removeEtudiant(etudiantBack);
        assertThat(cours.getEtudiants()).doesNotContain(etudiantBack);
        assertThat(etudiantBack.getNomCours()).isNull();

        cours.etudiants(new HashSet<>(Set.of(etudiantBack)));
        assertThat(cours.getEtudiants()).containsOnly(etudiantBack);
        assertThat(etudiantBack.getNomCours()).isEqualTo(cours);

        cours.setEtudiants(new HashSet<>());
        assertThat(cours.getEtudiants()).doesNotContain(etudiantBack);
        assertThat(etudiantBack.getNomCours()).isNull();
    }

    @Test
    void filiereEtudesTest() throws Exception {
        Cours cours = getCoursRandomSampleGenerator();
        FiliereEtudes filiereEtudesBack = getFiliereEtudesRandomSampleGenerator();

        cours.addFiliereEtudes(filiereEtudesBack);
        assertThat(cours.getFiliereEtudes()).containsOnly(filiereEtudesBack);
        assertThat(filiereEtudesBack.getCoursRequis()).containsOnly(cours);

        cours.removeFiliereEtudes(filiereEtudesBack);
        assertThat(cours.getFiliereEtudes()).doesNotContain(filiereEtudesBack);
        assertThat(filiereEtudesBack.getCoursRequis()).doesNotContain(cours);

        cours.filiereEtudes(new HashSet<>(Set.of(filiereEtudesBack)));
        assertThat(cours.getFiliereEtudes()).containsOnly(filiereEtudesBack);
        assertThat(filiereEtudesBack.getCoursRequis()).containsOnly(cours);

        cours.setFiliereEtudes(new HashSet<>());
        assertThat(cours.getFiliereEtudes()).doesNotContain(filiereEtudesBack);
        assertThat(filiereEtudesBack.getCoursRequis()).doesNotContain(cours);
    }
}
