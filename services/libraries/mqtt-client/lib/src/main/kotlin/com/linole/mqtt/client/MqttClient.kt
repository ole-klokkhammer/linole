package com.linole.mqtt.client

import com.linole.mqtt.MqttMessage
import com.linole.mqtt.MqttProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import org.slf4j.Logger
import kotlin.coroutines.CoroutineContext

abstract class MqttClient(
    protected val props: MqttProperties,
    protected val logger: Logger
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()
    private var messageFlow = MutableSharedFlow<MqttMessage>()
    private val connectionState = MutableStateFlow(false)

    protected abstract suspend fun clientConnect(): ByteArray
    protected abstract suspend fun clientDisconnect(): ByteArray
    protected abstract suspend fun clientSubscribe(topic: String, qos: Int = 0): ByteArray
    protected abstract suspend fun clientUnsubscribe(topic: String): ByteArray
    protected abstract suspend fun clientPublish(topic: String, payloadAsBytes: ByteArray): ByteArray

    fun connectionStateFlow() = connectionState.asStateFlow()

    fun onMessage(topic: String) = flow {
        messageFlow.collect { message ->
            if (message.matchesTopic(topic)) {
                emit(message)
            }
        }
    }

    private suspend fun isConnected(): Boolean = connectionState.firstOrNull() ?: false

    fun connect(onConnected: () -> Unit = {}, onFailure: (Throwable) -> Unit = {}) = launch(Dispatchers.Default) {
        withTimeoutOrNull(props.timeout.subscribe) {
            runCatching {
                clientConnect()
                logger.info("Connection successful")
                onConnected()
            }.onFailure { e ->
                logger.error("Failed to connect to ${props.host}", e)
                onFailure(e)
            }
        } ?: run {
            logger.error("Timeout when attempting to connect to ${props.host}")
        }
    }

    fun disconnect() = launch(Dispatchers.Default) {
        if (isConnected()) {
            withTimeoutOrNull(props.timeout.subscribe) {
                logger.debug("Disconnecting from broker ${props.host}")
                runCatching {
                    clientDisconnect()
                    logger.info("Disconnected from ${props.host}")
                }.onFailure { e ->
                    logger.error("Failed to disconnect from ${props.host}", e)
                }
            } ?: logger.error("Timeout when attempting to disconnect from ${props.host}")
        } else logger.info("Attempting to disconnect from ${props.host} when already disconnected!")
    }

    suspend fun subscribe(topic: String, qos: Int = 0) {
        withTimeoutOrNull(props.timeout.unsubscribe) {
            runCatching {
                clientSubscribe(topic, qos)
                logger.info("Added subscription to topic: $topic")
            }.onFailure { e ->
                logger.error("Subscription failed for $topic !", e)
            }
        } ?: logger.error("Timeout when attempting to subscribe to $topic")
    }

    fun unsubscribe(topic: String) = launch(Dispatchers.Default) {
        withTimeoutOrNull(props.timeout.unsubscribe) {
            logger.debug("Unsubscribing to topic $topic")
            runCatching {
                clientUnsubscribe(topic)
                logger.debug("Unsubscribed to topic: $topic")
            }.onFailure { e ->
                logger.error("Failed to unsubscribe to $topic !", e)
            }
        } ?: run {
            logger.error("Timeout when attempting to unsubscribe to $topic on ${props.host}")
        }
    }

    fun publish(topic: String, payloadAsBytes: ByteArray) = launch(Dispatchers.Default) {
        withTimeoutOrNull(props.timeout.publish) {
            logger.debug("Publishing ${String(payloadAsBytes)} to topic $topic")
            runCatching {
                clientPublish(topic, payloadAsBytes)
                logger.debug("Published ${String(payloadAsBytes)} to $topic")
            }.onFailure { e ->
                logger.error("Publish failed for $topic !", e)
            }
        } ?: run {
            logger.error("Timeout when attempting to publish to $topic on ${props.host}")
        }
    }

    protected fun emitOnChannel(topic: String, payload: ByteArray) = launch(Dispatchers.Default) {
        messageFlow.emit(MqttMessage(topic, String(payload)))
    }

    private fun MqttMessage.matchesTopic(topicMatcher: String): Boolean {
        return when {
            topicMatcher == topic -> true
            topicMatcher.contains("#") -> topic.startsWith(topicMatcher.substringBefore("#"))
            else -> false
        }
    }

    protected fun setConnected(flag: Boolean) {
        connectionState.tryEmit(flag)
    }
}
