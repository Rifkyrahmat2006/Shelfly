package com.shelfly.app.core.component

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ShelfInputValidationTest {

    @Test
    fun emptyNameIsInvalid() {
        assertFalse(ShelfInputValidation.isValidName(""))
        assertFalse(ShelfInputValidation.isValidName("   "))
    }

    @Test
    fun nonEmptyNameIsValid() {
        assertTrue(ShelfInputValidation.isValidName("Pemrograman Mobile"))
    }
}
