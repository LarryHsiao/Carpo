package com.silverhetch.carpo.javafx.tag.overview

import com.jfoenix.controls.JFXDecorator
import com.silverhetch.carpo.javafx.Stylesheets
import com.silverhetch.carpo.tag.Tag
import com.silverhetch.clotho.Source
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

class TagOverviewStage(private val resources: ResourceBundle, private val selected: Tag) : Source<Unit> {
    override fun fetch() {
        val stage = Stage()
        stage.scene = Scene(
            FXMLLoader(javaClass.getResource("/TagOverview.fxml")).let { loader ->
                loader.resources = resources
                JFXDecorator(stage, loader.load<Parent>()).also { _ ->
                    loader.getController<TagOverviewView>().loadTag(selected)
                }
            }
        ).also { it.stylesheets.addAll(Stylesheets().fetch()) }
        stage.show()
    }
}