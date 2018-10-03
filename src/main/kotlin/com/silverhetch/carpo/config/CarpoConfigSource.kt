package com.silverhetch.carpo.config

import com.silverhetch.clotho.Source
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Source class that builds [Config] instance for Carpo.
 */
class CarpoConfigSource(private val path: String = System.getProperty("user.dir") + File.separator + ".carpo.config") : Source<Config> {
    override fun fetch(): Config {
        return try {
            val config = File(path)
            if (!config.exists()) {
                config.createNewFile()
            }
            JdkPropertiesConfig(
                config,
                Properties().apply {
                    load(config.inputStream())
                },
                ConstConfig()
            )
        } catch (e: IOException) {
            ConstConfig()
        }
    }
}