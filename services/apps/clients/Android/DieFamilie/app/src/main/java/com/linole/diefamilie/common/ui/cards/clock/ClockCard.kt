package com.linole.diefamilie.common.ui.cards.clock

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.linole.diefamilie.common.ui.cards.basic.BasicCard
import kotlinx.coroutines.delay

@Preview
@Composable
fun ClockCard() {
    var time by remember { mutableStateOf(currentTime()) }
    LaunchedEffect(0) {
        while (true) {
            time = currentTime()
            delay(1000)
        }
    }
    BasicCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ConstraintLayout(modifier = Modifier.padding(bottom = 14.dp)) {
            val (clock, date) = createRefs()
            Text(
                text = time.clock(),
                style = MaterialTheme.typography.h2,
                color = Color.White,
                modifier = Modifier.constrainAs(clock) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Text(
                text = time.date(),
                style = MaterialTheme.typography.body1,
                color = Color.White,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(clock.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}