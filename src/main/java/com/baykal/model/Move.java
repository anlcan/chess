package com.baykal.model;

import java.util.Optional;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Move {
    private final Position current;
    private final Position next;
    private final Optional<Piece> target;

    public Move(Position current, Position next, Optional<Piece> target) {
        this.next = next;
        this.current = current;
        this.target = target;
    }

    public Position getCurrent() {
        return current;
    }

    public Position getNext() {
        return next;
    }

    public boolean isCheck() {
        return isCapture() && target.get().getKind() == Kind.KING;
    }

    public boolean isCapture() {
        return target.isPresent();
    }

    public Piece getTarget() {
        return target.get();
    }
}
