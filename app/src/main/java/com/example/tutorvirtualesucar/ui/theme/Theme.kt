package com.example.tutorvirtualesucar.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val TemaClaro = lightColorScheme(
    primary = Color(0xFF1D5339),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0E8D7),
    onPrimaryContainer = Color(0xFF123723),
    secondary = Color(0xFF426252),
    onSecondary = Color.White,
    background = Color(0xFFF7F8F3),
    onBackground = Color(0xFF18231C),
    surface = Color(0xFFF7F8F3),
    onSurface = Color(0xFF18231C),
    surfaceVariant = Color(0xFFE1EAE2),
    surfaceContainerHighest = Color(0xFFE1EAE2),
    onSurfaceVariant = Color(0xFF36463B),
    outline = Color(0xFF617166)
)

private val TemaOscuro = darkColorScheme(
    primary = Color(0xFFA5D5B5),
    onPrimary = Color(0xFF103822),
    primaryContainer = Color(0xFF254C35),
    onPrimaryContainer = Color(0xFFDCF3E2),
    secondary = Color(0xFFB4CDBD),
    onSecondary = Color(0xFF233B2D),
    background = Color(0xFF121A15),
    onBackground = Color(0xFFE1EAE2),
    surface = Color(0xFF121A15),
    onSurface = Color(0xFFE1EAE2),
    surfaceVariant = Color(0xFF303D34),
    surfaceContainerHighest = Color(0xFF303D34),
    onSurfaceVariant = Color(0xFFC4D1C6),
    outline = Color(0xFF9AA99E)
)

@Composable
fun TutorVirtualESUCARTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val esquema = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val contexto = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(contexto)
            else dynamicLightColorScheme(contexto)
        }
        darkTheme -> TemaOscuro
        else -> TemaClaro
    }

    MaterialTheme(
        colorScheme = esquema,
        typography = Typography,
        content = content
    )
}