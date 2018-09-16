package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.file.CFile
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

/**
 * View representing info of given file.
 */
class FileInfoView : Initializable {
    @FXML
    private lateinit var tagList: JFXListView<String>
    @FXML
    private lateinit var fileName: JFXTextField
    @FXML
    private lateinit var tagName: JFXTextField

    private lateinit var cfile: CFile

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        tagList.items = ObservableListWrapper<String>(ArrayList<String>())
        tagList.setCellFactory {
            object : JFXListCell<String>() {
                override fun updateItem(item: String?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        text = ""
                        graphic = null
                    } else {
                        text = item
                        graphic = ImageView(
                            Image(
                                javaClass.getResource("/ui/icon/tag.svg").toURI().toString(),
                                32.0,
                                32.0,
                                true,
                                true)
                        )
                    }
                }
            }
        }
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
        with(tagList.items) {
            clear()
            cfile.tags().all().let { tags ->
                tags.values.forEach { tag ->
                    add(tag.title())
                }
            }
        }
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