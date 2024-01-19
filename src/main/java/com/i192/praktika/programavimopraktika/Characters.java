package com.i192.praktika.programavimopraktika;

import com.i192.praktika.programavimopraktika.game.Fighter;

public enum Characters {
    GIRL("Bob The Cat", "Bob is a cool cat!", "Kurmis.png", new Fighter()),
    BOY("Rob The Cat", "Rob is a less cool cat than Bob :(", "Girl.png", new Fighter()),
    SOLDIER("Bob The Cat", "Bob is a cool cat!", "Soldier.png", new Fighter()),
    ASASSIN("Rob The Cat", "Rob is a less cool cat than Bob :(", "Asassin.png", new Fighter());

    private String name;
    private String description;
    private String imageName;
    private Fighter fighter;

    Characters(String name, String description, String imageName, Fighter fighter) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.fighter = fighter;
    }

    public Fighter getFighter(){
        return fighter;
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
