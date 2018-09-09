package com.silverhetch.carpo.file

import java.awt.Desktop
import java.io.File
import java.io.IOException
import java.net.URI

/**
 * Launch with given file with platform default application.
 */
class FileExecutable(private val uri: String) : CExecutable {
    override fun launch(callback: CExecutable.Callback) {
        if (Desktop.isDesktopSupported()) {
            Thread {
                try {
                    Desktop.getDesktop().open(File(URI.create(uri)))
                } catch (e: IOException) {
                    callback.onFailed()
                }
            }.start()
        }
    }
}