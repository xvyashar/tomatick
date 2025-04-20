package com.xvyashar.tomatick.composables.screens

import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.KeyboardType
import com.xvyashar.tomatick.R
import com.xvyashar.tomatick.composables.rdp
import com.xvyashar.tomatick.composables.rsp
import com.xvyashar.tomatick.ui.theme.TextFieldBackground
import com.xvyashar.tomatick.ui.theme.TextFieldText

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 32.rdp, end = 32.rdp),
        contentAlignment = Alignment.Center
    ) {
        RectangleContainer(color = MaterialTheme.colorScheme.primary, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            Column(modifier = Modifier.padding(top = 36.rdp, bottom = 36.rdp), verticalArrangement = Arrangement.spacedBy(8.rdp)) {
                Row(modifier = Modifier.padding(start = 48.rdp, end = 48.rdp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Settings",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.rsp,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { /* TODO: reset logic */ },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.check_vector),
                            contentDescription = "Check",
                            tint = Color.White,
                            modifier = Modifier.size(32.rdp, 32.rdp)
                        )
                    }
                }

                HorizontalDivider(color = Color.LightGray, thickness = 1.rdp, modifier = Modifier.padding(8.rdp))

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.rdp, end = 40.rdp, bottom = 8.rdp), verticalArrangement = Arrangement.spacedBy(24.rdp)) {
                    Text(
                        "TIME (MINUTES)",
                        color = Color.LightGray,
                        fontSize = 18.rsp
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.rdp)
                    ) {
                        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Pomodoro",
                                color = Color.LightGray,
                                fontSize = 16.rsp,
                                modifier = Modifier.weight(1f)
                            )

                            var pomodoroValue by remember { mutableStateOf("25") }

                            OutlinedTextField(
                                value = pomodoroValue.toString(),
                                onValueChange = { newValue: String ->
                                    if (newValue.all { char -> char.isDigit() }) {
                                        pomodoroValue = newValue
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .width(100.rdp)
                                    .background(
                                        color = TextFieldBackground,
                                        shape = RoundedCornerShape(18.rdp)
                                    ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = TextFieldText,
                                    unfocusedTextColor = TextFieldText
                                )
                            )
                        }

                        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Short Break",
                                color = Color.LightGray,
                                fontSize = 16.rsp,
                                modifier = Modifier.weight(1f)
                            )

                            var shBreakValue by remember { mutableStateOf("5") }

                            OutlinedTextField(
                                value = shBreakValue.toString(),
                                onValueChange = { newValue: String ->
                                    if (newValue.all { char -> char.isDigit() }) {
                                        shBreakValue = newValue
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .width(100.rdp)
                                    .background(
                                        color = TextFieldBackground,
                                        shape = RoundedCornerShape(18.rdp)
                                    ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = TextFieldText,
                                    unfocusedTextColor = TextFieldText
                                )
                            )
                        }

                        Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Long Break",
                                color = Color.LightGray,
                                fontSize = 16.rsp,
                                modifier = Modifier.weight(1f)
                            )

                            var lBreakValue by remember { mutableStateOf("15") }

                            OutlinedTextField(
                                value = lBreakValue.toString(),
                                onValueChange = { newValue: String ->
                                    if (newValue.all { char -> char.isDigit() }) {
                                        lBreakValue = newValue
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .width(100.rdp)
                                    .background(
                                        color = TextFieldBackground,
                                        shape = RoundedCornerShape(18.rdp)
                                    ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = TextFieldText,
                                    unfocusedTextColor = TextFieldText
                                )
                            )
                        }
                    }
                }
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