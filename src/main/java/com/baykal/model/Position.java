package com.baykal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:56
 */
public class Position {
    final String x;
    final char _x;
    final int y;
    String tag;

    public Position(String x, int y) {
        this.x = x;
        this.y = y;
        this._x = x.charAt(0);
    }

    public String x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Position setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Optional<Position> advance(int y) {
        if ( this.y + y > 8 || this.y + y < 1)
            return Optional.empty();
        else
            return Optional.of(new Position( this.x(), this.y() + y).setTag(tag));
    }

    public Optional<Position> lateral(int x) {
        String s =  String.valueOf((char) (_x + x));
        if (Board.Y.contains(s))
            return Optional.of(new Position(s, this.y()).setTag(tag));
        else
            return Optional.empty();
    }

    public List<Position> allLateral(){
        List<Position> positions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            if (i == y) {
                continue;
            }
            Optional<Position> p = advance(i-y);
            if ( p.isPresent()){
                positions.add(p.get());
            }
        }

        return positions;
    }

    public List<Position> allHorizontal(){
        List<Position> positions = new ArrayList<>();

        for ( String s : Board.Y) {

            if (s.equals(x)) {
                continue;
            }

            Optional<Position> p = lateral(s.charAt(0)- _x);
            if ( p.isPresent()){
                positions.add(p.get());
            }
        }

        return positions;
    }

    public List<Position> diff(Position target) {
        List<Position> pos = new ArrayList<>();

        if (Objects.equals(target.x(), this.x)) {
            for(int i = Math.min(target.y(), y) + 1;
                i <Math.max(target.y(), y);
                i++) {
                    pos.add(new Position(this.x, i));
            }
        } else if (target.y() == this.y) {
            for(int i = Math.min(target.x().charAt(0), _x) + 1;
                i <Math.max(target.x().charAt(0), _x);
                i++) {
                pos.add(new Position(String.valueOf((char)i), this.y));

            }
        } else if (Math.abs(target.y() - y) ==
                Math.abs(target.x().charAt(0) - _x)){

        }

        return pos;
    }

    @Override
    public String toString() {
        return x+y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (y != position.y) return false;
        return x != null ? x.equals(position.x) : position.x == null;

    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + y;
        return result;
    }
}
