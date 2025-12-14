package com.jarvis.assistant.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarvis.assistant.model.SystemStatus
import com.jarvis.assistant.ui.theme.JarvisCyan
import com.jarvis.assistant.ui.theme.JarvisGray

/**
 * Main JARVIS HUD Screen
 */
@Composable
fun JarvisHUD(
    systemStatus: SystemStatus,
    onCommandSubmit: (String) -> Unit,
    statusMessage: String = "",
    modifier: Modifier = Modifier
) {
    var commandText by remember { mutableStateOf("") }
    var voiceAmplitude by remember { mutableStateOf(0f) }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Left: CPU Temperature
        CpuTemperatureIndicator(
            temperature = systemStatus.cpuTemp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
        
        // Top Right: Battery
        BatteryIndicator(
            level = systemStatus.batteryLevel,
            voltage = systemStatus.batteryVoltage,
            isCharging = systemStatus.isCharging,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )
        
        // Bottom Left: Storage
        StorageIndicator(
            usedBytes = systemStatus.storageUsed,
            totalBytes = systemStatus.storageTotal,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 100.dp)
        )
        
        // Bottom Right: Network
        NetworkIndicator(
            uploadSpeed = systemStatus.networkUpload,
            downloadSpeed = systemStatus.networkDownload,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 100.dp)
        )
        
        // Center: Arc Reactor
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArcReactor(
                amplitude = voiceAmplitude,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = "J.A.R.V.I.S.",
                fontSize = 24.sp,
                color = JarvisCyan,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )
            
            if (statusMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = statusMessage,
                    fontSize = 14.sp,
                    color = JarvisCyan.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Monospace
                )
            }
        }
        
        // Bottom: Command Input
        CommandTerminal(
            value = commandText,
            onValueChange = { commandText = it },
            onSubmit = {
                if (commandText.isNotEmpty()) {
                    onCommandSubmit(commandText)
                    commandText = ""
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

/**
 * Terminal-like command input
 */
@Composable
fun CommandTerminal(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = JarvisGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = JarvisCyan.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ">",
                fontSize = 18.sp,
                color = JarvisCyan,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(end = 8.dp)
            )
            
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = JarvisCyan,
                    fontFamily = FontFamily.Monospace
                ),
                cursorBrush = SolidColor(JarvisCyan),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = { onSubmit() }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = "Speak or type command...",
                                fontSize = 16.sp,
                                color = JarvisCyan.copy(alpha = 0.4f),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        innerTextField()
                    }
                }
            )
            
            if (value.isNotEmpty()) {
                Button(
                    onClick = onSubmit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = JarvisCyan.copy(alpha = 0.2f),
                        contentColor = JarvisCyan
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "SEND",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}
