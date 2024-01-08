package com.i192.praktika.programavimopraktika;

public enum Scenes {
    MAIN_MENU("Main Menu", "main-menu", "style"),
    CHARACTER_SELECT("Character Select", "character-select", null),
    SETTINGS("Settings", "settings", null),
    PLAYERS_JOIN("Players Join", "players-join", null),
    GAMEMODE_MENU("Gamemode Menu", "gamemode-menu", null),
    ONE_VS_ONE_FIGHT("One VS One Fight", "one-vs-one-fight", null),
    GAME_STAGE("Game Stage", "game-stage", null);

    private final String sceneName;
    private final String fxmlFileName;
    private final String cssFileName;
    Scenes(String sceneName, String fxmlFileName, String cssFileName) {
        this.sceneName = sceneName;
        this.fxmlFileName = fxmlFileName;
        this.cssFileName = cssFileName;
    }

    public String getSceneName() {
        return sceneName;
    }

    public String getFxmlFileName() {
        return fxmlFileName;
    }

    public String getCssFileName() {
        return cssFileName;
    }
}
