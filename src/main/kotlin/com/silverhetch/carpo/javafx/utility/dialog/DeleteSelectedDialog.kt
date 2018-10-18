package com.silverhetch.carpo.javafx.utility.dialog

import com.jfoenix.controls.JFXAlert
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialogLayout
import com.silverhetch.clotho.Source
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.stage.Modality
import javafx.stage.Stage
import java.util.*

/**
 * Delete confirm dialog.
 */
class DeleteSelectedDialog(private val stage: Stage, private val bundle: ResourceBundle, private val confirmHandler: EventHandler<ActionEvent>) : Source<Unit> {
    override fun fetch() {
        val dialog = JFXAlert<Unit>(stage)
        dialog.initModality(Modality.APPLICATION_MODAL)
        dialog.isHideOnEscape = false
        dialog.isOverlayClose = false
        dialog.setContent(JFXDialogLayout().also { layout ->
            layout.id = "deleteDialog"
            layout.setHeading(Label(bundle.getString("General.delete")))
            layout.setBody(Label(bundle.getString("General.deleteSelected")))
            layout.setActions(
                JFXButton(bundle.getString("General.confirm")).also { button ->
                    button.setOnAction {
                        confirmHandler.handle(it)
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