package com.xvyashar.tomatick.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xvyashar.tomatick.view_models.TimerViewModel
import com.xvyashar.tomatick.composables.Timer
import com.xvyashar.tomatick.composables.rdp
import com.xvyashar.tomatick.composables.rsp

@Composable
fun HomeScreen(viewModel: TimerViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.rdp)
        ) {
            Timer(color = MaterialTheme.colorScheme.primary, size = 232.rdp, text = viewModel.timerText, progress = viewModel.timerProgress)

            CustomTabRow(tabs = listOf("Short Break", "Pomodoro", "Long Break"), selectedTabIndex = viewModel.stateIndex) { }
        }
    }
}

@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val indicatorHeight = 6.rdp
    val indicatorWidth = 6.rdp

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            val currentTab = tabPositions[selectedTabIndex]
            Box(
                Modifier
                    .tabIndicatorOffset(currentTab)
                    .padding(horizontal = (currentTab.width - indicatorWidth) / 2)
                    .height(indicatorHeight)
                    .width(indicatorWidth)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    )
            )
        },
        divider = {},
        modifier = Modifier.padding(start = 28.rdp, end = 28.rdp)
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabSelected(index) },
                enabled = false,
                text = {
                    Text(
                        text = title,
                        fontSize = 12.rsp,
                        color = if (index == selectedTabIndex)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            )
        }
    }
}