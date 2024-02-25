package com.example.jobstats

import app.cash.turbine.test
import com.example.jobstats.data.model.InvoiceApiModel
import com.example.jobstats.data.model.JobApiModel
import com.example.jobstats.data.remote.datasourceimpl.jobstatsdashboard.JobDataSourceImpl
import com.example.jobstats.data.remote.repositoryimpl.jobstatsdashboard.DashboardRepositoryImpl
import com.example.jobstats.data.remote.usecaseimpl.jobstatsdashboard.InvoiceDetailsUseCaseImpl
import com.example.jobstats.data.remote.usecaseimpl.jobstatsdashboard.JobDetailsUseCaseImpl
import com.example.jobstats.domain.CustomDispatcher
import com.example.jobstats.domain.datasource.jobstatsdashboard.JobDataSource
import com.example.jobstats.domain.repository.jobstatsdashboard.DashboardRepository
import com.example.jobstats.domain.usecase.jobstatsdashboard.InvoiceDetailsUseCase
import com.example.jobstats.domain.usecase.jobstatsdashboard.JobDetailsUseCase
import com.example.jobstats.presentation.ui.jobstatsdashboard.DashBoardViewModel
import junit.framework.TestCase
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/*
* This class tests all the cases for Dashboard ViewModel
* and uses TestDoubles for faking the production scenario for the data
*/
@RunWith(JUnit4::class)
class DashboardViewModelTest {
    private lateinit var dashVM: DashBoardViewModel
    private lateinit var testDispatcher: CustomDispatcher
    private lateinit var jobDetailsUseCase: JobDetailsUseCase
    private lateinit var invoiceDetailsUseCase: InvoiceDetailsUseCase
    private lateinit var dashBoardRepository: DashboardRepository
    private lateinit var dashBordSource: JobDataSource


    @Before
    fun setup() {
        /*since we are already using fake data we can go ahead with this class,
        * in ideal work we have to create Fake Data source to be passed here.
        */
        dashBordSource = JobDataSourceImpl()
        testDispatcher = TestDispatcher()
        dashBoardRepository = DashboardRepositoryImpl(dashBordSource)
        jobDetailsUseCase = JobDetailsUseCaseImpl(dashBoardRepository)
        invoiceDetailsUseCase = InvoiceDetailsUseCaseImpl(dashBoardRepository)
        dashVM = DashBoardViewModel(testDispatcher,jobDetailsUseCase, invoiceDetailsUseCase)
    }

    @Test
    fun `check for initial data for job list is empty`() {
        runTest {
            dashVM.jobList.test {
                val list = awaitItem()
                TestCase.assertEquals(true, list.isEmpty())
            }
        }
    }

    @Test
    fun `check for initial data for invoice list is empty`() {
        runTest {
            dashVM.invoiceList.test {
                val list = awaitItem()
                TestCase.assertEquals(true, list.isEmpty())
            }
        }
    }

    @Test
    fun `get job list, return non empty list`(){
        runTest {
            dashVM.getJobsData()
            dashVM.jobList.test {
                val list = awaitItem()
                TestCase.assertEquals(true, list.isNotEmpty())
            }
        }
    }
    @Test
    fun `get invoice list, return non empty list`(){
        runTest {
            dashVM.getInvoiceList()
            dashVM.invoiceList.test {
                val list = awaitItem()
                TestCase.assertEquals(true, list.isNotEmpty())
            }
        }
    }

}






















