package memoizrlabs.com.tdd_playground.string_calculator

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.Integer.parseInt
import java.util.regex.Pattern

class StringCalculatorTest {

    @Rule @JvmField
    val thrown = ExpectedException.none()

    @Test
    fun withEmptyString_returnsZero() {
        assertThat(add(""), equalTo(0))
    }

    @Test
    fun withOneNumberReturnsNumber() {
        assertThat(add("1"), equalTo(1))
        assertThat(add("42"), equalTo(42))
        assertThat(add("7"), equalTo(7))
    }

    @Test
    fun withTwoNumbersReturnsSumOfNumbers() {
        assertThat(add("1,8"), equalTo(9))
    }

    @Test
    fun withSeveralNumbersReturnsSumOfNumbers() {
        assertThat(add("1,8,3"), equalTo(12))
        assertThat(add("1,1,1,1,6"), equalTo(10))
    }

    @Test
    fun allowsNewLinesAsSeparator() {
        assertThat(add("1\n1\n1\n1\n6"), equalTo(10))
        assertThat(add("1\n1,1,1\n6"), equalTo(10))
    }

    @Test
    fun allowCustomDelimiter() {
        assertThat(add("//[;]\n1;2"), equalTo(3))
    }

    @Test
    fun customDelimiterCanBeAnySize() {
        assertThat(add("//[****]\n1****2"), equalTo(3))
    }

    @Test
    fun allowsMultipleCustomSeparators() {
        assertThat(add("//[****][;;]\n1****2;;4"), equalTo(7))
    }

    @Test
    fun throwsWhenNegativeNumberIsInserted() {
        thrown.expect(IllegalArgumentException::class.java)
        thrown.expectMessage("negatives not allowed, -8, -9")
        add("1,-8,-9")
    }

    @Test
    fun ignoresNubersBiggerThan1000() {
        assertThat(add("2,1001"), equalTo(2))
        assertThat(add("2,1000"), equalTo(1002))
    }
}

fun add(input: String): Int {

    return if (input.isEmpty()) 0 else {
        val numbers = input.split().map { parseInt(it) }.filter { it <= 1000 }
        if (numbers.any { it < 0 }) throw IllegalArgumentException("negatives not allowed, ${numbers.filter { it < 0 }.joinToString(", ") }")
        return numbers.sum()
    }
}

private fun String.split() = if (startsWith("//")) dropWhile { it.toString() != "\n" }.drop(1).split(*parseSeparator(this)) else split(",", "\n")
private fun parseSeparator(input: String): Array<String> {
    val matcher = Pattern.compile("\\[(.*?)\\]").matcher(input)
    val output = mutableListOf<String>()
    while (matcher.find()) {
        output.add(matcher.group(1))
    }
    return output.toTypedArray()
}
