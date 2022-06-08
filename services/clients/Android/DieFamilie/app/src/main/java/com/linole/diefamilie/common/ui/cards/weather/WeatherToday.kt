package com.linole.diefamilie.common.ui.cards.weather

import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Preview
@Composable
fun WeatherTodayPreview() {
    WeatherToday(
        modifier = Modifier.width(150.dp)
    )
}

@Composable
fun WeatherToday(modifier: Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (state, place, degrees, precipitation) = createRefs()
        Text(
            text = "Sunny",
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier.constrainAs(state) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = "Home",
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            modifier = Modifier.constrainAs(place) {
                top.linkTo(state.bottom)
                start.linkTo(parent.start)
            }
        )
        Text(
            text = "17.6",
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier.constrainAs(degrees) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = "0 mm",
            style = MaterialTheme.typography.caption,
            color = Color.Gray,
            modifier = Modifier.constrainAs(precipitation) {
                top.linkTo(degrees.bottom)
                end.linkTo(parent.end)
            }
        )
    }
}
