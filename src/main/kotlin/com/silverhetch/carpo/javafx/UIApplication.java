package com.silverhetch.carpo.javafx;

import com.jfoenix.controls.JFXDecorator;
import com.silverhetch.carpo.threads.WorkerThread;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        SvgImageLoaderFactory.install();
        final Parent parent = FXMLLoader.load(
                getClass().getResource("/Main.fxml"),
                ResourceBundle.getBundle("i18n/default")
        );
        stage.getIcons().add(new Image(getClass().getResource("/icon/alpha-c-box.svg").toString()));
        Scene scene = new Scene(new JFXDecorator(stage, parent));
        scene.getStylesheets().addAll(new Stylesheets().fetch());
        stage.setScene(scene);
        stage.show();
    }
}
