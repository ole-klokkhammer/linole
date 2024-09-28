package com.linole.diefamilie.common.ui.cards.gauge

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.linole.diefamilie.common.ui.theme.Colors

@Preview
@Composable
fun ArcPreview() {
    Arc(
        modifier = Modifier
            .padding(0.dp)
            .width(100.dp)
            .height(50.dp),
        value = 80,
        range = IntRange(0, 100),
        color = Colors.primary
    )
}

@Composable
fun Arc(
    modifier: Modifier = Modifier,
    range: IntRange,
    value: Int,
    color: Color
) {
    Canvas(
        modifier = modifier
            .requiredWidth(100.dp)
            .requiredHeight(50.dp)
    ) {
        drawArc(
            color = color,
            topLeft = Offset(20f, 20f),
            startAngle = 180f,
            sweepAngle = 180 * getSweepAngleFactor(value, range),
            useCenter = false,
            style = Stroke(width = 30f),
            size = Size(size.width - 40, (size.height - 20) * 2)
        )
    }
}

fun getSweepAngleFactor(value: Int, range: IntRange): Float {
    return value / (range.last - range.first).toFloat()
}