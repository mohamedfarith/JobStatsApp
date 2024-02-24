package com.example.jobstats.domain.usecase.jobstatsdashboard

import com.example.jobstats.data.model.JobApiModel
import kotlinx.coroutines.flow.Flow

interface JobDetailsUseCase {
    suspend fun fetchDashBoardDetails(): Flow<List<JobApiModel>>
}