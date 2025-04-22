package com.xvyashar.tomatick

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.xvyashar.tomatick.ui.theme.TomatickTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xvyashar.tomatick.composables.BottomNavItem
import com.xvyashar.tomatick.composables.BottomNavigationBar
import com.xvyashar.tomatick.composables.rdp
import com.xvyashar.tomatick.composables.rsp
import com.xvyashar.tomatick.composables.screens.HomeScreen
import com.xvyashar.tomatick.composables.screens.SettingsScreen
import com.xvyashar.tomatick.services.TimerService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatickTheme {
                MainScreen(this)
            }
        }

        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE)

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK)
        }

        val permissionRequester = PermissionRequester(
            activity = this,
            permissions = permissions,
            onPermissionDenied = {
                Toast.makeText(this, "Permission denied. App can't function without these permissions.", Toast.LENGTH_SHORT).show()
                finish()
            }
        )

        permissionRequester.requestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()


    }
}

@Composable
fun MainScreen(activity: MainActivity?, viewModel: TimerViewModel = viewModel()) {
    if (activity != null) {
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val lifecycle = lifecycleOwner.lifecycle

            val observer = object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    viewModel.registerReceiver(activity.applicationContext)
                }

                override fun onStop(owner: LifecycleOwner) {
                    viewModel.unregisterReceiver(activity.applicationContext)
                }
            }

            lifecycle.addObserver(observer)

            onDispose {
                lifecycle.removeObserver(observer)
                viewModel.unregisterReceiver(activity.applicationContext)
            }
        }
    }

    var selectedTab by remember { mutableStateOf(BottomNavItem.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar (
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        },
        topBar = {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.rdp, vertical = 75.rdp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name).uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 22.rsp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                Row (
                    modifier = Modifier.align(Alignment.CenterEnd),
                    horizontalArrangement = Arrangement.spacedBy(8.rdp)
                ) {
                    IconButton(
                        onClick = {
                            val serviceIntent = Intent(activity, TimerService::class.java).apply {
                                action = TimerService.ACTION_RESET
                            }

                            if (activity != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    activity.startForegroundService(serviceIntent)
                                } else {
                                    activity.startService(serviceIntent)
                                }
                            }
                        },
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.rdp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.reset_vector),
                            contentDescription = "Reset",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            val serviceIntent = Intent(activity, TimerService::class.java).apply {
                                action = TimerService.ACTION_PLAY_PAUSE
                            }

                            if (activity != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    activity.startForegroundService(serviceIntent)
                                } else {
                                    activity.startService(serviceIntent)
                                }
                            }
                        },
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.rdp))
                    ) {
                        Icon(
                            painter = painterResource(if (viewModel.isPaused) R.drawable.play_vector else R.drawable.pause_vector),
                            contentDescription = "Play",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            AnimatedContent(targetState = selectedTab) { screen ->
                when (screen) {
                    BottomNavItem.Home -> HomeScreen()
                    BottomNavItem.Settings -> SettingsScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TomatickTheme {
        MainScreen(null)
    }
}