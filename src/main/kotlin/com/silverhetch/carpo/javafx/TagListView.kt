package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.TagNameComparator
import com.silverhetch.clotho.utility.comparator.StringComparator
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class TagListView : Initializable {
    @FXML
    private lateinit var tagList: JFXListView<Tag>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        tagList.items = ObservableListWrapper<Tag>(ArrayList<Tag>())
        tagList.setCellFactory {
            object : JFXListCell<Tag>() {
                override fun updateItem(item: Tag?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        text = ""
                        graphic = null
                    } else {
                        text = item?.title() ?: ""
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

    fun loadTags(tags: Map<String, Tag>) {
        with(tagList.items) {
            clear()
            addAll(tags.values.sortedWith(
                TagNameComparator(
                    StringComparator()
                )
            ))
        }
    }
}