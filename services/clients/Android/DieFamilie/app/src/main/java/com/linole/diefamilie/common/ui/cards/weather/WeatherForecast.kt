package com.linole.diefamilie.common.ui.cards.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Preview
@Composable
fun WeatherForecastPreview() {
    WeatherForecast()
}

val itemsList = (0..5).toList()

@Composable
fun WeatherForecast(modifier: Modifier = Modifier.wrapContentWidth()) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        items(itemsList) {
            ConstraintLayout(modifier = Modifier.padding(5.dp)) {
                val (day, image, max_deg, min_deg) = createRefs()
                Text(
                    text = "Sat",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier.constrainAs(day) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = "Cloudy",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(day.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = "22.1",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier.constrainAs(max_deg) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Text(
                    text = "12.6",
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray,
                    modifier = Modifier.constrainAs(min_deg) {
                        top.linkTo(max_deg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}