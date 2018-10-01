package com.silverhetch.carpo.file.views

/**
 * Views records
 */
interface Views {
    /**
     * All of [View]
     */
    fun all(): List<View>

    /**
     * New view
     */
    fun newView(name: String): View
}