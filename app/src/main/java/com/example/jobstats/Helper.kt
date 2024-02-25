package com.example.jobstats

import androidx.compose.ui.graphics.Color
import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.InvoiceStatus
import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.data.model.JobStatus
import com.example.jobstats.ui.theme.StatBlue
import com.example.jobstats.ui.theme.StatGreen
import com.example.jobstats.ui.theme.StatPurple
import com.example.jobstats.ui.theme.StatYellow
import com.example.jobstats.ui.theme.StateRed
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

object Helper {

    data class JobUiState(
        val color: Color,
        val jobName: String,
        val jobList: List<JobApiModel> = emptyList(),
        val weightPercent: Float
    )

    data class InvoiceItemUiDto(
        val color: Color,
        val jobName: String,
        val total: Int,
        val weightPercent: Float
    )

    fun getEnumMappedJobList(list: List<JobApiModel>): List<JobUiState> {
        val colors =
            listOf(StatPurple, StatBlue, StatYellow, StatGreen, StateRed)
        val mList: ArrayList<JobUiState> = arrayListOf()
        JobStatus.entries.toTypedArray().forEachIndexed { index, value ->
            val tempList = list.filter { it.status.name == value.name }
            if (tempList.isNotEmpty())
                mList.add(
                    JobUiState(
                        color = colors[index],
                        jobName = value.name,
                        jobList = tempList,
                        weightPercent = (tempList.size.toDouble() / list.size.toDouble()).toFloat()
                    )
                )

        }
        return mList
    }

    fun getEnumMappedInvoiceList(list: List<InvoiceApiModel>): List<InvoiceItemUiDto> {
        val colors =
            listOf(StatPurple, StatBlue, StatYellow, StatGreen, StateRed)
        val mList: ArrayList<InvoiceItemUiDto> = arrayListOf()
        InvoiceStatus.entries.toTypedArray().forEachIndexed { index, value ->
            val tempList = list.filter { it.status.name == value.name }
            if (tempList.isNotEmpty())
                mList.add(
                    InvoiceItemUiDto(
                        color = colors[index],
                        jobName = value.name,
                        total = tempList.sumOf { it.total },
                        weightPercent = (tempList.size.toDouble() / list.size.toDouble()).toFloat()
                    )
                )

        }
        return mList
    }

    fun getDateSuffix(date: Int): String {
        return if (date < 0) return ""
        else when (date) {
            1, 21, 31 -> "" + date + "st"
            2, 22 -> "" + date + "nd"
            3, 23 -> "" + date + "rd"
            else -> "" + date + "th"
        }
    }

    fun getCurrentFormattedDate(
        date: Int = Calendar.getInstance().get(Calendar.DATE),
        pattern: String = AppConstants.WELCOME_FORMAT_DATE
    ): String {
        val dateSuffix = getDateSuffix(date)
        return try {
            DateTimeFormatter.ofPattern(pattern.replace("{ddd}", "'${dateSuffix}'"))
                .format(LocalDateTime.now())
        } catch (e: Exception) {
            ""
        }
    }

    fun getDurationFormattedDate(
        fromFormat: String,
        toFormat: String,
        startTime: String,
        endTime: String
    ): String {
        return try {
            val start = startTime.let {
                val temp = DateTimeFormatter.ofPattern(fromFormat).parse(it)
                DateTimeFormatter.ofPattern("hh:mm a").format(temp)
            }
            val end = endTime.let {
                val temp = DateTimeFormatter.ofPattern(fromFormat).parse(it)
                DateTimeFormatter.ofPattern(toFormat).format(temp)
            }
            "$start - $end"
        } catch (e: java.lang.Exception) {
            ""
        }
    }


}

fun String.snakeCaseToSentenceWord(): String {
    var ans = ""
    for (i in indices) {
        if (this[i].isUpperCase())
            ans += " " + this[i]
        else
            ans += this[i]
    }
    return ans.trim()
}
