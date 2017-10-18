package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Move {
    private final Position current;
    private final Position next;
    private boolean isCheck = false;

    public Move(Position current, Position next, boolean check) {
        this.next = next;
        this.current = current;
        this.isCheck = check;
    }

    public Position getCurrent() {
        return current;
    }

    public Position getNext() {
        return next;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
