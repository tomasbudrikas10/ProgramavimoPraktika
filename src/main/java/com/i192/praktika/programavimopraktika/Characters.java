package com.i192.praktika.programavimopraktika;

import com.i192.praktika.programavimopraktika.game.Fighter;

public enum Characters {
    GIRL("Bob The Cat", "Bob is a cool cat!", "Kurmis.png"),
    BOY("Rob The Cat", "Rob is a less cool cat than Bob :(", "Girl.png"),
    SOLDIER("Bob The Cat", "Bob is a cool cat!", "Soldier.png"),
    ASASSIN("Rob The Cat", "Rob is a less cool cat than Bob :(", "Asassin.png");

    private String name;
    private String description;
    private String imageName;

    Characters(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }

    public static Fighter getFighter(Characters the){
        return new Fighter(the);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageName() {
        return this.imageName;
    }
}
