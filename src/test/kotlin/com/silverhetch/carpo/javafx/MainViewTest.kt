package com.silverhetch.carpo.javafx

import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import com.silverhetch.carpo.file.CFile
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.carpo.workspace.DefaultWorkspaceFile
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File
import java.util.*

class MainViewTest : ApplicationTest() {

    override fun start(stage: Stage) {
        super.start(stage)
        SvgImageLoaderFactory.install()
        DefaultWorkspaceFile().fetch().let { it ->
            if (it.exists()) {
                it.deleteRecursively()
            }
            it.mkdir()
            File(it, UUID.randomUUID().toString().substring(0, 5)).createNewFile()
        }
        val parent = FXMLLoader.load<Parent>(
            javaClass.getResource("/ui/Main.fxml"),
            ResourceBundle.getBundle("ui/i18n/default")
        )
        stage.scene = Scene(parent)
        stage.show()
    }

    @Test
    fun newTagToFile() {
        val newTagName = "new Tag" + UUID.randomUUID().toString().substring(0, 7)
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(newTagName).push(KeyCode.ENTER)
        from(lookup("#tagList").nth(2)).queryListView<Tag>().also { tagList ->
            assertTrue(
                tagList.items.let { list ->
                    var exist = false
                    for (tag in list) {
                        if (tag.title() == newTagName) {
                            exist = true
                            break
                        }
                    }
                    exist
                }
            )
        }

    }

    @Test
    fun dragTagToAttach() {
        val newTagName = "new Tag" + UUID.randomUUID().toString().substring(0, 7)
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write("PlaceHolder").push(KeyCode.ENTER)
        write(newTagName).push(KeyCode.ENTER)

        drag(from(lookup("#tagList").nth(2)).lookup(".list-cell").nth(1).query<JFXListView<String>>())

        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(1).query<JFXListView<String>>())

        from(lookup("#tagList").nth(2)).queryListView<Tag>().also { tagList ->
            assertTrue(
                tagList.items.let { list ->
                    var exist = false
                    for (tag in list) {
                        if (tag.title() == newTagName) {
                            exist = true
                            break
                        }
                    }
                    exist
                }
            )
        }
    }

    /**
     * When: Insert same name tag twice.
     * Expected: Should have only one tag exist.
     */
    @Test
    fun existTagToFile() {
        val existTagName = "exist tag" + UUID.randomUUID().toString().substring(0, 7)
        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(0).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        clickOn(from(lookup("#fileList")).lookup(".list-cell").nth(1).query<JFXListView<String>>())
        clickOn(lookup("#tagName").query<JFXTextField>())
        write(existTagName).push(KeyCode.ENTER)

        from(lookup("#tagList").nth(2)).queryListView<Tag>().also { tagList ->
            assertEquals(
                1,
                tagList.items.let { list ->
                    var exist = 0
                    for (tag in list) {
                        if (tag.title() == existTagName) {
                            exist++
                        }
                    }
                    exist
                }
            )
        }
    }


    @Test
    fun searchWithTagName() {
        val newTagName = "search with tag name " + UUID.randomUUID().toString().substring(0, 7)
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