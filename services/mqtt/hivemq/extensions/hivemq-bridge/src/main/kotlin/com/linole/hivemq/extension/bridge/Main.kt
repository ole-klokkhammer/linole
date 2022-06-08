//package com.linole.hivemq.extension.bridge
//
//import com.hivemq.client.mqtt.MqttClient
//import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext
//import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener
//import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
//import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener
//import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
//import com.hivemq.extension.sdk.api.ExtensionMain
//import com.hivemq.extension.sdk.api.packets.general.Qos
//import com.hivemq.extension.sdk.api.parameter.ExtensionInformation
//import com.hivemq.extension.sdk.api.parameter.ExtensionStartInput
//import com.hivemq.extension.sdk.api.parameter.ExtensionStartOutput
//import com.hivemq.extension.sdk.api.parameter.ExtensionStopInput
//import com.hivemq.extension.sdk.api.parameter.ExtensionStopOutput
//import com.hivemq.extension.sdk.api.services.Services
//import com.hivemq.extension.sdk.api.services.builder.Builders
//import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry
//import com.hivemq.extension.sdk.api.services.publish.Publish
//import org.jetbrains.annotations.NotNull
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//class Main : ExtensionMain, @NotNull MqttClientConnectedListener, @NotNull MqttClientDisconnectedListener {
//    private val log: Logger = LoggerFactory.getLogger(Main::class.java)
//    private val mqttClient = MqttClient.builder()
//        .useMqttVersion5()
//        .identifier(Config.clientId)
//        .serverHost(Config.host)
//        .serverPort(Config.port)
//        .automaticReconnectWithDefaultConfig()
//        .addConnectedListener(this@Main)
//        .addDisconnectedListener(this@Main)
//        .buildAsync()
//
//    @Override
//    override fun extensionStart(extensionStartInput: ExtensionStartInput, extensionStartOutput: ExtensionStartOutput) {
//        try {
//            if (Config.enabled) {
//                addForwarder()
//                mqttClient.connect()
//                val extensionInformation: ExtensionInformation = extensionStartInput.extensionInformation
//                log.info("Config says we forward: ${Config.publishTopics}")
//                log.info("Config says we receive: ${Config.subscriptionTopics}")
//                log.info("Started " + extensionInformation.name.toString() + ":" + extensionInformation.version)
//            }
//        } catch (e: Exception) {
//            log.error("Exception thrown at extension start: ", e)
//        }
//    }
//
//    @Override
//    override fun extensionStop(extensionStopInput: ExtensionStopInput, extensionStopOutput: ExtensionStopOutput) {
//        if (Config.enabled) {
//            mqttClient.disconnect()
//            val extensionInformation: ExtensionInformation = extensionStopInput.extensionInformation
//            log.info("Stopped " + extensionInformation.name.toString() + ":" + extensionInformation.version)
//        }
//    }
//
//    private fun addForwarder() {
//        val initializerRegistry: InitializerRegistry = Services.initializerRegistry()
//        val helloWorldInterceptor = Forwarder(mqttClient)
//        initializerRegistry.setClientInitializer { _, clientContext ->
//            clientContext.addPublishOutboundInterceptor(
//                helloWorldInterceptor
//            )
//        }
//    }
//
//    override fun onConnected(context: MqttClientConnectedContext) {
//        log.info("Mqtt client connected")
//        Config.subscriptionTopics.forEach { topic ->
//            mqttClient.subscribeWith()
//                .topicFilter(topic)
//                .callback {
//                    try {
//                        Services.publishService().publish(it.toPublishPacket())
//                            .whenComplete { _, t -> log.debug("Publish complete. (throwable={})", t) }
//                    } catch (e: Throwable) {
//                        log.error("", e)
//                    }
//                }
//                .send()
//        }
//    }
//
//    override fun onDisconnected(context: MqttClientDisconnectedContext) {
//        log.info("Mqtt client disconnected")
//    }
//
//    private fun Mqtt5Publish.toPublishPacket(): Publish = Builders.publish().let { builder ->
//        builder.topic("${Config.localMountpoint}$topic")
//        if (payload.isPresent) builder.payload(payload.get())
//        if (contentType.isPresent) builder.contentType(contentType.get().toString())
//        if (correlationData.isPresent) builder.correlationData(correlationData.get())
//        if (messageExpiryInterval.isPresent) builder.messageExpiryInterval(messageExpiryInterval.asLong)
//        if (responseTopic.isPresent) builder.responseTopic(responseTopic.get().filter().toString())
//        for (userProp in userProperties.asList()) {
//            builder.userProperty(userProp.name.toString(), userProp.value.toString())
//        }
//        builder.retain(isRetain)
//        builder.qos(Qos.valueOf(qos.code))
//        builder.build()
//    }
//}