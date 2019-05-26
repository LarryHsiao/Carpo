package com.silverhetch.carpo.javafx.utility.dialog

import javafx.application.Platform.runLater
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS

class DeleteSelectedDialogTest : ApplicationTest() {
    private lateinit var stage: Stage

    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
        this.stage.scene = Scene(StackPane().apply {
            prefHeight = 480.0
            prefWidth = 640.0
        })
        this.stage.show()
    }

    @Test
    fun confirm() {
        val countDownLatch = CountDownLatch(1)
        runLater {
            DeleteSelectedDialog(
                stage,
                ResourceBundle.getBundle("i18n/default"),
                EventHandler {
                    countDownLatch.countDown()
                }
            ).value()
        }
        Thread.sleep(1000)
        clickOn(lookup("#confirmButton").queryButton())
        countDownLatch.await(1, SECONDS)
        assertEquals(0, countDownLatch.count)
    }

    @Test
    fun cancel() {
        val countDownLatch = CountDownLatch(1)
        runLater {
            DeleteSelectedDialog(
                stage,
                ResourceBundle.getBundle("i18n/default"),
                EventHandler {
                    fail()
                }
            ).value()
        }
        Thread.sleep(1000)
        clickOn(lookup("#cancelButton").queryButton())
        countDownLatch.await(2, SECONDS)
        assertEquals(1, countDownLatch.count)
    }

}