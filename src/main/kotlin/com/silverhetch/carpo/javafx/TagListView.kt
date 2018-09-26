package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.silverhetch.carpo.javafx.utility.ContextMenuFactory
import com.silverhetch.carpo.javafx.utility.GeneralContextMenuFactory
import com.silverhetch.carpo.javafx.utility.RenameAction
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.tag.TagNameComparator
import com.silverhetch.carpo.tag.factory.UriTagFactory
import com.silverhetch.clotho.utility.comparator.StringComparator
import com.sun.javafx.collections.ObservableListWrapper
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.MouseButton.PRIMARY
import javafx.scene.input.TransferMode
import javafx.stage.Stage
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


/**
 * Represent list with given [Tag].
 */
class TagListView : Initializable {
    @FXML
    private lateinit var tagList: JFXListView<Tag>

    override fun initialize(location: URL?, resources: ResourceBundle) {
        tagList.items = ObservableListWrapper<Tag>(ArrayList<Tag>())
        tagList.setCellFactory {
            object : JFXListCell<Tag>() {
                init {
                    setOnContextMenuRequested { menu ->
                        contextMenu = GeneralContextMenuFactory(object : ContextMenuFactory.Events {
                            override fun onItemClicked(id: String) {
                                when (id) {
                                    "rename" -> {
                                        RenameAction(resources).fetch().let { result ->
                                            if (result.isEmpty().not()) {
                                                item.rename(result)
                                                updateItem(item, false)
                                            }
                                        }
                                    }
                                    "delete" -> {
                                        item.remove()
                                        tagList.items.remove(item)
                                    }
                                }
                            }
                        }, resources).fetch()
                    }

                    setOnDragDetected { dropEvent ->
                        val dragboard = startDragAndDrop(TransferMode.MOVE, TransferMode.LINK)
                        dragboard.setContent(ClipboardContent().also { content ->
                            content[DataFormat.URL] = UriTagFactory.TagUri(item).fetch()
                            dragboard.dragView = Image(
                                javaClass.getResource("/icon/tag.svg").toURI().toString(),
                                32.0,
                                32.0,
                                true,
                                true
                            )
                        })
                        dropEvent.consume()
                    }
                }

                override fun updateItem(item: Tag?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty) {
                        text = ""
                        graphic = null
                    } else {
                        text = item?.title() ?: ""
                        graphic = ImageView(
                            Image(
                                javaClass.getResource("/icon/tag.svg").toURI().toString(),
                                32.0,
                                32.0,
                                true,
                                true)
                        )
                    }
                }
            }
        }
        tagList.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.button == PRIMARY && mouseEvent.clickCount == 2) {
                tagList.selectionModel.selectedItem?.also { selected ->
                    val stage = Stage()
                    stage.title = selected.title()
                    stage.scene = Scene(
                        FXMLLoader(javaClass.getResource("/TagOverview.fxml")).let { loader ->
                            loader.resources = resources
                            loader.load<Parent>().also { _ ->
                                loader.getController<TagOverviewView>().loadTag(selected)
                            }
                        }
                    )
                    stage.show()
                }
            }
        }
    }

    /**
     * Load list with given tags.
     */
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