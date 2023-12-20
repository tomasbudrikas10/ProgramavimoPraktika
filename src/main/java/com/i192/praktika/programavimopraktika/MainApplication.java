package com.i192.praktika.programavimopraktika;

import com.i192.praktika.programavimopraktika.fxml.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    //define scenes
    static Stage mainStage;
    static Scene titleScreenScene;
    static Scene mainMenuScene;
    static Scene settingsScene;
    static Scene playersJoinScene;
    static Scene characterSelectScene;
    static Scene gameScene;

    //define controllers
    static TitleScreen titleScreen;
    static MainMenu mainMenu;
    static Settings settings;
    static PlayersJoin playersJoin;
    static CharacterSelect characterSelect;
    static Game game;
    @Override
    public void start(Stage stage) throws IOException {
        //save stage
        mainStage = stage;

        //define views
        FXMLLoader titleScreenLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/title-screen.fxml"));
        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/main-menu.fxml"));
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/settings.fxml"));
        FXMLLoader playersJoinLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/players-join.fxml"));
        FXMLLoader characterSelectLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/character-select.fxml"));
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/com/i192/praktika/programavimopraktika/fxml/game.fxml"));


        //init scenes
        titleScreenScene = new Scene(titleScreenLoader.load(), 500, 500);
        mainMenuScene = new Scene(mainMenuLoader.load(), 500, 500);
        settingsScene = new Scene(settingsLoader.load(), 500, 500);
        playersJoinScene = new Scene(playersJoinLoader.load(), 500, 500);
        characterSelectScene = new Scene(characterSelectLoader.load(), 500, 500);
        gameScene = new Scene(gameLoader.load(), 500, 500);


        //init controllers
        titleScreen = titleScreenLoader.getController();
        mainMenu = mainMenuLoader.getController();
        settings = settingsLoader.getController();
        playersJoin = playersJoinLoader.getController();
        characterSelect = characterSelectLoader.getController();
        game = gameLoader.getController();

        //set stilesheets
        titleScreenScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        mainMenuScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        settingsScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        playersJoinScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        characterSelectScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        gameScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());
        gameScene.getStylesheets().add(getClass().getResource("/com/i192/praktika/programavimopraktika/css/style.css").toExternalForm());

        //enter title screen
        toTilteScreen();


        mainStage.show();
    }

    public static void toTilteScreen(){
        mainStage.setTitle("KVKFightX");
        mainStage.setScene(titleScreenScene);
    }
    public static void toMainMenu(){
        mainStage.setTitle("Main Menu");
        mainStage.setScene(mainMenuScene);
    }
    public static void toSettings(){
        mainStage.setTitle("Settings");
        mainStage.setScene(settingsScene);
    }

    public static void toPlayersJoin(){
        mainStage.setTitle("PlayersJoin");
        mainStage.setScene(playersJoinScene);
    }

    public static void toCharacterSelect(){
        mainStage.setTitle("CharacterSelect");
        mainStage.setScene(characterSelectScene);
    }

    public static void toGame(){
        mainStage.setTitle("KVKFightX");
        mainStage.setScene(characterSelectScene);
    }

    public static void QuitApplication(){
        mainStage.close();
    }






    public static void main(String[] args) {
        launch();
    }
}