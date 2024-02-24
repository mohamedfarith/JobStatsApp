package com.example.jobstats.data.model

import androidx.compose.ui.graphics.Color


/**
 * A simple API model representing a Job
 *
 * Start and end date time is in ISO 8601 format - Date and time are stored in UTC timezone and
 * expected to be shown on the UI in the local timezone
 */
data class JobApiModel(
    val jobNumber: Int,
    val title: String,
    val startTime: String,
    val endTime: String,
    val status: JobStatus
)

enum class JobStatus {
    YetToStart,
    InProgress,
    Canceled,
    Completed,
    Incomplete
}



