package com.silverhetch.carpo.config

import java.io.File

/**
 * The hard code version of [Config]. This class also known as default value of [Config].
 */
class ConstConfig : Config {
    private var workspacePath = System.getProperty("user.dir") +File.separator+ "Playground"
    override fun workspacePath(): String {
        return workspacePath
    }

    override fun changeWorkspacePath(newWorkspace: File) {
        if (newWorkspace.isDirectory) {
            workspacePath = newWorkspace.absolutePath
        }
    }
}