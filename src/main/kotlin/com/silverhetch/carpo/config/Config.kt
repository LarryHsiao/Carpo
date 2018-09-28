package com.silverhetch.carpo.config

/**
 * Represent the configuration of Carpo
 */
interface Config {
    /**
     * The workspace path of this instance.
     */
    fun workspacePath(): String
}