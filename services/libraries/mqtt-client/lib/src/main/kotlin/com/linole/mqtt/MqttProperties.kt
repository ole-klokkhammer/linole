package com.linole.mqtt

interface MqttProperties {
    val clientId: String
    val host: String
    val port: Int
    val username: String?
    val password: String?
    val keepAlive: Int
    val timeout: TimeoutProperties
}

interface TimeoutProperties {
    val connect: Long
    val disconnect: Long
    val subscribe: Long
    val publish: Long
    val unsubscribe: Long
}
