package com.linole.diefamilie.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = Colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
