package com.example.gitsearchtest.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary =Color(0xFF202020),
    onSurface = Color(0xFF050505),
    onPrimary = Color(0xFFFAFAFA),
    onBackground = Color(0xFF2D2D2D),
    onSecondary = Color(0xFF66FFFFFF),
    secondary = Color(0xFF050505),
    tertiary = Pink80,
    inversePrimary =Color(0xFFFFFFFF)

)

private val LightColorScheme = lightColorScheme(


    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    secondary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF0D0D0D),
    onSecondary = Color(0xFF4D0D0D0D),
    onTertiary = Color.White,
    onBackground = Color(0xFFFEFDFF),
    onSurface = Color(0xFF050505),
    primary = Color(0xFFECECEC),
    inversePrimary =Color(0xFFFFFFFF)

)

@Composable
fun GitSearchTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}