package com.example.jobstats.presentation.ui.jobstatsdashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jobstats.AppConstants
import com.example.jobstats.Helper
import com.example.jobstats.snakeCaseToSentenceWord
import com.example.jobstats.ui.theme.Typography

@Composable
fun JobStatsCard(
    totalJobCount: Int,
    totalCompleted: Int,
    list: List<Helper.JobUiState>,
    jobCardClicked: (List<Helper.JobUiState>) -> Unit
) {
    val sortedList = list.sortedByDescending { it.jobList.size }
    Surface(modifier = Modifier
        .padding(horizontal = 10.dp)
        .clickable { jobCardClicked(list) }) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .clip(shape = RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
        ) {
            Text(
                modifier = Modifier.padding(15.dp),
                text = AppConstants.JOB_STATS,
                style = Typography.bodyMedium,
            )
            Divider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(10.dp))
            JobStatCountComponent(
                totalJobs = totalJobCount.toString(),
                totalCompleted = totalCompleted.toString()
            )
            Column(modifier = Modifier.padding(15.dp)) {
                JobStatComponent(sortedList)
                Spacer(modifier = Modifier.height(20.dp))

                JobSplitComponent(enumList = sortedList)
            }
        }


    }

}

@Composable
fun JobStatCountComponent(totalJobs: String, totalCompleted: String) {
    Row {
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
            text = "$totalJobs ${AppConstants.JOBS}",
            style = Typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
            text = "$totalCompleted out of $totalJobs completed",
            style = Typography.bodySmall, color = Color.Gray
        )

    }
}

@Composable
fun JobStatComponent(enumList: List<Helper.JobUiState>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(5.dp))
            .wrapContentHeight()
    ) {
        enumList.forEach { value ->
            Box(
                modifier = Modifier
                    .weight(value.weightPercent)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(color = value.color)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobSplitComponent(enumList: List<Helper.JobUiState>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 2
    ) {

        (enumList).forEach { state ->
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color = state.color)
                        .clip(shape = RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${state.jobName.snakeCaseToSentenceWord()} (${state.jobList.size})",
                    style = Typography.bodySmall, color = Color.Gray
                )
            }
        }
    }
}


@Preview
@Composable
fun JobStatsCardPreview() {

}