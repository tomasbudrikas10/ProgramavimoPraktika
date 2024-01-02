package com.i192.praktika.programavimopraktika;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager sceneManager;
    private static Stage stage;
    private static Map<String, Scene> sceneMap;

    private SceneManager() {
        stage = new Stage();
        sceneMap = new HashMap<>();
    }

    public static SceneManager getInstance() throws IOException {
        if (sceneManager == null) {
            sceneManager = new SceneManager();
        }
        return sceneManager;
    }

    public void loadFXMLScene(String fileName, String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/i192/praktika/programavimopraktika/fxml/" + fileName + ".fxml"));
        sceneMap.put(sceneName, new Scene(fxmlLoader.load()));
    }

    public void loadFXMLScene(String fileName, String sceneName, String cssFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/i192/praktika/programavimopraktika/fxml/" + fileName + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(MainApplication.class.getResource("/com/i192/praktika/programavimopraktika/css/" + cssFileName + ".css").toExternalForm());
        sceneMap.put(sceneName, scene);
    }

    public void setScene(String name) {
        stage.setScene(sceneMap.get(name));
    }

    public void setStage(Stage newStage) {
        stage = newStage;
    }

    public Stage getStage() {
        return stage;
    }
}
