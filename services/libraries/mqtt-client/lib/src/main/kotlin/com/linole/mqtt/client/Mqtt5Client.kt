package com.linole.mqtt.client

import com.linole.mqtt.MqttProperties
import kotlinx.coroutines.suspendCancellableCoroutine
import org.eclipse.paho.mqttv5.common.MqttException
import org.slf4j.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.eclipse.paho.mqttv5.client.IMqttToken as AsyncMqtt5IMqttToken
import org.eclipse.paho.mqttv5.client.MqttActionListener as AsyncMqtt5MqttActionListener
import org.eclipse.paho.mqttv5.client.MqttAsyncClient as AsyncMqtt5PahoClient
import org.eclipse.paho.mqttv5.client.MqttCallback as AsyncMqtt5MqttCallback
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions as AsyncMqtt5MqttConnectionOptions
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse as AsyncMqtt5MqttDisconnectResponse
import org.eclipse.paho.mqttv5.common.MqttMessage as AsyncMqttMessage5

class Mqtt5Client(
    props: MqttProperties,
    logger: Logger
) : MqttClient(props, logger), AsyncMqtt5MqttCallback {

    private var client = AsyncMqtt5PahoClient("tcp://${props.host}:${props.port}", props.clientId).also {
        it.setCallback(this)
    }

    override suspend fun clientConnect() = suspendCancellableCoroutine<ByteArray> { continuation ->
        val options = AsyncMqtt5MqttConnectionOptions().apply {
            props.username?.let { userName = it }
            props.password?.let { password = it.toByteArray() }
            keepAliveInterval = props.keepAlive
            sessionExpiryInterval = null
            isAutomaticReconnect = true
            isCleanStart = true
        }
        client.connect(
            options, null,
            object : AsyncMqtt5MqttActionListener {
                override fun onSuccess(asyncActionToken: AsyncMqtt5IMqttToken) {
                    continuation.resume(asyncActionToken.response.payload)
                }

                override fun onFailure(token: AsyncMqtt5IMqttToken, exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            }
        )
    }

    override suspend fun clientDisconnect() = suspendCancellableCoroutine<ByteArray> { continuation ->
        client.disconnect(
            null,
            object : AsyncMqtt5MqttActionListener {
                override fun onSuccess(asyncActionToken: AsyncMqtt5IMqttToken) {
                    continuation.resume(asyncActionToken.response.payload)
                }

                override fun onFailure(token: AsyncMqtt5IMqttToken, exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            }
        )
    }

    override suspend fun clientSubscribe(topic: String, qos: Int) =
        suspendCancellableCoroutine<ByteArray> { continuation ->
            client.subscribe(
                topic, qos, null,
                object : AsyncMqtt5MqttActionListener {
                    override fun onSuccess(asyncActionToken: AsyncMqtt5IMqttToken) {
                        continuation.resume(asyncActionToken.response.payload)
                    }

                    override fun onFailure(asyncActionToken: AsyncMqtt5IMqttToken, exception: Throwable) {
                        continuation.resumeWithException(exception)
                    }
                }
            )
        }

    override suspend fun clientUnsubscribe(topic: String) = suspendCancellableCoroutine<ByteArray> { continuation ->
        client.unsubscribe(
            topic, null,
            object : AsyncMqtt5MqttActionListener {
                override fun onSuccess(asyncActionToken: AsyncMqtt5IMqttToken) {
                    continuation.resume(asyncActionToken.response.payload)
                }

                override fun onFailure(asyncActionToken: AsyncMqtt5IMqttToken, exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            }
        )
    }

    override suspend fun clientPublish(topic: String, payloadAsBytes: ByteArray) =
        suspendCancellableCoroutine<ByteArray> { continuation ->
            client.publish(
                topic, AsyncMqttMessage5(payloadAsBytes).also { it.qos = 2 },
                null,
                object : AsyncMqtt5MqttActionListener {
                    override fun onSuccess(asyncActionToken: AsyncMqtt5IMqttToken) {
                        continuation.resume(asyncActionToken.response.payload)
                    }

                    override fun onFailure(token: AsyncMqtt5IMqttToken, exception: Throwable) {
                        continuation.resumeWithException(exception)
                    }
                }
            )
        }

    override fun connectComplete(reconnect: Boolean, serverURI: String) {
        logger.info("Mqtt ${if (reconnect) "reconnected" else "connected"} to $serverURI")
        setConnected(true)
    }

    override fun disconnected(disconnectResponse: AsyncMqtt5MqttDisconnectResponse?) {
        logger.error("Mqtt disconnected")
        setConnected(false)
    }

    override fun mqttErrorOccurred(exception: MqttException?) {
        logger.debug("mqttErrorOccurred")
    }

    override fun messageArrived(topic: String, message: org.eclipse.paho.mqttv5.common.MqttMessage) {
        logger.debug("messageArrived $topic ")
        emitOnChannel(topic, message.payload)
    }

    override fun deliveryComplete(token: AsyncMqtt5IMqttToken) {
        logger.debug("deliveryComplete ${token.response.payload.let { String(it) }}")
    }

    override fun authPacketArrived(reasonCode: Int, properties: org.eclipse.paho.mqttv5.common.packet.MqttProperties?) {
        logger.debug("authPacketArrived")
    }
}
