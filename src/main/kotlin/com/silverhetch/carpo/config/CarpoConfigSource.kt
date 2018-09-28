package com.silverhetch.carpo.config

import com.silverhetch.clotho.Source
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Source class that builds [Config] instance for Carpo.
 */
class CarpoConfigSource(private val path: String = System.getProperty("user.home") + File.separator + ".carpo.config") : Source<Config> {
    override fun fetch(): Config {
        return try {
            JdkPropertiesConfig(
                Properties().apply {
                    load(File(path).inputStream())
                },
                ConstConfig()
            )
        } catch (e: IOException) {
            ConstConfig()
        }
    }
}