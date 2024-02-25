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
import androidx.navigation.navArgument
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

            NavHost(
                navController = controller,
                startDestination = JobDashBoardRoute.JOB_DASHBOARD
            ) {
                composable(JobDashBoardRoute.JOB_DASHBOARD) {

                    JobDashboardScreen(
                        dashVM = dashVM,
                        jobStatsCardClicked = {
                            controller.currentBackStackEntry?.savedStateHandle?.set("jobList", it)
                            controller.navigate(route = JobDashBoardRoute.JOB_DESCRIPTION)
                        },
                        invoiceStatCardClicked = {
                            // no action as of now
                        })
                }
                composable(JobDashBoardRoute.JOB_DESCRIPTION) {
                    val list =
                        controller.previousBackStackEntry?.savedStateHandle?.get<List<Helper.JobUiState>>(
                            "jobList"
                        )
                    JobDescriptionScreen(
                        totalCompleted = list?.firstOrNull { it.jobName == JobStatus.Completed.name }?.jobList?.size
                            ?: 0,
                        totalJobCount = list?.sumOf { it.jobList.size } ?: 0,
                        enumList = list ?: emptyList()
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