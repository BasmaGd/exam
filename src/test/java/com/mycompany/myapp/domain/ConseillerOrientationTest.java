package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CarriereTestSamples.*;
import static com.mycompany.myapp.domain.ConseillerOrientationTestSamples.*;
import static com.mycompany.myapp.domain.FiliereEtudesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ConseillerOrientationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConseillerOrientation.class);
        ConseillerOrientation conseillerOrientation1 = getConseillerOrientationSample1();
        ConseillerOrientation conseillerOrientation2 = new ConseillerOrientation();
        assertThat(conseillerOrientation1).isNotEqualTo(conseillerOrientation2);

        conseillerOrientation2.setId(conseillerOrientation1.getId());
        assertThat(conseillerOrientation1).isEqualTo(conseillerOrientation2);

        conseillerOrientation2 = getConseillerOrientationSample2();
        assertThat(conseillerOrientation1).isNotEqualTo(conseillerOrientation2);
    }

    @Test
    void carriereTest() throws Exception {
        ConseillerOrientation conseillerOrientation = getConseillerOrientationRandomSampleGenerator();
        Carriere carriereBack = getCarriereRandomSampleGenerator();

        conseillerOrientation.addCarriere(carriereBack);
        assertThat(conseillerOrientation.getCarrieres()).containsOnly(carriereBack);
        assertThat(carriereBack.getNomConseiller()).isEqualTo(conseillerOrientation);

        conseillerOrientation.removeCarriere(carriereBack);
        assertThat(conseillerOrientation.getCarrieres()).doesNotContain(carriereBack);
        assertThat(carriereBack.getNomConseiller()).isNull();

        conseillerOrientation.carrieres(new HashSet<>(Set.of(carriereBack)));
        assertThat(conseillerOrientation.getCarrieres()).containsOnly(carriereBack);
        assertThat(carriereBack.getNomConseiller()).isEqualTo(conseillerOrientation);

        conseillerOrientation.setCarrieres(new HashSet<>());
        assertThat(conseillerOrientation.getCarrieres()).doesNotContain(carriereBack);
        assertThat(carriereBack.getNomConseiller()).isNull();
    }

    @Test
    void filiereEtudesTest() throws Exception {
        ConseillerOrientation conseillerOrientation = getConseillerOrientationRandomSampleGenerator();
        FiliereEtudes filiereEtudesBack = getFiliereEtudesRandomSampleGenerator();

        conseillerOrientation.addFiliereEtudes(filiereEtudesBack);
        assertThat(conseillerOrientation.getFiliereEtudes()).containsOnly(filiereEtudesBack);
        assertThat(filiereEtudesBack.getNomConseiller()).isEqualTo(conseillerOrientation);

        conseillerOrientation.removeFiliereEtudes(filiereEtudesBack);
        assertThat(conseillerOrientation.getFiliereEtudes()).doesNotContain(filiereEtudesBack);
        assertThat(filiereEtudesBack.getNomConseiller()).isNull();

        conseillerOrientation.filiereEtudes(new HashSet<>(Set.of(filiereEtudesBack)));
        assertThat(conseillerOrientation.getFiliereEtudes()).containsOnly(filiereEtudesBack);
        assertThat(filiereEtudesBack.getNomConseiller()).isEqualTo(conseillerOrientation);

        conseillerOrientation.setFiliereEtudes(new HashSet<>());
        assertThat(conseillerOrientation.getFiliereEtudes()).doesNotContain(filiereEtudesBack);
        assertThat(filiereEtudesBack.getNomConseiller()).isNull();
    }
}
