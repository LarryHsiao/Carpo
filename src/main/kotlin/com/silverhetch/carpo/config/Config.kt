package com.silverhetch.carpo.config

import java.io.File

/**
 * Represent the configuration of Carpo
 */
interface Config {
    /**
     * The workspace path of this instance.
     */
    fun workspacePath(): String

    /***
     * Change the workspace path in config file
     */
    fun changeWorkspacePath(newWorkspace:File)

    /**
     * Clear config.
     */
    fun clear()
}