package com.silverhetch.carpo.javafx.server

import com.silverhetch.carpo.Carpo
import com.silverhetch.carpo.server.CarpoServers
import com.silverhetch.carpo.server.Server
import com.silverhetch.carpo.threads.WorkerThread
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

class ServerStatusView : Initializable {
    @FXML private lateinit var enabledToggle: ToggleButton
    @FXML private lateinit var hostname: Label
    @FXML private lateinit var port: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
    }

    fun loadCarpo(carpo: Carpo) {
        val server = CarpoServers(carpo).fileServer()
        updateUI(server)

        enabledToggle.selectedProperty().addListener { observable, oldValue, newValue ->
            if (newValue) {
                server.start()
            } else {
                server.stop()
            }

            updateUI(server)
        }
        WorkerThread.getInstance().scheduleAtFixedRate({
            Platform.runLater {
                updateUI(server)
            }
        }, 0, 5, TimeUnit.SECONDS)
    }

    private fun updateUI(server: Server) {
        enabledToggle.isSelected = server.isRunning()
        hostname.text = server.hostname()
        port.text = server.port().toString()
    }
}