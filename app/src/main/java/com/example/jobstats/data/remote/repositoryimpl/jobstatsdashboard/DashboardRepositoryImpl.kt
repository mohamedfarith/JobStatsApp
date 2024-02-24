package com.example.jobstats.data.remote.repositoryimpl.jobstatsdashboard

import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.domain.datasource.jobstatsdashboard.JobDataSource
import com.example.jobstats.domain.repository.jobstatsdashboard.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(private val datasource: JobDataSource) :
    DashboardRepository {
    override suspend fun getDashboardDetails(): Flow<List<JobApiModel>> {
        return datasource.getJobDetails()
    }

    override suspend fun getInvoiceDetails(): Flow<List<InvoiceApiModel>> {
       return datasource.getInvoiceDetails()
    }

}