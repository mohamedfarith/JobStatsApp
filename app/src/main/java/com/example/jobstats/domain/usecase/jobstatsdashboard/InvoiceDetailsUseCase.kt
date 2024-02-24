package com.example.jobstats.domain.usecase.jobstatsdashboard

import com.example.jobstats.data.model.InvoiceApiModel
import kotlinx.coroutines.flow.Flow

interface InvoiceDetailsUseCase {
    suspend fun getInvoiceList(): Flow<List<InvoiceApiModel>>
}