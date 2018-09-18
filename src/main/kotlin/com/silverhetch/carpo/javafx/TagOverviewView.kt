package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.javafx.utility.DraggingBehavior
import com.silverhetch.carpo.tag.Tag
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.VBox
import java.io.File
import java.net.URL
import java.util.*

class TagOverviewView : Initializable, DraggingBehavior.Events {
    @FXML private lateinit var root: VBox
    @FXML private lateinit var tagName: JFXTextField
    @FXML private lateinit var fileListController: FileListView
    @FXML private lateinit var tag: Tag
    private val dragBehavior = DraggingBehavior(this)

    override fun initialize(location: URL?, resources: ResourceBundle) {
        root.onDragOver = dragBehavior
        root.onDragDropped = dragBehavior
    }

    fun loadTag(tag: Tag) {
        this.tag = tag
        tagName.text = tag.title()
        fileListController.loadList(tag.files().all())
    }

    override fun onDroppedFiles(files: List<File>) {
        files.forEach { newFile ->
            tag.files().add(newFile).also { cfile ->
                fileListController.appendCFile(cfile)
            }
        }

    }
}