package com.silverhetch.carpo.javafx.tag.overview

import com.silverhetch.carpo.javafx.file.FileListView
import com.silverhetch.carpo.javafx.utility.draging.JdkFileDraging
import com.silverhetch.carpo.tag.Tag
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.effect.ColorAdjust
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import java.net.URL
import java.util.*

class TagOverviewView : Initializable {
    @FXML private lateinit var root: VBox
    @FXML private lateinit var tagName: Label
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
        tagName.graphic = ImageView(
            Image(
                javaClass.getResource("/icon/tag.svg").toURI().toString(),
                24.0,
                24.0,
                true,
                true
            )
        ).also { imageView ->
            imageView.effect = ColorAdjust().also {
                it.hue = 0.0
                it.brightness = 1.0
            }
        }
    }

    /**
     * Load view with given [Tag]
     */
    fun loadTag(tag: Tag) {
        this.tag = tag
        tagName.text = tag.title()
        fileListController.loadList(tag.files().all())
    }
}