package com.silverhetch.carpo.javafx.tag.overview

import com.silverhetch.carpo.tag.PhantomTag
import javafx.application.Platform
import javafx.scene.Parent
import javafx.stage.Stage
import org.junit.Assert
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.util.*

class TagOverviewStageTest : ApplicationTest() {
    private lateinit var stage: Stage
    override fun start(stage: Stage?) {
        super.start(stage)
        this.stage = stage!!
    }

    @Test
    fun simple() {
        Platform.runLater {
            TagOverviewStage(
                ResourceBundle.getBundle("i18n/default"),
                PhantomTag(),
                stage
            ).fetch()
        }
        Thread.sleep(1000)

        Assert.assertTrue(
            from(lookup("#TagOverviewWindow")).query<Parent>().isVisible
        )
    }
}