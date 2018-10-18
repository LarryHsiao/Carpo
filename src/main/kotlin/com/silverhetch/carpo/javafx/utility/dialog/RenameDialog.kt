package com.silverhetch.carpo.javafx.utility.dialog

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.controls.JFXTextField
import com.silverhetch.clotho.Source
import javafx.scene.control.Label
import javafx.stage.Modality
import javafx.stage.Stage
import java.util.*

/**
 * Dialog for rename.
 */
class RenameDialog(private val stage: Stage, private val bundle: ResourceBundle, private val confirm: (newName: String) -> Unit) : Source<Unit> {
    override fun fetch() {
        val dialog = JFXAlert<Unit>(stage)
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.isHideOnEscape = false
        dialog.isOverlayClose = false
        dialog.setContent(JFXDialogLayout().also { layout ->
            layout.id = "renameDialog"
            layout.setHeading(Label(bundle.getString("General.rename")))
            val textField = JFXTextField()
            layout.setBody(textField.apply {
                promptText = bundle.getString("General.rename.hint")
            })
            layout.setActions(
                JFXButton(bundle.getString("General.confirm")).also { button ->
                    button.setOnAction {
                        confirm(textField.text)
                        dialog.hideWithAnimation()
                    }
                },
                JFXButton(bundle.getString("General.cancel")).also { button ->
                    button.setOnAction { dialog.hideWithAnimation() }
                }
            )
        })
        dialog.showAndWait()
    }
}