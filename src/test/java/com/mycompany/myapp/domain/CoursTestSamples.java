package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CoursTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cours getCoursSample1() {
        return new Cours().id(1L).nomCours("nomCours1").description("description1");
    }

    public static Cours getCoursSample2() {
        return new Cours().id(2L).nomCours("nomCours2").description("description2");
    }

    public static Cours getCoursRandomSampleGenerator() {
        return new Cours().id(longCount.incrementAndGet()).nomCours(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
