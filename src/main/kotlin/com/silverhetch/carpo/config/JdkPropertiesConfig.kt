package com.silverhetch.carpo.config

import java.util.*

/**
 * Implemented with [Properties] provide by JDK.
 */
class JdkPropertiesConfig(private val properties: Properties, private val config: Config) : Config {
    override fun workspacePath(): String {
        return properties.getProperty("workspace", config.workspacePath())
    }
}