package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EtudiantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Etudiant getEtudiantSample1() {
        return new Etudiant().id(1L).nom("nom1").prenom("prenom1").email("email1").choixDeCarriere("choixDeCarriere1");
    }

    public static Etudiant getEtudiantSample2() {
        return new Etudiant().id(2L).nom("nom2").prenom("prenom2").email("email2").choixDeCarriere("choixDeCarriere2");
    }

    public static Etudiant getEtudiantRandomSampleGenerator() {
        return new Etudiant()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .choixDeCarriere(UUID.randomUUID().toString());
    }
}
