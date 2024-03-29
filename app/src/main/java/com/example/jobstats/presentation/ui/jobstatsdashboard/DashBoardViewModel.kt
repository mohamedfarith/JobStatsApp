package com.example.jobstats.presentation.ui.jobstatsdashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.domain.CustomDispatcher
import com.example.jobstats.domain.usecase.jobstatsdashboard.InvoiceDetailsUseCase
import com.example.jobstats.domain.usecase.jobstatsdashboard.JobDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val dispatcher: CustomDispatcher,
    private val jobStatUseCase: JobDetailsUseCase,
    private val invoiceStatUseCase: InvoiceDetailsUseCase
) : ViewModel() {


    private var _jobList = MutableStateFlow<List<JobApiModel>>(emptyList())
    val jobList = _jobList.asStateFlow()

    private var _invoiceList = MutableStateFlow<List<InvoiceApiModel>>(emptyList())
    val invoiceList = _invoiceList.asStateFlow()

    fun getJobsData() {
        viewModelScope.launch(dispatcher.default) {
            jobStatUseCase.fetchDashBoardDetails(dispatcher = dispatcher).collectLatest {
                _jobList.value = it
            }
        }
    }

    fun getInvoiceList() {
        viewModelScope.launch(dispatcher.default) {
            invoiceStatUseCase.getInvoiceList(dispatcher = dispatcher).collectLatest {
                _invoiceList.value = it
            }
        }
    }
}