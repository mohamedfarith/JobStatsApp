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

    private fun getDateSuffix(date: Int): String {
        return when (date) {
            1, 21, 31 -> "" + date + "st"
            2, 22 -> "" + date + "nd"
            3, 23 -> "" + date + "rd"
            else -> "" + date + "th"
        }
    }

    fun getCurrentFormattedDate(): String {
        val mCalendar = Calendar.getInstance()
        val dateSuffix = getDateSuffix(mCalendar.get(Calendar.DATE))
        return try {
            SimpleDateFormat("EEEE '$dateSuffix' MMMM yyyy").format(mCalendar.time)
        } catch (e: Exception) {
            ""
        }
    }

    fun getDurationFormattedDate(startTime: String, endTime: String): String {
        return try {
            val start = startTime.let {
                val temp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(it)
                DateTimeFormatter.ofPattern("hh:mm a").format(temp)
            }
            val end = endTime.let {
                val temp = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(it)
                DateTimeFormatter.ofPattern("hh:mm a").format(temp)
            }
            "$start - $end"
        } catch (e: java.lang.Exception) {
            ""
        }
    }


}

//fun String.makeItSentenceCase(){
//    val size  = this.length
//    val subStriing = arrayOf<String>()
//    for(i in 0..<size){
//       if(this[i].isUpperCase()){
//           subStriing
//       }
//    }
//}