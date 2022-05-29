package com.orson.swechallenge.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstraintsCheckTest {

    @Test
    public void testValidSort() {
       boolean isValidSort = ConstraintsCheck.checkIfValidSort("name");
        assertThat(isValidSort).isTrue();

        isValidSort = ConstraintsCheck.checkIfValidSort("NAME");
        assertThat(isValidSort).isTrue();

        isValidSort = ConstraintsCheck.checkIfValidSort("salary");
        assertThat(isValidSort).isTrue();

        isValidSort = ConstraintsCheck.checkIfValidSort("SALARY");
        assertThat(isValidSort).isTrue();

        isValidSort = ConstraintsCheck.checkIfValidSort("");
        assertThat(isValidSort).isTrue();

    }

    @Test
    public void testInValidSort() {
        boolean isValidSort = ConstraintsCheck.checkIfValidSort("hehe");
        assertThat(isValidSort).isFalse();

        isValidSort = ConstraintsCheck.checkIfValidSort("11111");
        assertThat(isValidSort).isFalse();

    }

    @Test
    public void testValidArgumentType() {
        String message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "2000.0", "0", "0");
        assertThat(message).isEqualTo("Valid");

    }

    @Test
    public void testInValidArgumentType() {
        String message = ConstraintsCheck.checkIfValidArgumentType("haha", "2000.0", "0", "0");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "haha", "0", "0");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "2000.0", "haha", "0");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "2000.0", "0", "haha");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("haha", "haha", "haha", "haha");
        assertThat(message).isEqualTo("Invalid Input Type");

    }

    @Test
    public void testInfiniteArgumentType() {
        String message = ConstraintsCheck.checkIfValidArgumentType("1234567890123456789012345678901234567890123456789", "2000.0", "0", "0");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "20001234567890123456789012345678901234567890123456789", "0", "0");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "2000.0", "1234567890123456789012345678901234567890123456789", "0");
        assertThat(message).isEqualTo("Invalid Input Type");

        message = ConstraintsCheck.checkIfValidArgumentType("1000.0", "2000.0", "0", "1234567890123456789012345678901234567890123456789");
        assertThat(message).isEqualTo("Invalid Input Type");
    }

}
