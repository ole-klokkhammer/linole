package com.linole.diefamilie.common.ui.cards.gauge

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.linole.diefamilie.common.ui.cards.basic.BasicCard
import com.linole.diefamilie.common.ui.theme.Colors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Preview
@Composable
fun GaugeCardPreview() {
    GaugeCard(
        modifier = Modifier
            .width(110.dp)
            .height(75.dp),
        name = "CO2",
        measurement = "ppm",
        stateFlow = flowOf(50),
        initialState = 50,
        range = IntRange(0, 100),
        severity = SeverityLevel(
            green = 0,
            yellow = 600,
            red = 800
        )
    )
}

data class SeverityLevel(
    val green: Int,
    val red: Int,
    val yellow: Int
)

@Composable
fun GaugeCard(
    modifier: Modifier = Modifier,
    range: IntRange,
    name: String,
    stateFlow: Flow<Int>,
    initialState: Int = 0,
    measurement: String = "",
    severity: SeverityLevel,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val locationFlowLifecycleAware = remember(stateFlow, lifecycleOwner) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val stateValue: Int by locationFlowLifecycleAware.collectAsState(initial = initialState)

    BasicCard(modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            val (arcBox, title) = createRefs()
            Box(
                Modifier
                    .constrainAs(arcBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
            ) {
                Arc(
                    color = getColor(stateValue, severity),
                    value = stateValue,
                    range = range
                )
                Text(
                    text = "$stateValue $measurement",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 10.sp
                    ),
                    color = Color.White,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 12.sp
                ),
                color = Color.White,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(arcBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxHeight()
            )
        }
    }
}

fun getColor(stateValue: Int, severity: SeverityLevel): Color {
    return when {
        stateValue < severity.yellow -> Colors.primary
        stateValue < severity.red -> Colors.secondary
        stateValue >= severity.red -> Colors.error
        else -> Colors.surface
    }
}
