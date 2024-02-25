package com.example.jobstats

import com.example.jobstats.domain.CustomDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatcher() : CustomDispatcher {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    override var main: CoroutineDispatcher = testDispatcher

    override var io: CoroutineDispatcher = testDispatcher

    override var default: CoroutineDispatcher = testDispatcher

}