package com.example.jobstats.domain.usecase.jobstatsdashboard

import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.domain.CustomDispatcher
import kotlinx.coroutines.flow.Flow

interface InvoiceDetailsUseCase {
    suspend fun getInvoiceList(dispatcher: CustomDispatcher): Flow<List<InvoiceApiModel>>
}