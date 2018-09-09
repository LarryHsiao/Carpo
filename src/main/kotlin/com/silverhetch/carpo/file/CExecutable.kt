package com.silverhetch.carpo.file

/**
 * Represents resources can be 'Launch'.
 *
 * It may be a file uri which launch with given application? Or just launched with platform default application.
 */
interface CExecutable {
    /**
     * Callback class for every launches.
     */
    interface Callback {
        /**
         * Invoked when failed.
         */
        fun onFailed()
    }

    /**
     * Launch this executable object.
     */
    fun launch(callback: Callback)
}