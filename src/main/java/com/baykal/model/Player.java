package com.baykal.model;


/**
 * User: anlcan Date: 17/10/2017 Time: 21:43
 */
public class Player {

    private final String name;
    private final Type type;

    public Player(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Move getMove(Board board) {
        // GAME LOGIC GOES HERE
        return null;
    }
}
