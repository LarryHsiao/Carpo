package com.silverhetch.carpo.javafx;

import com.sun.javafx.css.StyleManager;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * UI implementation of [Carpo]
 */
public class UIApplication extends Application {
    public static void main(String[] args) {
        SvgImageLoaderFactory.install();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        StyleManager.getInstance().addUserAgentStylesheet(
                getClass().getResource("/ui/css/General.css").toURI().toString()
        );
        final Parent parent = FXMLLoader.load(
                getClass().getResource("/ui/DropZone.fxml"),
                ResourceBundle.getBundle("ui/i18n/default")
        );
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
