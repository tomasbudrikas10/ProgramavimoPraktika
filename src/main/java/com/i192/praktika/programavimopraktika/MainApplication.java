package com.i192.praktika.programavimopraktika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        MainMenuViewController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);
        scene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Main Menu");
    }

    public static void main(String[] args) {
        launch();
    }
}