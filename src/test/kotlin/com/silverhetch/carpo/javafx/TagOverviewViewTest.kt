package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*

class TagOverviewViewTest : ApplicationTest() {
    private lateinit var tagOverview: TagOverviewView

    override fun start(stage: Stage) {
        super.start(stage)
        DefaultWorkspaceFile().fetch().let {
            if (it.exists()) {
                it.deleteRecursively()
            }
            it.mkdir()
        }
        FXMLLoader().let {
            it.resources = ResourceBundle.getBundle("ui/i18n/default")
            it.location = javaClass.getResource("/ui/TagOverview.fxml")
            stage.scene = Scene(it.load())
            tagOverview = it.getController<TagOverviewView>()
            stage.show()
        }
    }

    @Test
    fun newTagWithNoAttachedFiles() {
        val newTagName = "NewTag" + UUID.randomUUID().toString().substring(0, 5)
        tagOverview.loadTag(CarpoImpl(
            CarpoWorkspace(
                DefaultWorkspaceFile().fetch()
            )
        ).tags().addTag(newTagName))

        Assert.assertEquals(
            newTagName,
            from(lookup("#tagName")).query<TextField>().text
        )

        Assert.assertEquals(
            0,
            from(lookup("#fileList")).queryListView<Tag>().items.size
        )
    }
}