package com.example.jobstats.domain.usecase.jobstatsdashboard

import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.domain.CustomDispatcher
import kotlinx.coroutines.flow.Flow

interface JobDetailsUseCase {
    suspend fun fetchDashBoardDetails(dispatcher: CustomDispatcher): Flow<List<JobApiModel>>
}