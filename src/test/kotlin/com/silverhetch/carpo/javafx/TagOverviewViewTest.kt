package com.silverhetch.carpo.javafx

import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.config.CarpoConfigSource
import com.silverhetch.carpo.javafx.tag.overview.TagOverviewView
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.workspace.CarpoWorkspace
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import java.nio.file.Files
import java.util.*

class TagOverviewViewTest : ApplicationTest() {
    private lateinit var tagOverview: TagOverviewView

    override fun start(stage: Stage) {
        super.start(stage)
        FXMLLoader().let {
            it.resources = ResourceBundle.getBundle("i18n/default")
            it.location = javaClass.getResource("/TagOverview.fxml")
            stage.scene = Scene(it.load())
            tagOverview = it.getController<TagOverviewView>()
            stage.show()
        }
    }

    @Test
    fun newTagWithNoAttachedFiles() {
        val newTagName = "NewTag" + UUID.randomUUID().toString().substring(0, 5)
        Platform.runLater {
            tagOverview.loadTag(CarpoImpl(
                CarpoWorkspace(
                    Files.createTempDirectory("test").toFile()
                )
            ).tags().addTag(newTagName))

            Assert.assertEquals(
                newTagName,
                from(lookup("#tagName")).query<Label>().text
            )

            Assert.assertEquals(
                0,
                from(lookup("#fileList")).queryListView<Tag>().items.size
            )
        }
    }
}