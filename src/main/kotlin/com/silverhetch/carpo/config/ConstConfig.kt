package com.silverhetch.carpo.config

/**
 * The hard code version of [Config]. This class also known as default value of [Config].
 */
class ConstConfig : Config {
    override fun workspacePath(): String {
        return System.getProperty("user.dir")
    }
}