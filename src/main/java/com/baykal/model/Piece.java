package com.baykal.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Piece {

    private Kind kind;

    private Type type;
    private Position current;
    private List<Position> past = new ArrayList<Position>();

    public Piece(Kind kind, Type type, Position current) {
        this.kind = kind;
        this.type = type;
        setPosition(current);
    }

    public void setPosition(Position position) {
        this.current = position;
        past.add(current);
    }

    public Position getCurrent() {
        return current;
    }

    public Kind getKind() {
        return kind;
    }
}
