package com.example.hospital_management_system;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

class FirstTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all tests");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before each test");
    }

    @Test
    void testAddition() {
        int result = 10 + 20;
        assertThat(result).isEqualTo(30);
    }

    @Test
    void testSubtraction() {
        int result = 30 - 10;
        assertThat(result).isEqualTo(20);
    }

    @AfterEach
    void afterEach() {
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all tests");
    }
}