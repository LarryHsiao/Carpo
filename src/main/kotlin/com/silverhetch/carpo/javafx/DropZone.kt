package com.silverhetch.carpo.javafx

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.input.DataFormat
import javafx.scene.input.TransferMode
import javafx.scene.layout.AnchorPane
import java.net.URL
import java.util.*

class DropZone : Initializable {
    @FXML
    private lateinit var dropZone: AnchorPane
    @FXML
    private lateinit var info: Label

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        dropZone.setOnDragOver {
            if (it.dragboard.hasContent(DataFormat.PLAIN_TEXT)) {
                it.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            }
            it.consume()
        }
        dropZone.setOnDragDropped {
            info.text = it.dragboard.string
            it.isDropCompleted = true
            it.consume()
        }
    }
}