package com.example.jobstats.presentation.ui.jobstatsdashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jobstats.AppConstants
import com.example.jobstats.Helper
import com.example.jobstats.data.model.JobStatus
import com.example.jobstats.ui.theme.Typography

/*
* This screen is the host for showing the dashboard
* for the job lists.
*/
@Composable
fun JobDashboardScreen(
    dashVM:DashBoardViewModel = hiltViewModel(),
    jobStatsCardClicked: (List<Helper.JobUiState>) -> Unit,
    invoiceStatCardClicked: () -> Unit
) {
    val jobData = dashVM.jobList.collectAsStateWithLifecycle()
    val jobsList = Helper.getEnumMappedJobList(jobData.value)
    val invoiceData = dashVM.invoiceList.collectAsStateWithLifecycle()
    val invoiceList = Helper.getEnumMappedInvoiceList(invoiceData.value)
    val totalCompleted =
        jobsList.firstOrNull() { it.jobName == JobStatus.Completed.name }?.jobList?.size
            ?: -1
    LaunchedEffect(key1 = Unit) {
            dashVM.getJobsData()
            dashVM.getInvoiceList()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        topBar = { DashboardTopAppBar(AppConstants.DASHBOARD_TITLE) }) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))
            ProfileNameCard(
                greeting = "Hello",
                name = "James",
                dateString = Helper.getCurrentFormattedDate()
            )
            JobStatsCard(
                totalJobCount = jobData.value.size,
                totalCompleted = totalCompleted,
                list = jobsList,
                jobStatsCardClicked
            )
            InvoiceStatCard(invoiceList, invoiceStatCardClicked)

        }
    }

}

@Composable
fun ProfileNameCard(greeting: String, name: String, dateString: String) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(color = Color.White)
            .wrapContentHeight(),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("$greeting, $name", style = Typography.titleSmall)
                Text(dateString, style = Typography.bodySmall, color = Color.Gray)
            }
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "User Image",
                Modifier
                    .size(50.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .padding(7.dp)
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopAppBar(title: String) {
    TopAppBar(modifier = Modifier.shadow(elevation = 2.dp), title = {
        Text(
            text = title,
            color = Color.Black,
            style = Typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White))

}

@Preview
@Composable
fun JobDashboardScreenPreview() {
    JobDashboardScreen(
        jobStatsCardClicked = { },
        invoiceStatCardClicked = {})
}

