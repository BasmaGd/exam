package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConseillerOrientationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ConseillerOrientation getConseillerOrientationSample1() {
        return new ConseillerOrientation()
            .id(1L)
            .nomConseiller("nomConseiller1")
            .prenom("prenom1")
            .domaineExpertise("domaineExpertise1")
            .coordonnees("coordonnees1");
    }

    public static ConseillerOrientation getConseillerOrientationSample2() {
        return new ConseillerOrientation()
            .id(2L)
            .nomConseiller("nomConseiller2")
            .prenom("prenom2")
            .domaineExpertise("domaineExpertise2")
            .coordonnees("coordonnees2");
    }

    public static ConseillerOrientation getConseillerOrientationRandomSampleGenerator() {
        return new ConseillerOrientation()
            .id(longCount.incrementAndGet())
            .nomConseiller(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .domaineExpertise(UUID.randomUUID().toString())
            .coordonnees(UUID.randomUUID().toString());
    }
}
