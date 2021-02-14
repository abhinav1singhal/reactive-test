package com.reactive.reactivetest;

import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReservationDocumentTest {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


    @Test
    public void persis() throws Exception{

        Flux<Reservation> saved=
        Flux.just(new Reservation(null,"one"), new Reservation(null, "two"))
                .flatMap(r->this.reactiveMongoTemplate.save(r));


        Flux<Reservation> interaction=this.reactiveMongoTemplate
                .dropCollection(Reservation.class)
                .thenMany(saved)
                .thenMany(this.reactiveMongoTemplate.findAll(Reservation.class));

        Predicate<Reservation> predicate=reservation->
                StringUtils.isNotBlank(reservation.getId())&&(
                reservation.getReservationName().equalsIgnoreCase("one")||
                        reservation.getReservationName().equalsIgnoreCase("two"));

        StepVerifier
                .create(interaction)
                .expectNextMatches(predicate)
                .expectNextMatches(predicate)
                .verifyComplete();

    }
}
