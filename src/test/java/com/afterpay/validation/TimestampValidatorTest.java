package com.afterpay.validation;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimestampValidatorTest {

    private TimestampValidator timestampValidator = new TimestampValidator();

    @Test
    public void shouldFailTimestampValidation() throws Exception {

        assertFalse(timestampValidator.isValidTimestamp("2014-12-01"));

        assertFalse(timestampValidator.isValidTimestamp("2014-12-01T00:01"));
        assertFalse(timestampValidator.isValidTimestamp(""));

        assertFalse(timestampValidator.isValidTimestamp(null));
    }

    @Test
    public void shouldPassTimestampValidation() {
        assertTrue(timestampValidator.isValidTimestamp("2014-04-29T13:15:54"));
    }

    @Test
    public void shouldFailTimeInPast() {
        assertFalse(timestampValidator.isTimeInPast("3014-04-29T13:15:54"));
    }

    @Test
    public void shouldPassTimeInPast() {
        assertTrue(timestampValidator.isTimeInPast("2014-04-29T13:15:54"));
    }

    @Test
    public void shouldFailTimeForDay() throws Exception {
        Date day = timestampValidator.getDayFormat().parse("2016-12-12");

        assertFalse(timestampValidator.isTimestampForDay("2016-11-12T13:15:54", day));
    }

    @Test
    public void shouldPassTimeForDay() throws Exception {
        Date day = timestampValidator.getDayFormat().parse("2016-12-12");

        assertTrue(timestampValidator.isTimestampForDay("2016-12-12T13:15:54", day));
    }
}
