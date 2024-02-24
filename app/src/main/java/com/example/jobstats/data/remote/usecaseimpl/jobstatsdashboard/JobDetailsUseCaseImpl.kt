package com.example.jobstats.data.remote.usecaseimpl.jobstatsdashboard

import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.domain.CustomDispatcher
import com.example.jobstats.domain.repository.jobstatsdashboard.DashboardRepository
import com.example.jobstats.domain.usecase.jobstatsdashboard.JobDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
* This class is the place where business logic of
*  what and how data should be fetched is decided
 */

class JobDetailsUseCaseImpl @Inject constructor(
    private val dispatcher: CustomDispatcher,
    private val repository: DashboardRepository
) : JobDetailsUseCase {
    override suspend fun fetchDashBoardDetails(): Flow<List<JobApiModel>> {
        return withContext(dispatcher.default) {
            repository.getDashboardDetails()
        }
    }
}