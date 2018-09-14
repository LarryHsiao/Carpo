package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.file.CFile
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.assertions.api.Assertions.assertThat
import org.testfx.framework.junit.ApplicationTest
import org.testfx.util.WaitForAsyncUtils
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class MainViewTest : ApplicationTest() {
    private lateinit var parent: Parent

    override fun start(stage: Stage) {
        super.start(stage)
        parent = FXMLLoader.load<Parent>(
            javaClass.getResource("/ui/Main.fxml"),
            ResourceBundle.getBundle("ui/i18n/default")
        )
        stage.scene = Scene(parent)
        stage.show()
    }

    @Test
    fun newTagToFile() {
        val newTagName = UUID.randomUUID().toString()
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(newTagName).push(KeyCode.ENTER)

        assertThat(
            from(lookup("#tagList")).queryListView<String>()
        ).hasListCell(newTagName)
    }

    @Test
    fun existTagToFile() {
        val existTagName = UUID.randomUUID().toString()
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(1).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        assertThat(
            from(lookup("#tagList")).queryListView<String>()
        ).hasListCell(existTagName)
    }


    @Test
    fun searchWithTagName() {
        val newTagName = UUID.randomUUID().toString()
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(newTagName).push(KeyCode.ENTER)

        clickOn(lookup("#searchKey").query<JFXTextField>())
        write(newTagName).push(KeyCode.ENTER)

        from(lookup("#fileList")).queryListView<CFile>().also {
            Assert.assertEquals(
                1,
                it.items.size
            )
            Assert.assertEquals(
                newTagName,
                it.items[0].tags().all()[newTagName]!!.title()
            )
        }

    }

    @Test
    fun renameFile_checkWithSearch() {
        val newFileName = UUID.randomUUID().toString()
        rightClickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        rightClickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#rename").query<Node>())
        write(newFileName).push(KeyCode.ENTER)

        clickOn(lookup("#searchKey").query<JFXTextField>())
        write(newFileName).push(KeyCode.ENTER)

        from(lookup("#fileList")).queryListView<CFile>().also {
            Assert.assertEquals(
                1,
                it.items.size
            )
            Assert.assertEquals(
                newFileName,
                it.items[0].title()
            )
        }
    }
}