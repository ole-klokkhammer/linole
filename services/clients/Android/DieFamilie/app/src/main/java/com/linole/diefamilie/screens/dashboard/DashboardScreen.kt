package com.linole.diefamilie.screens.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linole.diefamilie.common.ui.cards.clock.ClockCard
import com.linole.diefamilie.common.ui.cards.gauge.GaugeCard
import com.linole.diefamilie.common.ui.cards.gauge.SeverityLevel
import com.linole.diefamilie.common.ui.cards.weather.WeatherCard

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun DashboardScreen(vm: DashboardViewModel = viewModel()) {
    val co2Flow = remember { vm.subscribe(Topic.CO2) }

    Column(modifier = Modifier.padding(10.dp)) {
        ClockCard()
        Spacer(modifier = Modifier.height(5.dp))
        WeatherCard()
        Spacer(modifier = Modifier.height(5.dp))
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
            items(6) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(5.dp)
                ) {
                    GaugeCard(
                        modifier = Modifier.fillMaxSize(),
                        name = Topic.CO2.name,
                        stateFlow = co2Flow,
                        measurement = "ppm",
                        range = IntRange(0, 1200),
                        severity = SeverityLevel(green = 0, yellow = 600, red = 800)
                    )
                }
            }
        }
    }
}
