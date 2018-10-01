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

class TagOverviewStage(private val resources: ResourceBundle, private val selected: Tag, private val stage: Stage = Stage()) : Source<Unit> {
    override fun fetch() {
        stage.scene = Scene(
            FXMLLoader(javaClass.getResource("/TagOverview.fxml")).let { loader ->
                loader.resources = resources
                val view = if (stage.isShowing) {
                    loader.load<Parent>()
                } else {
                    JFXDecorator(stage, loader.load<Parent>())
                }
                view.id = "TagOverviewWindow"
                loader.getController<TagOverviewView>().loadTag(selected)
                view
            }
        ).also { it.stylesheets.addAll(Stylesheets().fetch()) }
        stage.show()
    }
}