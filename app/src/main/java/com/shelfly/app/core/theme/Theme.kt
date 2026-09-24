package com.shelfly.app.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// PIC: Person A — sesuaikan scale sesuai Stitch export
val ShelflyTypography = Typography(
    headlineLarge = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
    titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
)

private val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryOrange,
    background = Background,
    surface = Surface,
    error = ErrorRed,
)

private val DarkColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryOrange,
    background = NavyDark,
    surface = NavyDark,
    error = ErrorRed,
)

@Composable
fun ShelflyTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = ShelflyTypography,
        content = content,
    )
}
