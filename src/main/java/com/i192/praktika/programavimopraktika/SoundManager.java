package com.i192.praktika.programavimopraktika;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class SoundManager {
    private static SoundManager soundManager;
    private static MediaPlayer mediaPlayer;
    private SoundManager() {
        Media sound = new Media(new File("src/main/java/music_bacground/backgroundMusic.m4a").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
        });
    }
    public static SoundManager getInstance() {
        if (soundManager == null) {
            soundManager = new SoundManager();
        }
        return soundManager;
    }
    public void setVolume(int percentage) {
        if (percentage > 100) {
            mediaPlayer.volumeProperty().set(0.1);
        } else if (percentage < 0) {
            mediaPlayer.volumeProperty().set(0);
        } else {
            mediaPlayer.volumeProperty().set(0.1 * (double) percentage/100);
        }
    }

    public void play() {
        mediaPlayer.play();
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
