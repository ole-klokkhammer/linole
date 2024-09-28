package com.linole.diefamilie.common.ui.cards.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.linole.diefamilie.common.ui.cards.basic.BasicCard

@Preview
@Composable
fun WeatherCard() {
    BasicCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ConstraintLayout(modifier = Modifier.padding(15.dp)) {
            val (today, forecast) = createRefs()
            WeatherToday(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(today) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            WeatherForecast(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(forecast) {
                        top.linkTo(today.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

