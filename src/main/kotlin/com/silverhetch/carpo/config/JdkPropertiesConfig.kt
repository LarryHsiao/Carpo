package com.silverhetch.carpo.config

import java.io.File
import java.util.*

/**
 * Implemented with [Properties] provide by JDK.
 */
class JdkPropertiesConfig(private val jdkFile: File, private val properties: Properties, private val default: Config) : Config {
    override fun workspacePath(): String {
        return properties.getProperty("workspace", default.workspacePath())
    }

    override fun changeWorkspacePath(newWorkspace: File) {
        if (newWorkspace.isDirectory) {
            properties["workspace"] = newWorkspace.absolutePath
            properties.store(jdkFile.outputStream(), "Modified by Carpo")
        }
    }
}