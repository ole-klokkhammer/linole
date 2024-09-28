package com.linole.diefamilie.common.ui.cards.basic

import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.linole.diefamilie.common.ui.theme.Colors

@Preview
@Composable
fun BasicCardPreview() {
    BasicCard(modifier = Modifier.size(100.dp))
}

@Composable
fun BasicCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier,
        backgroundColor = Colors.surface,
    ) {
        content()
    }
}
