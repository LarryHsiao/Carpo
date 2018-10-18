package com.silverhetch.carpo.javafx.utility.dialog

import javafx.application.Platform.runLater
import javafx.scene.Scene
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS

class RenameDialogTest : ApplicationTest() {
    private lateinit var stage: Stage

    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
        this.stage.scene = Scene(StackPane())
        this.stage.show()
    }

    @Test
    fun confirm() {
        val countDownLatch = CountDownLatch(1)
        lateinit var result: String
        runLater {
            RenameDialog(
                stage,
                ResourceBundle.getBundle("i18n/default")
            ) { newName ->
                result = newName
                countDownLatch.countDown()
            }.fetch()
        }
        Thread.sleep(1000)
        clickOn(lookup("#renameField").query<TextField>())
        write("NewNameInput").push(KeyCode.ENTER)
        clickOn(lookup("#confirmButton").queryButton())
        countDownLatch.await(1, SECONDS)
        assertEquals(0, countDownLatch.count)
        assertEquals("NewNameInput", result)
    }

    @Test
    fun cancel() {
        val countDownLatch = CountDownLatch(1)
        runLater {
            RenameDialog(
                stage,
                ResourceBundle.getBundle("i18n/default")
            ) {
                fail()
            }.fetch()
        }
        Thread.sleep(1000)
        clickOn(lookup("#cancelButton").queryButton())
        countDownLatch.await(2, SECONDS)
        assertEquals(1, countDownLatch.count)
    }

}