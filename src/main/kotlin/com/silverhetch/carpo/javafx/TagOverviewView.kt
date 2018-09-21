package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.javafx.utility.draging.JdkFileDraging
import com.silverhetch.carpo.tag.Tag
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.VBox
import java.net.URL
import java.util.*

class TagOverviewView : Initializable {
    @FXML private lateinit var root: VBox
    @FXML private lateinit var tagName: JFXTextField
    @FXML private lateinit var fileListController: FileListView
    @FXML private lateinit var tag: Tag

    override fun initialize(location: URL?, resources: ResourceBundle) {
        JdkFileDraging { files ->
            files.forEach { newFile ->
                tag.files().add(newFile).also { cfile ->
                    fileListController.appendCFile(cfile)
                }
            }
        }.let {
            root.onDragOver = it
            root.onDragDropped = it
        }
    }

    fun loadTag(tag: Tag) {
        this.tag = tag
        tagName.text = tag.title()
        fileListController.loadList(tag.files().all())
    }
}