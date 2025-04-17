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
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.xvyashar.tomatick.composables.Timer

@Composable
fun HomeScreen() {
    var selected by remember { mutableIntStateOf(1) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            Timer(color = MaterialTheme.colorScheme.primary, size = 256.dp)

            CustomTabRow(tabs = listOf("Short break", "Pomodoro", "Long break"), selectedTabIndex = selected) { selectedIndex ->
                selected = selectedIndex
            }
        }
    }
}

@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val indicatorHeight = 6.dp
    val indicatorWidth = 6.dp

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
        modifier = Modifier.padding(start = 32.dp, end = 32.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabSelected(index) },
                enabled = false,
                text = {
                    Text(
                        text = title,
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