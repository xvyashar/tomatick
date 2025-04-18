package com.xvyashar.tomatick.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 200.dp
) {
    CircularContainer(color = color, size = size, modifier = modifier) {
        TimerIndicator(Modifier, 1f, "25:00")
    }
}

@Composable
fun CircularContainer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 200.dp,
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Box(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                // Outer soft shadow
                shadowElevation = 32f
                shape = CircleShape
                clip = true
            }
            .background(color, shape = CircleShape)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val darkIsh = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        setColor(android.graphics.Color.TRANSPARENT)
                        setShadowLayer(
                            100f,
                            -50f, -50f,
                            android.graphics.Color.argb(25, 0, 0, 0) // dark-ish glow
                        )
                    }

                    val whiteIsh = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        setColor(android.graphics.Color.TRANSPARENT)
                        setShadowLayer(
                            50f,
                            50f, 50f,
                            if (isDark) android.graphics.Color.argb(25, 255, 255, 255) else android.graphics.Color.argb(50, 255, 255, 255) // white-ish glow
                        )
                    }

                    val xy = size.toPx() / 2
                    val radius = size.toPx() / 2 - 10f

                    canvas.nativeCanvas.drawCircle(
                        xy,
                        xy,
                        radius,
                        darkIsh
                    )

                    canvas.nativeCanvas.drawCircle(
                        xy,
                        xy,
                        radius,
                        whiteIsh
                    )
                }
            }
    ) {
        content()
    }
}

@Composable
fun TimerIndicator(
    modifier: Modifier = Modifier,
    progress: Float = 0.75f,
    text: String
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val arcStroke = 10.dp.toPx()
            val arcGap = 20.dp.toPx()

            val diameter = size.minDimension

            val arcSize = Size(
                width = diameter - 2 * arcGap,
                height = diameter - 2 * arcGap
            )

            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = Offset(arcGap, arcGap),
                size = arcSize,
                style = Stroke(width = arcStroke, cap = StrokeCap.Round)
            )
        }

        Text(
            text = text,
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}