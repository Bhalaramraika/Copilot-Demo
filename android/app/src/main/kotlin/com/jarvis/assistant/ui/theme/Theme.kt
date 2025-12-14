package com.jarvis.assistant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// JARVIS Color Scheme - Iron Man inspired cyan/blue theme
val JarvisCyan = Color(0xFF00D4FF)
val JarvisBlue = Color(0xFF0095D4)
val JarvisDarkBlue = Color(0xFF006494)
val JarvisBlack = Color(0xFF0A0A0A)
val JarvisDarkGray = Color(0xFF1A1A1A)
val JarvisGray = Color(0xFF2A2A2A)

private val JarvisDarkColorScheme = darkColorScheme(
    primary = JarvisCyan,
    secondary = JarvisBlue,
    tertiary = JarvisDarkBlue,
    background = JarvisBlack,
    surface = JarvisDarkGray,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = JarvisCyan,
    onSurface = JarvisCyan
)

@Composable
fun JarvisTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = JarvisDarkColorScheme,
        content = content
    )
}
