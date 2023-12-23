package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CarriereTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Carriere getCarriereSample1() {
        return new Carriere().id(1L).nomCariere("nomCariere1").prerequisAcademiques("prerequisAcademiques1");
    }

    public static Carriere getCarriereSample2() {
        return new Carriere().id(2L).nomCariere("nomCariere2").prerequisAcademiques("prerequisAcademiques2");
    }

    public static Carriere getCarriereRandomSampleGenerator() {
        return new Carriere()
            .id(longCount.incrementAndGet())
            .nomCariere(UUID.randomUUID().toString())
            .prerequisAcademiques(UUID.randomUUID().toString());
    }
}
