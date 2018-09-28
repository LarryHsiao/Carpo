package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.file.CFile
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import java.net.URL
import java.util.*

/**
 * View representing info of given file.
 */
class FileInfoView : Initializable {
    @FXML private lateinit var tagListController: TagListView
    @FXML private lateinit var fileName: JFXTextField
    @FXML private lateinit var tagName: JFXTextField

    private lateinit var cfile: CFile

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        // no implementation needed here
    }

    /**
     * Load the views with given [CFile].
     */
    fun loadCFile(newFile: CFile) {
        this.cfile = newFile
        updateUI()
    }

    private fun updateUI() {
        fileName.text = cfile.title()
        tagName.text = ""
        tagListController.loadTags(cfile.tags().all())
    }

    @FXML
    private fun tagNameKey(keyEvent: KeyEvent) {
        if (keyEvent.code == KeyCode.ENTER) {
            createTag()
        }
    }

    @FXML
    private fun createTag() {
        if (tagName.text.isNotEmpty()) {
            cfile.tags().addTag(tagName.text)
            updateUI()
        }
    }
}