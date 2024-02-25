package com.example.jobstats

import com.example.jobstats.data.remote.SampleData
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


@RunWith(JUnit4::class)
class HelperTest {

    //snakeCaseToSentenceWord
    @Test
    fun `sentenceCase for empty string return blankString`() {
        TestCase.assertEquals("", "".snakeCaseToSentenceWord())
    }

    @Test
    fun `sentenceCase for no_capital_word string return sameString`() {
        TestCase.assertEquals("one", "one".snakeCaseToSentenceWord())
    }

    @Test
    fun `sentenceCase for sentence_spaced_word string return sameString`() {
        TestCase.assertEquals("He is a hero", "He is a hero".snakeCaseToSentenceWord())
    }

    @Test
    fun `sentenceCase for snake case string return sentence case`() {
        TestCase.assertEquals("Yet To Start", "YetToStart".snakeCaseToSentenceWord())
    }

    //getDurationFormattedDate

    @Test
    fun `check for invalid format return blankString`() {
        TestCase.assertEquals(
            true,
            Helper.getDurationFormattedDate(
                fromFormat = "abc",
                toFormat = "ced",
                startTime = "",
                endTime = ""
            ) == ""
        )
    }

    @Test
    fun `check for valid format return notBlankString`() {
        val isoFormatter = DateTimeFormatter.ofPattern(AppConstants.ISO_TIME_FORMAT)
        val startTime = LocalDateTime.now().plusDays(Random.nextLong(1, 30)).format(isoFormatter)
        val endTime = LocalDateTime.now().plusDays(Random.nextLong(31, 60)).format(isoFormatter)
        TestCase.assertEquals(
            true,
            Helper.getDurationFormattedDate(
                fromFormat = AppConstants.ISO_TIME_FORMAT,
                toFormat = AppConstants.TIME_FORMAT,
                startTime = startTime,
                endTime = endTime
            ).isNotBlank()
        )
    }

    //getCurrentFormattedDate
    @Test
    fun `check for invalid pattern return emptyString`() {
        TestCase.assertEquals(
            true,
            Helper.getCurrentFormattedDate(date = 1, pattern = "abcd").isBlank()
        )
    }

    @Test
    fun `check for valid pattern return nonEmptyString`() {
        TestCase.assertEquals(true, Helper.getCurrentFormattedDate().isNotBlank())
    }

    //getDateSuffix
    @Test
    fun `check for invalid number return blankString`(){
        TestCase.assertEquals("",Helper.getDateSuffix(-1))
    }
    @Test
    fun `check for 1 return 1st`(){
        TestCase.assertEquals("1st",Helper.getDateSuffix(1))
    }
    @Test
    fun `check for 2 return 2nd`(){
        TestCase.assertEquals("2nd",Helper.getDateSuffix(2))
    }
    @Test
    fun `check for 3 return 3rd`(){
        TestCase.assertEquals("3rd",Helper.getDateSuffix(3))
    }


}

















