package com.xvyashar.tomatick.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Baseline width (like Pixel 4, standard phone)
private const val BASE_SCREEN_WIDTH_DP = 360f

@Composable
@ReadOnlyComposable
private fun screenWidthDp(): Float {
    return LocalConfiguration.current.screenWidthDp.toFloat()
}

val Float.rdp: Dp
    @Composable @ReadOnlyComposable
    get() {
        val scale = screenWidthDp() / BASE_SCREEN_WIDTH_DP
        return (this * scale).dp
    }

val Float.rsp: TextUnit
    @Composable @ReadOnlyComposable
    get() {
        val scale = screenWidthDp() / BASE_SCREEN_WIDTH_DP
        return (this * scale).sp
    }

val Int.rdp: Dp
    @Composable @ReadOnlyComposable
    get() {
        val scale = screenWidthDp() / BASE_SCREEN_WIDTH_DP
        return (this * scale).dp
    }

val Int.rsp: TextUnit
    @Composable @ReadOnlyComposable
    get() {
        val scale = screenWidthDp() / BASE_SCREEN_WIDTH_DP
        return (this * scale).sp
    }

val Double.rdp: Dp
    @Composable @ReadOnlyComposable
    get() {
        val scale = screenWidthDp() / BASE_SCREEN_WIDTH_DP
        return (this * scale).dp
    }

val Double.rsp: TextUnit
    @Composable @ReadOnlyComposable
    get() {
        val scale = screenWidthDp() / BASE_SCREEN_WIDTH_DP
        return (this * scale).sp
    }
