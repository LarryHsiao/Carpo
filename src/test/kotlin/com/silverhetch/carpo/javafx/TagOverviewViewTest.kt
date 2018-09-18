package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.CarpoImpl
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.file.phantom.PhantomCFile
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.workspace.CarpoWorkspace
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.nio.file.Files
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
            from(lookup("#tagName")).query<JFXTextField>().text
        )

        Assert.assertEquals(
            0,
            from(lookup("#fileList")).queryListView<Tag>().items.size
        )
    }


    @Test
    fun dropFunctionTest() {
        val newTagName = "NewTag" + UUID.randomUUID().toString().substring(0, 5)
        tagOverview.loadTag(CarpoImpl(
            CarpoWorkspace(
                DefaultWorkspaceFile().fetch()
            )
        ).tags().addTag(newTagName))

        tagOverview.onDroppedFiles(listOf(
            Files.createTempFile("", "").toFile()
        ))

        Assert.assertEquals(
            1,
            from(lookup("#fileList")).queryListView<CFile>().items.size
        )
    }
}