package com.silverhetch.carpo.javafx.server

import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.javafx.Stylesheets
import com.silverhetch.carpo.javafx.utility.jfoenix.JFXDecoratorParent
import com.silverhetch.clotho.Source
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

/**
 * Source that build and show server status.
 */
class ServerStatusStage(private val resources: ResourceBundle, private val carpo: Carpo, private val stage: Stage) : Source<Unit> {
    override fun fetch() {
        stage.scene = Scene(
            FXMLLoader(javaClass.getResource("/ServerStatus.fxml")).let { loader ->
                loader.resources = resources
                val view = JFXDecoratorParent(stage, loader.load()).fetch()
                view.id = "SeverStatusView"
                loader.getController<ServerStatusView>().loadCarpo(carpo)
                view
            }
        ).also { it.stylesheets.addAll(Stylesheets().fetch()) }
        stage.show()
    }
}