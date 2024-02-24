package com.example.jobstats.presentation.di.jobstatsdashboard

import com.example.jobstats.data.CustomDefaultDispatcher
import com.example.jobstats.data.remote.datasourceimpl.jobstatsdashboard.JobDataSourceImpl
import com.example.jobstats.data.remote.repositoryimpl.jobstatsdashboard.DashboardRepositoryImpl
import com.example.jobstats.data.remote.usecaseimpl.jobstatsdashboard.InvoiceDetailsUseCaseImpl
import com.example.jobstats.data.remote.usecaseimpl.jobstatsdashboard.JobDetailsUseCaseImpl
import com.example.jobstats.domain.CustomDispatcher
import com.example.jobstats.domain.datasource.jobstatsdashboard.JobDataSource
import com.example.jobstats.domain.repository.jobstatsdashboard.DashboardRepository
import com.example.jobstats.domain.usecase.jobstatsdashboard.InvoiceDetailsUseCase
import com.example.jobstats.domain.usecase.jobstatsdashboard.JobDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class DashBoardModule {


    @Binds
    abstract fun provideJobDetailsUseCase(dashboardUseCase: JobDetailsUseCaseImpl): JobDetailsUseCase

    @Binds
    abstract fun provideInvoiceDetailsUseCase(invoiceUseCase: InvoiceDetailsUseCaseImpl): InvoiceDetailsUseCase

    @Binds
    abstract fun provideDashboardRepository(dashboardRepo: DashboardRepositoryImpl): DashboardRepository

    @Binds
    abstract fun provideDashboardDataSource(dashboardDataSource: JobDataSourceImpl): JobDataSource

    @Binds
    abstract fun provideDispatcher(dispatcher: CustomDefaultDispatcher): CustomDispatcher
}