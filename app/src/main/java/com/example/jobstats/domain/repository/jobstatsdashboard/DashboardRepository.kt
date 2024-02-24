package com.example.jobstats.domain.repository.jobstatsdashboard

import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.JobApiModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {

    suspend fun getDashboardDetails(): Flow<List<JobApiModel>>
    suspend fun getInvoiceDetails(): Flow<List<InvoiceApiModel>>
}