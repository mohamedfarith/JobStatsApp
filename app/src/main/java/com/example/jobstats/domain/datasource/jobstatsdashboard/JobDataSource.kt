package com.example.jobstats.domain.datasource.jobstatsdashboard

import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.JobApiModel
import kotlinx.coroutines.flow.Flow

interface JobDataSource {

    suspend fun getJobDetails(): Flow<List<JobApiModel>>

    suspend fun getInvoiceDetails():Flow<List<InvoiceApiModel>>
}