package com.example.jobstats.data

import com.example.jobstats.domain.CustomDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/*
* These dispatchers will be used execute coroutines
* View - models in main package and will be overridden with test
* dispatcher in testing module
*/

class CustomDefaultDispatcher @Inject constructor() : CustomDispatcher {

    override var main: CoroutineDispatcher = Dispatchers.Main

    override var io: CoroutineDispatcher = Dispatchers.IO

    override var default: CoroutineDispatcher = Dispatchers.Default

}
