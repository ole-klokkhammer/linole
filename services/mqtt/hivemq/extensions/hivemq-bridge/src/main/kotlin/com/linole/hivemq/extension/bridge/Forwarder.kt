//package com.linole.hivemq.extension.bridge
//
//import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder
//import com.hivemq.client.mqtt.datatypes.MqttQos
//import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
//import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties
//import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor
//import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput
//import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput
//import com.hivemq.extension.sdk.api.packets.publish.PublishPacket
//import org.slf4j.LoggerFactory
//
//class Forwarder(private val client: Mqtt5AsyncClient) : PublishOutboundInterceptor {
//    private val log = LoggerFactory.getLogger(Forwarder::class.java)
//
//    override fun onOutboundPublish(
//        publishOutboundInput: PublishOutboundInput,
//        publishOutboundOutput: PublishOutboundOutput
//    ) {
//        try {
//            publishOutboundInput.publishPacket.let { publishPacket ->
//                Config.publishTopics.forEach { topics ->
//                    topics.substringBefore("#").let { topicMatcher ->
//                        if (publishPacket.topic.startsWith(topicMatcher)) {
//                            log.debug("Forwarding topic ${publishPacket.topic} to ${Config.host}")
//                            client.publish(publishPacket.toPublish())
//                        }
//                    }
//                }
//            }
//        } catch (e: Throwable) {
//            log.error("Exception: $e")
//        }
//    }
//
//    private fun PublishPacket.toPublish() = MqttPublishBuilder.Default().let { builder ->
//        builder.topic("${Config.remoteMountpoint}${topic.substringAfter("${Config.localMountpoint}set/")}")
//        if (payload.isPresent) builder.payload(payload.get())
//        if (contentType.isPresent) builder.contentType(contentType.get())
//        if (correlationData.isPresent) builder.correlationData(correlationData.get())
//        if (messageExpiryInterval.isPresent) builder.messageExpiryInterval(messageExpiryInterval.get() - 1)
//        if (responseTopic.isPresent) builder.responseTopic(responseTopic.get())
////                if (message.payloadFormatIndicator.isPresent) it.payloadFormatIndicator(message.payloadFormatIndicator.get())
//        builder.qos(MqttQos.fromCode(qos.qosNumber))
//        builder.retain(retain)
//        builder.userProperties(
//            Mqtt5UserProperties.builder().apply {
//                userProperties.asList().forEach { userProp ->
//                    add(userProp.name, userProp.value)
//                }
//            }.build()
//        )
//        builder.build()
//    }
//}