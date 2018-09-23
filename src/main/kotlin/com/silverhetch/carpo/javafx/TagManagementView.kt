package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.tag.Tags
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import java.net.URL
import java.util.*

/**
 * Tag management with given [Tags]
 */
class TagManagementView : Initializable {
    @FXML private lateinit var tagListController: TagListView
    @FXML private lateinit var searchField: TextField

    private lateinit var tags: Tags

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        searchField.textProperty().addListener { _, _, newValue ->
            tagListController.loadTags(tags.byName(newValue))
        }
    }

    /**
     * Load the given [Tags]
     */
    fun loadTags(tags: Tags) {
        this.tags = tags
        tagListController.loadTags(tags.all())
    }
}