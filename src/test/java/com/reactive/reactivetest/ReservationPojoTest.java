package com.reactive.reactivetest;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

public class ReservationPojoTest {

    @Test
    public void create() throws Exception{
        Reservation reservation=new Reservation(null, "Jane");
        Assert.assertNull(reservation.getId());
        Assert.assertThat(reservation.getReservationName(), Matchers.equalTo("Jane"));
        Assertions.assertThat(reservation.getReservationName()).isEqualToIgnoringCase("Jane");
    }
}
