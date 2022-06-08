package com.linole.mqtt

data class MqttMessage(
    val topic: String,
    val payloadString: String
)
