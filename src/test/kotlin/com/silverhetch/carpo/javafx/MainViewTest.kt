package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.tag.Tag
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import java.util.*

class MainViewTest : ApplicationTest() {
    private lateinit var parent: Parent

    override fun start(stage: Stage) {
        super.start(stage)
        File(System.getProperty("user.home") + "/Playground").let {
            if (it.exists()) {
                it.deleteRecursively()
            }
        }
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
        from(lookup("#tagList").nth(1)).queryListView<Tag>().also { tagList ->
            assertEquals(
                newTagName,
                tagList.items[0].title()
            )
            assertEquals(
                1,
                tagList.items.size
            )
        }

    }

    /**
     * When: Insert same name tag twice.
     * Expected: Should have only one tag exist.
     */
    @Test
    fun existTagToFile() {
        val existTagName = UUID.randomUUID().toString()
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(1).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        from(lookup("#tagList").nth(1)).queryListView<Tag>().also { tagList ->
            assertEquals(
                existTagName,
                tagList.items[0].title()
            )
            assertEquals(
                1,
                tagList.items.size
            )
        }
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
            assertEquals(
                1,
                it.items.size
            )
            assertEquals(
                newTagName,
                it.items[0].tags().all()[newTagName]!!.title()
            )
        }

    }

    @Ignore("Can`t handle context menu test in CI servers.")
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
            assertEquals(
                1,
                it.items.size
            )
            assertEquals(
                newFileName,
                it.items[0].title()
            )
        }
    }
}