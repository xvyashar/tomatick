package com.xvyashar.tomatick.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.xvyashar.tomatick.constants.BottomNavItem

class UiViewModel : ViewModel() {

    var selectedTab by mutableStateOf(BottomNavItem.Home)
        private set

    fun selectTab(tab: BottomNavItem) {
        selectedTab = tab
    }
}