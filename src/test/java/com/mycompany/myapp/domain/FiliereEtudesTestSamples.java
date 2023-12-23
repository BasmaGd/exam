package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FiliereEtudesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FiliereEtudes getFiliereEtudesSample1() {
        return new FiliereEtudes().id(1L).nomFiliere("nomFiliere1").description("description1");
    }

    public static FiliereEtudes getFiliereEtudesSample2() {
        return new FiliereEtudes().id(2L).nomFiliere("nomFiliere2").description("description2");
    }

    public static FiliereEtudes getFiliereEtudesRandomSampleGenerator() {
        return new FiliereEtudes()
            .id(longCount.incrementAndGet())
            .nomFiliere(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
