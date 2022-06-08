package com.linole.hivemq.extension.authentication

import com.hivemq.extension.sdk.api.ExtensionMain
import com.hivemq.extension.sdk.api.parameter.ExtensionInformation
import com.hivemq.extension.sdk.api.parameter.ExtensionStartInput
import com.hivemq.extension.sdk.api.parameter.ExtensionStartOutput
import com.hivemq.extension.sdk.api.parameter.ExtensionStopInput
import com.hivemq.extension.sdk.api.parameter.ExtensionStopOutput
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Main : ExtensionMain {
    private val log: Logger = LoggerFactory.getLogger(Main::class.java)


    @Override
    override fun extensionStart(extensionStartInput: ExtensionStartInput, extensionStartOutput: ExtensionStartOutput) {
        try {
            if (Config.enabled) {
                val extensionInformation: ExtensionInformation = extensionStartInput.extensionInformation
                log.info("Started " + extensionInformation.name.toString() + ":" + extensionInformation.version)
            }
        } catch (e: Exception) {
            log.error("Exception thrown at extension start: ", e)
        }
    }

    @Override
    override fun extensionStop(extensionStopInput: ExtensionStopInput, extensionStopOutput: ExtensionStopOutput) {
        if (Config.enabled) {
            val extensionInformation: ExtensionInformation = extensionStopInput.extensionInformation
            log.info("Stopped " + extensionInformation.name.toString() + ":" + extensionInformation.version)
        }
    }
}