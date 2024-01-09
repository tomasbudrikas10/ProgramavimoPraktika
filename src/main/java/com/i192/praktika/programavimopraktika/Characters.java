package com.i192.praktika.programavimopraktika;

public enum Characters {
    BOB_THE_CAT("Bob The Cat", "Bob is a cool cat!", "player1.png"),
    ROB_THE_CAT("Rob The Cat", "Rob is a less cool cat than Bob :(", "player2.png");

    private String name;
    private String description;
    private String imageName;

    Characters(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
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
