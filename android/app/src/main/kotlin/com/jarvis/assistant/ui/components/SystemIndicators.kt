package com.jarvis.assistant.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarvis.assistant.model.SystemStatus
import com.jarvis.assistant.ui.theme.JarvisCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * CPU Temperature Gauge
 */
@Composable
fun CpuTemperatureIndicator(
    temperature: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CPU",
            fontSize = 10.sp,
            color = JarvisCyan,
            fontFamily = FontFamily.Monospace
        )
        
        Box(
            modifier = Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularGauge(
                value = temperature.coerceIn(0f, 100f),
                maxValue = 100f,
                color = when {
                    temperature < 50f -> JarvisCyan
                    temperature < 70f -> Color.Yellow
                    else -> Color.Red
                }
            )
            
            Text(
                text = "${temperature.toInt()}°C",
                fontSize = 12.sp,
                color = JarvisCyan,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

/**
 * Battery Level Indicator
 */
@Composable
fun BatteryIndicator(
    level: Int,
    voltage: Int,
    isCharging: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BATTERY",
            fontSize = 10.sp,
            color = JarvisCyan,
            fontFamily = FontFamily.Monospace
        )
        
        Box(
            modifier = Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularGauge(
                value = level.toFloat(),
                maxValue = 100f,
                color = when {
                    level > 60 -> Color.Green
                    level > 20 -> Color.Yellow
                    else -> Color.Red
                }
            )
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$level%",
                    fontSize = 12.sp,
                    color = JarvisCyan,
                    fontFamily = FontFamily.Monospace
                )
                if (isCharging) {
                    Text(
                        text = "⚡",
                        fontSize = 10.sp,
                        color = Color.Green
                    )
                }
            }
        }
        
        Text(
            text = "${voltage}mV",
            fontSize = 8.sp,
            color = JarvisCyan.copy(alpha = 0.7f),
            fontFamily = FontFamily.Monospace
        )
    }
}

/**
 * Storage Capacity Indicator
 */
@Composable
fun StorageIndicator(
    usedBytes: Long,
    totalBytes: Long,
    modifier: Modifier = Modifier
) {
    val usedGb = usedBytes / (1024 * 1024 * 1024)
    val totalGb = totalBytes / (1024 * 1024 * 1024)
    val percentage = if (totalBytes > 0) (usedBytes.toFloat() / totalBytes * 100).toInt() else 0
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "STORAGE",
            fontSize = 10.sp,
            color = JarvisCyan,
            fontFamily = FontFamily.Monospace
        )
        
        Box(
            modifier = Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            HorizontalBarChart(
                value = percentage.toFloat(),
                maxValue = 100f,
                color = JarvisCyan
            )
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$percentage%",
                    fontSize = 12.sp,
                    color = JarvisCyan,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "$usedGb/$totalGb GB",
                    fontSize = 8.sp,
                    color = JarvisCyan.copy(alpha = 0.7f),
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Network Status Indicator
 */
@Composable
fun NetworkIndicator(
    uploadSpeed: Float,
    downloadSpeed: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NETWORK",
            fontSize = 10.sp,
            color = JarvisCyan,
            fontFamily = FontFamily.Monospace
        )
        
        Box(
            modifier = Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ) {
            NetworkGraph()
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "↓ ${downloadSpeed.toInt()} KB/s",
                    fontSize = 8.sp,
                    color = Color.Green,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "↑ ${uploadSpeed.toInt()} KB/s",
                    fontSize = 8.sp,
                    color = Color.Cyan,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

/**
 * Circular Gauge Component
 */
@Composable
fun CircularGauge(
    value: Float,
    maxValue: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(60.dp)) {
        val strokeWidth = 4.dp.toPx()
        val sweepAngle = (value / maxValue * 360f).coerceIn(0f, 360f)
        
        // Background arc
        drawArc(
            color = color.copy(alpha = 0.2f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        
        // Value arc
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

/**
 * Horizontal Bar Chart Component
 */
@Composable
fun HorizontalBarChart(
    value: Float,
    maxValue: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(60.dp, 40.dp)) {
        val barHeight = 8.dp.toPx()
        val barWidth = size.width * 0.8f
        val startX = size.width * 0.1f
        val centerY = size.height / 2
        
        // Background
        drawLine(
            color = color.copy(alpha = 0.2f),
            start = Offset(startX, centerY),
            end = Offset(startX + barWidth, centerY),
            strokeWidth = barHeight,
            cap = StrokeCap.Round
        )
        
        // Value
        val valueWidth = barWidth * (value / maxValue).coerceIn(0f, 1f)
        drawLine(
            color = color,
            start = Offset(startX, centerY),
            end = Offset(startX + valueWidth, centerY),
            strokeWidth = barHeight,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Network Graph Component (simulated wave)
 */
@Composable
fun NetworkGraph(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(60.dp)) {
        val points = 20
        val stepX = size.width / points
        
        for (i in 0 until points - 1) {
            val y1 = size.height * 0.5f + (Math.random().toFloat() - 0.5f) * size.height * 0.3f
            val y2 = size.height * 0.5f + (Math.random().toFloat() - 0.5f) * size.height * 0.3f
            
            drawLine(
                color = JarvisCyan.copy(alpha = 0.5f),
                start = Offset(i * stepX, y1),
                end = Offset((i + 1) * stepX, y2),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}
