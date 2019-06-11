package com.silverhetch.carpo.javafx.utility

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.controls.JFXTextField
import com.silverhetch.clotho.Source
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.stage.Modality
import javafx.stage.Stage
import java.util.*

/**
 * Note: this class will block the UI until rename action completed.
 */
class FieldInputDialog(private val stage: Stage, private val resource: ResourceBundle,
                       private val title: String) : Source<String> {
    override fun value(): String {
        val alert = JFXAlert<String>(stage)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.isHideOnEscape = false
        alert.isOverlayClose = false
        alert.setContent(JFXDialogLayout().also { layout ->
            val textField = JFXTextField().also { textField ->
                textField.id = "FieldInputDialogTextField"
                textField.promptText = resource.getString("General.title")
                textField.setOnKeyPressed {
                    if (it.code == KeyCode.ENTER) {
                        alert.result = textField.text
                        alert.hideWithAnimation()
                    }
                }
            }
            layout.setHeading(Label(title))
            layout.setBody(textField)
            layout.setActions(
                JFXButton(resource.getString("General.confirm")).also { closeButton ->
                    closeButton.setOnAction { _ ->
                        alert.result = textField.text
                        alert.hideWithAnimation()
                    }
                },
                JFXButton(resource.getString("General.cancel")).also { closeButton ->
                    closeButton.setOnAction { _ ->
                        alert.result = ""
                        alert.hideWithAnimation()
                    }
                }
            )
        })
        alert.showAndWait()
        return alert.result ?: ""
    }
}