package dev.pages.ahsan40.dlf.main;

import dev.pages.ahsan40.dlf.utils.Utils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main extends Application {
    public static File file;
    public static TextFile textFile;
    public static Stage primaryStage;
    public static Scene scene;
    public static Parent root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws URISyntaxException {
        // init
        primaryStage = stage;

        // Window Settings
        primaryStage.setTitle(Configs.title + " " + Configs.version);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Configs.icon)).toURI().toString()));
        Utils.changeScene(Configs.homePage, 600, 800);

        // Window Style
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        // Show
        primaryStage.show();
    }
}