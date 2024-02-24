package com.example.jobstats.presentation.ui.jobstatsdashboard

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jobstats.Helper
import com.example.jobstats.data.model.JobStatus
import com.example.jobstats.toUiDataDto
import com.example.jobstats.ui.theme.Typography


@Composable
fun JobDescriptionScreen(
    totalJobCount: Int, enumList: List<Helper.JobUiState>, totalCompleted: Int
) {
    val dashTitle = "Jobs(${totalJobCount})"
    val navController = rememberNavController()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Scaffold(topBar = {
        JobDescriptionAppBar(title = dashTitle) {
            backPressedDispatcher?.onBackPressed()
        }
    }) {
        Column(modifier = Modifier.padding(it)) {


            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                JobStatCountComponent(
                    totalJobs = totalJobCount.toString(),
                    totalCompleted = totalCompleted.toString()
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            JobStatComponent(
                modifier = Modifier.padding(horizontal = 20.dp),
                enumList = enumList
            )
            Spacer(modifier = Modifier.height(25.dp))
            Divider(modifier = Modifier.height(1.dp), color = Color.LightGray)
            HorizontalTabs(jobList = enumList, navController = navController)
            NavHost(navController = navController, startDestination = enumList[0].jobName) {
                enumList.forEach { state ->
                    composable(route = state.jobName) {
                        ItemContent(state = state)
                    }
                }
            }

        }
    }


}

@Composable
fun ItemContent(state: Helper.JobUiState) {
    val uiModelList = state.jobList.map { it.toUiDataDto() }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
        items(ArrayList(uiModelList)) { uiDto ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(5.dp), elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Column {
                            Text(text = uiDto.jobNumber)
                            Text(text = uiDto.title)
                            Text(text = uiDto.duration)
                        }
                    }
                }
            }

        }
    }


}

@Composable
fun HorizontalTabs(jobList: List<Helper.JobUiState>, navController: NavController) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val entry by navController.currentBackStackEntryAsState()
    val currentTab = entry?.destination?.route
    ScrollableTabRow(selectedTabIndex = selectedTabIndex.intValue, edgePadding = 5.dp) {
        jobList.forEachIndexed { index, value ->
            Tab(selected = currentTab == value.jobName, onClick = {
                if (currentTab != value.jobName) {
                    selectedTabIndex.intValue = index
                    navController.navigate(value.jobName) {
                        popUpTo(entry?.destination?.route ?: JobStatus.YetToStart.name) {
                            inclusive = true
                        }
                        launchSingleTop = true

                    }
                }
            }) {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = "${value.jobName} (${value.jobList.size})",
                    style = Typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedTabIndex.intValue == index) Color.Black else Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDescriptionAppBar(title: String, onBackClicked: () -> Unit) {
    TopAppBar(modifier = Modifier.shadow(elevation = 2.dp), title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable { onBackClicked() },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back Button"
            )
            Text(
                text = title,
                color = Color.Black,
                style = Typography.titleSmall,
                fontWeight = FontWeight.Medium
            )

        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White))
}

