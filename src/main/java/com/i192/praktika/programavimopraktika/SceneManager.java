package com.i192.praktika.programavimopraktika;

import com.i192.praktika.programavimopraktika.fxml.Initialisable;
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
    private static Map<String, Initialisable> sceneInitMap;
    private static Map<String, FXMLLoader> fxmlLoaderMap;

    private SceneManager() throws IOException {
        stage = new Stage();
        sceneMap = new HashMap<>();
        sceneInitMap = new HashMap<>();
        fxmlLoaderMap = new HashMap<>();
        loadAllFXMLScenes();
    }

    public static SceneManager getInstance() throws IOException {
        if (sceneManager == null) {
            sceneManager = new SceneManager();
        }
        return sceneManager;
    }

    public void cleanOldScenes() throws IOException {
        loadFXMLScene(Scenes.PLAYERS_JOIN);
        loadFXMLScene(Scenes.CHARACTER_SELECT);
        loadFXMLScene(Scenes.ONE_VS_ONE_FIGHT);
    }

    private void loadFXMLScene(Scenes sceneData) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/i192/praktika/programavimopraktika/fxml/" + sceneData.getFxmlFileName() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        if (sceneData.getCssFileName() != null) {
            scene.getStylesheets().add(MainApplication.class.getResource("/com/i192/praktika/programavimopraktika/css/" + sceneData.getCssFileName() + ".css").toExternalForm());
        }

        sceneMap.put(sceneData.getSceneName(), scene);
        sceneInitMap.put(sceneData.getSceneName(), fxmlLoader.getController());
        fxmlLoaderMap.put(sceneData.getSceneName(), fxmlLoader);
    }

    private void loadAllFXMLScenes() throws IOException {
        for (Scenes scene: Scenes.values()) {
            loadFXMLScene(scene);
        }
    }

    public FXMLLoader getLoader(Scenes sceneData){
        return fxmlLoaderMap.get(sceneData.getSceneName());
    }

    public void setScene(Scenes sceneData) {
        stage.setScene(sceneMap.get(sceneData.getSceneName()));
        stage.setTitle(sceneData.getSceneName());
        sceneInitMap.get(sceneData.getSceneName()).initialise();
    }

    public void setStage(Stage newStage) {
        stage = newStage;
    }

    public Stage getStage() {
        return stage;
    }
}
