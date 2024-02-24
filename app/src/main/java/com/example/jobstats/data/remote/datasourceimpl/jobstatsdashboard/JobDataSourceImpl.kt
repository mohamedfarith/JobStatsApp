package com.example.jobstats.data.remote.datasourceimpl.jobstatsdashboard

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.data.remote.SampleData
import com.example.jobstats.domain.datasource.jobstatsdashboard.JobDataSource
import javax.inject.Inject
import javax.sql.DataSource
import kotlin.time.Duration.Companion.seconds

class JobDataSourceImpl @Inject constructor() : JobDataSource {

    fun observeJobs(): Flow<List<JobApiModel>> {
        return flow {
            while (true) {
                emit(SampleData.generateRandomJobList(20))
                delay(30.seconds.inWholeMilliseconds)
            }
        }
    }

    fun observeInvoices(): Flow<List<InvoiceApiModel>> {
        return flow {
            while (true) {
                emit(SampleData.generateRandomInvoiceList(20))
                delay(30.seconds.inWholeMilliseconds)
            }
        }
    }

    fun getJobs(): List<JobApiModel> {
        return SampleData.generateRandomJobList(50)
    }

    override suspend fun getJobDetails(): Flow<List<JobApiModel>> {
        return flow {
            emit(SampleData.generateRandomJobList(20))
        }
    }

    override suspend fun getInvoiceDetails(): Flow<List<InvoiceApiModel>> {
        return flow {
            emit(SampleData.generateRandomInvoiceList(20))
        }
    }
}
