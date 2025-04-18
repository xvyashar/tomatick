package com.xvyashar.tomatick.composables.screens

import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xvyashar.tomatick.R

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        RectangleContainer(color = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxWidth().height(400.dp)) {
            Column(modifier = Modifier.padding(top = 36.dp, bottom = 36.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.padding(start = 48.dp, end = 48.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Settings",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { /* TODO: reset logic */ },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check_vector),
                            contentDescription = "Check",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp, 32.dp)
                        )
                    }
                }

                HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(8.dp))


            }
        }
    }
}

@Composable
fun RectangleContainer(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                // Outer soft shadow
                shadowElevation = 32f
                shape = RoundedCornerShape(18)
                clip = true
            }
            .background(color, shape = RoundedCornerShape(18))
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
                            android.graphics.Color.argb(25, 255, 255, 255) // white-ish glow
                        )
                    }

                    canvas.nativeCanvas.drawRoundRect(
                        RectF(0f, 0f, 1000f, 1000f),
                        64f,
                        64f,
                        darkIsh
                    )

                    canvas.nativeCanvas.drawRoundRect(
                        RectF(0f, 0f, 2000f, 2000f),
                        64f,
                        64f,
                        whiteIsh
                    )
                }
            }
    ) {
        content()
    }
}