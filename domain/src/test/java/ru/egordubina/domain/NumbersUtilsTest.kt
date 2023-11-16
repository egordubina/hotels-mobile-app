package ru.egordubina.domain

import org.junit.Test

import org.junit.Assert.*
import ru.egordubina.domain.utils.toRubInt

class NumbersUtilsTest {
    @Test
    fun `should return "10 ₽" from 10`() {
        assertEquals("10 ₽", 10.toRubInt())
    }
    @Test
    fun `should return "100 ₽" from 100`() {
        assertEquals("100 ₽", 100.toRubInt())
    }
    @Test
    fun `should return "1 000 ₽" from 1000`() {
        assertEquals("1 000 ₽", 1000.toRubInt())
    }

    @Test
    fun `should return "10 000 ₽" from 10000`() {
        assertEquals("10 000 ₽", 10000.toRubInt())
    }

    @Test
    fun `should return "100 000 ₽" from 1000`() {
        assertEquals("100 000 ₽", 100000.toRubInt())
    }

    @Test
    fun `should return "0 ₽" from 0`() {
        assertEquals("0 ₽", 0.toRubInt())
    }
}