package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:56
 */
public class Position {
    int x;
    String y;

    public Position(String y, int x) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y != null ? y.equals(position.y) : position.y == null;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}
