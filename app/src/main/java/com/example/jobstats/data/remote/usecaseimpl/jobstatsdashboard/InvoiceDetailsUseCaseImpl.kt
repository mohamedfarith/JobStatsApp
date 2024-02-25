package com.example.jobstats.data.remote.usecaseimpl.jobstatsdashboard

import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.domain.CustomDispatcher
import com.example.jobstats.domain.repository.jobstatsdashboard.DashboardRepository
import com.example.jobstats.domain.usecase.jobstatsdashboard.InvoiceDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InvoiceDetailsUseCaseImpl @Inject constructor(
    private val repository: DashboardRepository
) :
    InvoiceDetailsUseCase {
    override suspend fun getInvoiceList(dispatcher: CustomDispatcher): Flow<List<InvoiceApiModel>> {
        return withContext(dispatcher.default) {
            repository.getInvoiceDetails()
        }
    }


}