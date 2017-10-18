package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Move {
    private final Position current;
    private final Position next;

    public Move(Position current, Position next) {
        this.next = next;
        this.current = current;
    }

    public Position getCurrent() {
        return current;
    }

    public Position getNext() {
        return next;
    }



}
