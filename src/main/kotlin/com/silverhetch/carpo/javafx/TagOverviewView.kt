package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.tag.Tag
import javafx.fxml.FXML
import javafx.fxml.Initializable
import java.net.URL
import java.util.*

class TagOverviewView : Initializable {
    @FXML private lateinit var tagName: JFXTextField
    @FXML private lateinit var fileListController: FileListView
    @FXML private lateinit var tag: Tag

    override fun initialize(location: URL?, resources: ResourceBundle) {
    }

    fun loadTag(tag: Tag) {
        tagName.text = tag.title()
        fileListController.loadList(tag.files().all())
    }
}