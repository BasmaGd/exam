package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
import static com.mycompany.myapp.domain.ConseillerOrientationTestSamples.*;
import static com.mycompany.myapp.domain.EtudiantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CarriereTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carriere.class);
        Carriere carriere1 = getCarriereSample1();
        Carriere carriere2 = new Carriere();
        assertThat(carriere1).isNotEqualTo(carriere2);

        carriere2.setId(carriere1.getId());
        assertThat(carriere1).isEqualTo(carriere2);

        carriere2 = getCarriereSample2();
        assertThat(carriere1).isNotEqualTo(carriere2);
    }

    @Test
    void etudiantTest() throws Exception {
        Carriere carriere = getCarriereRandomSampleGenerator();
        Etudiant etudiantBack = getEtudiantRandomSampleGenerator();

        carriere.addEtudiant(etudiantBack);
        assertThat(carriere.getEtudiants()).containsOnly(etudiantBack);
        assertThat(etudiantBack.getNomCariere()).isEqualTo(carriere);

        carriere.removeEtudiant(etudiantBack);
        assertThat(carriere.getEtudiants()).doesNotContain(etudiantBack);
        assertThat(etudiantBack.getNomCariere()).isNull();

        carriere.etudiants(new HashSet<>(Set.of(etudiantBack)));
        assertThat(carriere.getEtudiants()).containsOnly(etudiantBack);
        assertThat(etudiantBack.getNomCariere()).isEqualTo(carriere);

        carriere.setEtudiants(new HashSet<>());
        assertThat(carriere.getEtudiants()).doesNotContain(etudiantBack);
        assertThat(etudiantBack.getNomCariere()).isNull();
    }

    @Test
    void nomConseillerTest() throws Exception {
        Carriere carriere = getCarriereRandomSampleGenerator();
        ConseillerOrientation conseillerOrientationBack = getConseillerOrientationRandomSampleGenerator();

        carriere.setNomConseiller(conseillerOrientationBack);
        assertThat(carriere.getNomConseiller()).isEqualTo(conseillerOrientationBack);

        carriere.nomConseiller(null);
        assertThat(carriere.getNomConseiller()).isNull();
    }
}
