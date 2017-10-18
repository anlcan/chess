package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Move {
    private Piece piece;
    private Position next;

    public Move(Piece piece, Position next) {
        this.piece = piece;
        this.next = next;
    }
}
