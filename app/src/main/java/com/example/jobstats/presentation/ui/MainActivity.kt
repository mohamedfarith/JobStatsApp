package com.example.jobstats.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobstats.Helper
import com.example.jobstats.R
import com.example.jobstats.data.model.JobStatus
import com.example.jobstats.presentation.ui.jobstatsdashboard.DashBoardViewModel
import com.example.jobstats.presentation.ui.jobstatsdashboard.JobDashBoardRoute
import com.example.jobstats.presentation.ui.jobstatsdashboard.JobDashboardScreen
import com.example.jobstats.presentation.ui.jobstatsdashboard.JobDescriptionScreen
import com.example.jobstats.ui.theme.JobStatsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var dashVM: DashBoardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        setContent {
            val controller = rememberNavController()
            dashVM = hiltViewModel()
            val jobData = dashVM.jobList.collectAsState()
            val jobsList = Helper.getEnumMappedJobList(jobData.value)
            val invoiceData = dashVM.invoiceList.collectAsState()
            val invoiceList = Helper.getEnumMappedInvoiceList(invoiceData.value)
            val totalCompleted =
                jobsList.firstOrNull() { it.jobName == JobStatus.Completed.name }?.jobList?.size
                    ?: -1
            val lifecycleOwner = LocalLifecycleOwner.current.lifecycle
            val latestEvent = remember {
                mutableStateOf(Lifecycle.Event.ON_ANY)
            }
            DisposableEffect(lifecycleOwner) {
                val lifecycleObserver =
                    LifecycleEventObserver { _, event -> latestEvent.value = event }
                lifecycleOwner.addObserver(lifecycleObserver)
                onDispose { lifecycleOwner.removeObserver(lifecycleObserver) }
            }
            LaunchedEffect(key1 = Unit) {
                if (latestEvent.value == Lifecycle.Event.ON_RESUME) {
                    dashVM.getJobsData()
                    dashVM.getInvoiceList()
                }
            }

            NavHost(
                navController = controller,
                startDestination = JobDashBoardRoute.JOB_DASHBOARD
            ) {
                composable(JobDashBoardRoute.JOB_DASHBOARD) {

                    JobDashboardScreen(
                        jobList = jobsList,
                        jobStatsCardClicked = {
                            controller.navigate(route = JobDashBoardRoute.JOB_DESCRIPTION)
                        },
                        invoiceList = invoiceList,
                        totalJobCount = jobData.value.size,
                        totalCompleted = totalCompleted,
                        invoiceStatCardClicked = {
                            // no action as of now
                        })
                }
                composable(JobDashBoardRoute.JOB_DESCRIPTION) {
                    JobDescriptionScreen(
                        totalCompleted = totalCompleted,
                        totalJobCount = jobData.value.size,
                        enumList = jobsList
                    )
                }
            }
        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JobStatsTheme {
        Greeting("Android")
    }
}