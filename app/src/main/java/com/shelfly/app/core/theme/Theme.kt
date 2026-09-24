package com.shelfly.app.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Font: Inter (docs/design.md). ponytail: pakai FontFamily.Default sampai file Inter
// ditaruh di res/font/ — upgrade: font(R.font.inter_regular, FontWeight.Normal) dst.
private val ShelflyFont = FontFamily.Default

val ShelflyTypography = Typography(
    displayLarge = TextStyle(fontFamily = ShelflyFont, fontSize = 32.sp, fontWeight = FontWeight.Bold),
    headlineLarge = TextStyle(fontFamily = ShelflyFont, fontSize = 24.sp, fontWeight = FontWeight.Bold),
    headlineMedium = TextStyle(fontFamily = ShelflyFont, fontSize = 20.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontFamily = ShelflyFont, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontFamily = ShelflyFont, fontSize = 16.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontFamily = ShelflyFont, fontSize = 14.sp, fontWeight = FontWeight.Normal),
    labelMedium = TextStyle(fontFamily = ShelflyFont, fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
    labelSmall = TextStyle(fontFamily = ShelflyFont, fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
)

private val ShelflyColors = lightColorScheme(
    primary = Primary,
    onPrimary = Surface,
    secondary = Secondary,
    onSecondary = Surface,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    error = ErrorRed,
    onError = Surface,
)

@Composable
fun ShelflyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ShelflyColors,
        typography = ShelflyTypography,
        content = content,
    )
}
