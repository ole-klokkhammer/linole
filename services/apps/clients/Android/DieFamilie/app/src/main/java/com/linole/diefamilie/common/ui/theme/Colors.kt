package com.linole.diefamilie.common.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.compositeOver

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

val Yellow800 = Color(0xFFF29F05)
val Red300 = Color(0xFFEA6D7E)
val Green500 = Color(0xFF1EB980)
val DarkBlue900 = Color(0xFF26282F)

val Colors = darkColors(
    primary = Green500,
    onPrimary = Color.Black,
    primaryVariant = Yellow800,
    surface = Color.DarkGray,
    onSurface = Color.DarkGray,
    background = DarkBlue900,
    onBackground = Color.DarkGray,
    secondary = Yellow800,
    onSecondary = Color.Black,
    error = Red300,
    onError = Color.Black,
)
