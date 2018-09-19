package com.silverhetch.carpo.javafx.utility

import com.silverhetch.clotho.Source
import javafx.scene.control.TextInputDialog
import java.util.*

/**
 * Note: this class will block the UI until rename action completed.
 */
class RenameAction(private val bundle: ResourceBundle) : Source<String> {
    override fun fetch(): String {
        with(TextInputDialog()) {
            this.headerText = bundle.getString("General.rename.hint")
            title = bundle.getString("General.rename")
            showAndWait() // block at this line.
            return result ?: ""
        }
    }
}