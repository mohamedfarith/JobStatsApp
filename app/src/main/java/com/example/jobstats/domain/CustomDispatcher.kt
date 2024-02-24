package com.example.jobstats.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CustomDispatcher {
    var main: CoroutineDispatcher
    var io: CoroutineDispatcher
    var default: CoroutineDispatcher
}