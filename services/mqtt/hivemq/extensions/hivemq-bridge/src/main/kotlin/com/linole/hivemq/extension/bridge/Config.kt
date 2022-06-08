//package com.linole.hivemq.extension.bridge
//
//fun safeGetEnv(name: String): String? {
//    return try {
//        System.getenv(name)
//    } catch (e: Throwable) {
//        null
//    }
//}
//
//object Config {
//    val enabled: Boolean = safeGetEnv("HIVEMQ_BRIDGE_ENABLE")?.toBoolean() ?: false
//    val clientId: String = safeGetEnv("HIVEMQ_BRIDGE_CLIENT_ID") ?: "hivemq"
//    val host: String = safeGetEnv("HIVEMQ_BRIDGE_HOST") ?: "mqttbroker"
//    val port: Int = safeGetEnv("HIVEMQ_BRIDGE_PORT")?.toInt() ?: 1883
//    val publishTopics: List<String> = safeGetEnv("HIVEMQ_BRIDGE_PUBLISH_TOPICS")?.split(",") ?: emptyList()
//    val remoteMountpoint: String = safeGetEnv("HIVEMQ_BRIDGE_REMOTE_MOUNTPOINT") ?: ""
//    val subscriptionTopics: List<String> = safeGetEnv("HIVEMQ_BRIDGE_SUBSCRIPTION_TOPICS")?.split(",") ?: emptyList()
//    val localMountpoint: String = safeGetEnv("HIVEMQ_BRIDGE_LOCAL_MOUNTPOINT") ?: ""
//}