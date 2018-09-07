package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.file.CFile
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class FileInfoView : Initializable {
    @FXML
    private lateinit var tagList: JFXListView<String>
    @FXML
    private lateinit var fileInfo: Label
    @FXML
    private lateinit var tagName: JFXTextField

    private lateinit var cfile: CFile

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        tagList.items = ObservableListWrapper<String>(ArrayList<String>())
    }

    fun loadCFile(newFile: CFile) {
        this.cfile = newFile
        updateUI()
    }

    private fun updateUI() {
        fileInfo.text = cfile.title()
        with(tagList.items) {
            clear()
            cfile.tags().all().let { tags ->
                tags.values.forEach { tag ->
                    add(tag.title())
                }
            }
        }
    }

    fun createTag(mouseEvent: MouseEvent) {
        if (tagName.text.isNotEmpty()) {
            cfile.tags().addTag(tagName.text)
            updateUI()
        }
    }
}