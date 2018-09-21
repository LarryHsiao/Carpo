package com.silverhetch.carpo.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * UI Application implemented with JavaFx.
 */
public class UIApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Parent parent = FXMLLoader.load(
                getClass().getResource("/ui/Main.fxml"),
                ResourceBundle.getBundle("ui/i18n/default")
        );
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
