package com.xvyashar.tomatick.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.xvyashar.tomatick.constants.BottomNavItem

@Composable
fun BottomNavigationBar(
    selectedTab: BottomNavItem,
    onTabSelected: (BottomNavItem) -> Unit
) {
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                selected = item == selectedTab,
                onClick = { onTabSelected(item) },
                icon = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.rdp),
                        modifier = Modifier.padding(horizontal = 8.rdp, vertical = 8.rdp)
                    ) {
                        Icon(imageVector = item.icon, contentDescription = item.label)

                        if (selectedTab == item) {
                            Text(item.label, fontSize = 14.rsp)
                        }
                    }
                },
                label = null,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}