package com.jarvis.assistant.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.jarvis.assistant.ui.theme.JarvisCyan
import kotlin.math.cos
import kotlin.math.sin

/**
 * Arc Reactor Animation - The heart of JARVIS HUD
 */
@Composable
fun ArcReactor(
    modifier: Modifier = Modifier,
    amplitude: Float = 0f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "arc_reactor")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Box(
        modifier = modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2
            val baseRadius = canvasWidth / 2 * 0.8f
            
            // Outer glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        JarvisCyan.copy(alpha = 0.3f * pulse),
                        Color.Transparent
                    ),
                    center = Offset(centerX, centerY),
                    radius = baseRadius * 1.5f
                ),
                center = Offset(centerX, centerY),
                radius = baseRadius * 1.5f
            )
            
            // Main reactor ring
            drawCircle(
                color = JarvisCyan.copy(alpha = 0.8f),
                center = Offset(centerX, centerY),
                radius = baseRadius,
                style = Stroke(width = 4.dp.toPx())
            )
            
            // Inner ring
            drawCircle(
                color = JarvisCyan.copy(alpha = 0.6f),
                center = Offset(centerX, centerY),
                radius = baseRadius * 0.7f,
                style = Stroke(width = 3.dp.toPx())
            )
            
            // Core
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        JarvisCyan,
                        JarvisCyan.copy(alpha = 0.4f)
                    ),
                    center = Offset(centerX, centerY),
                    radius = baseRadius * 0.3f
                ),
                center = Offset(centerX, centerY),
                radius = baseRadius * 0.3f
            )
            
            // Rotating energy lines
            rotate(rotation) {
                for (i in 0 until 8) {
                    val angle = (i * 45f) * (Math.PI / 180f)
                    val startRadius = baseRadius * 0.4f
                    val endRadius = baseRadius * 0.95f
                    
                    drawLine(
                        color = JarvisCyan.copy(alpha = 0.7f),
                        start = Offset(
                            centerX + cos(angle).toFloat() * startRadius,
                            centerY + sin(angle).toFloat() * startRadius
                        ),
                        end = Offset(
                            centerX + cos(angle).toFloat() * endRadius,
                            centerY + sin(angle).toFloat() * endRadius
                        ),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
            
            // Amplitude-reactive particles (simulated voice visualizer)
            if (amplitude > 0.1f) {
                for (i in 0 until 16) {
                    val angle = (i * 22.5f) * (Math.PI / 180f)
                    val particleRadius = baseRadius * (1.1f + amplitude * 0.3f)
                    
                    drawCircle(
                        color = JarvisCyan.copy(alpha = amplitude),
                        center = Offset(
                            centerX + cos(angle).toFloat() * particleRadius,
                            centerY + sin(angle).toFloat() * particleRadius
                        ),
                        radius = 3.dp.toPx() * amplitude
                    )
                }
            }
        }
    }
}
