package com.linole.diefamilie.screens.dashboard

import androidx.lifecycle.ViewModel
import com.linole.diefamilie.common.mqtt.MqttService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class Topic(value: String) {
    CO2("co2")
}

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    fun subscribe(topic: Topic) = flow {
        var counter = 0
        while (true) {
            emit(500 + (++counter))
            delay(1000)
        }
    }
}