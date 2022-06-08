package com.linole.mqtt.client

import com.linole.mqtt.MqttProperties
import kotlinx.coroutines.suspendCancellableCoroutine
import org.slf4j.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import org.eclipse.paho.client.mqttv3.IMqttActionListener as PahoMqtt3IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken as PahoMqtt3IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken as PahoMqtt3IMqttToken
import org.eclipse.paho.client.mqttv3.MqttAsyncClient as AsyncMqtt3PahoClient
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended as PahoMqtt3MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions as PahoMqtt3MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage as PahoMqtt3Message

class Mqtt3Client(
    props: MqttProperties,
    logger: Logger
) : MqttClient(props, logger), PahoMqtt3MqttCallbackExtended {
    private var client = AsyncMqtt3PahoClient("tcp://${props.host}:${props.port}", props.clientId).also {
        it.setCallback(this)
    }

    override suspend fun clientConnect() = suspendCancellableCoroutine<ByteArray> { continuation ->
        val options = PahoMqtt3MqttConnectOptions().apply {
            props.username?.let { userName = it }
            props.password?.let { password = it.toCharArray() }
            keepAliveInterval = props.keepAlive
            isAutomaticReconnect = true
            maxInflight = 100
            isCleanSession = true
        }
        client.connect(
            options, null,
            object : PahoMqtt3IMqttActionListener {
                override fun onSuccess(asyncActionToken: PahoMqtt3IMqttToken) {
                    continuation.resume(asyncActionToken.response.payload)
                }

                override fun onFailure(token: PahoMqtt3IMqttToken, exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            }
        )
    }

    override suspend fun clientDisconnect() = suspendCancellableCoroutine<ByteArray> { continuation ->
        client.disconnect(
            null,
            object : PahoMqtt3IMqttActionListener {
                override fun onSuccess(asyncActionToken: PahoMqtt3IMqttToken) {
                    continuation.resume(asyncActionToken.response.payload)
                }

                override fun onFailure(token: PahoMqtt3IMqttToken, exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            }
        )
    }

    override suspend fun clientSubscribe(topic: String, qos: Int) =
        suspendCancellableCoroutine<ByteArray> { continuation ->
            client.subscribe(
                topic, qos, null,
                object : PahoMqtt3IMqttActionListener {
                    override fun onSuccess(asyncActionToken: PahoMqtt3IMqttToken) {
                        continuation.resume(asyncActionToken.response.payload)
                    }

                    override fun onFailure(asyncActionToken: PahoMqtt3IMqttToken, exception: Throwable) {
                        continuation.resumeWithException(exception)
                    }
                }
            )
        }

    override suspend fun clientUnsubscribe(topic: String) = suspendCancellableCoroutine<ByteArray> { continuation ->
        client.unsubscribe(
            topic, null,
            object : PahoMqtt3IMqttActionListener {
                override fun onSuccess(asyncActionToken: PahoMqtt3IMqttToken) {
                    continuation.resume(asyncActionToken.response.payload)
                }

                override fun onFailure(asyncActionToken: PahoMqtt3IMqttToken, exception: Throwable) {
                    continuation.resumeWithException(exception)
                }
            }
        )
    }

    override suspend fun clientPublish(topic: String, payloadAsBytes: ByteArray) =
        suspendCancellableCoroutine<ByteArray> { continuation ->
            client.publish(
                topic, PahoMqtt3Message(payloadAsBytes).also { it.qos = 2 },
                null,
                object : PahoMqtt3IMqttActionListener {
                    override fun onSuccess(asyncActionToken: PahoMqtt3IMqttToken) {
                        continuation.resume(asyncActionToken.response.payload)
                    }

                    override fun onFailure(token: PahoMqtt3IMqttToken, exception: Throwable) {
                        continuation.resumeWithException(exception)
                    }
                }
            )
        }

    override fun connectComplete(reconnect: Boolean, serverURI: String) {
        logger.info("Mqtt ${if (reconnect) "reconnected" else "connected"} to $serverURI")
        setConnected(true)
    }

    override fun connectionLost(cause: Throwable?) {
        logger.debug("connectionLost")
        setConnected(false)
    }

    override fun messageArrived(topic: String, message: PahoMqtt3Message) {
        logger.debug("messageArrived $topic ")
        emitOnChannel(topic, message.payload)
    }

    override fun deliveryComplete(token: PahoMqtt3IMqttDeliveryToken?) {
        logger.debug("deliveryComplete")
    }
}
