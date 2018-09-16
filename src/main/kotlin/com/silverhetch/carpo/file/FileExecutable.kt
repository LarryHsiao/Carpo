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
                    File(URI.create(uri)).also { targetFile ->
                        if (targetFile.isDirectory && targetFile.listFiles().size == 1) {
                            Desktop.getDesktop().open(targetFile.listFiles()[0])
                        } else {
                            Desktop.getDesktop().open(targetFile)
                        }
                    }
                } catch (e: IOException) {
                    callback.onFailed()
                }
            }.start()
        }
    }
}