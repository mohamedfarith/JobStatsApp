package com.example.jobstats

import com.example.jobstats.data.model.JobApiModel
import java.text.DateFormat
import java.text.SimpleDateFormat


data class JobApiModelUiDto(val jobNumber: String, val title: String, val duration: String)

fun JobApiModel.toUiDataDto(): JobApiModelUiDto {

    return JobApiModelUiDto(
        jobNumber = "#${this.jobNumber.nonNullString()}",
        title = this.title,
        duration = Helper.getDurationFormattedDate(
            fromFormat = AppConstants.ISO_TIME_FORMAT,
            toFormat = AppConstants.TIME_FORMAT,
            this.startTime,
            this.endTime
        )
    )
}

fun Any?.nonNullString(): String {
    return this?.toString() ?: ""
}