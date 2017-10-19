package com.baykal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:56
 */
public enum Kind {

    KING("K", new Kind.KingPositions()),
    QUEEN("Q", new Kind.QueenPosition()),
    ROOK("R", new Kind.EmptyPositions()),
    BISHOP("B", new Kind.EmptyPositions()),
    KNIGHT("N", new Kind.KnightPosition()),
    PAWN(" ", new  Kind.EmptyPositions());

    public  Function<Position, List<Position>> positions;

    private final String PGN_SIGN;

    public void setPositions(Function<Position, List<Position>> positions) {
        this.positions = positions;
    }

    Kind(String sign, Function<Position, List<Position>> positionFunction) {
        PGN_SIGN = sign;
        positions = positionFunction;
    }

    public String getSign() {
        return PGN_SIGN;
    }

    public static class EmptyPositions implements Function<Position, List<Position>> {

        @Override
        public List<Position> apply(Position current) {
            return new ArrayList<>();
        }
    }


    public static class KingPositions implements Function<Position, List<Position>> {

        @Override
        public List<Position> apply(Position current) {
            List<Position> positions = new ArrayList<>();

            current.advance(1).ifPresent(positions::add);
            current.advance(-1).ifPresent(positions::add);
            current.lateral(1).ifPresent(positions::add);
            current.lateral(-1).ifPresent(positions::add);

            return positions;
        }
    }



    public static class QueenPosition implements Function<Position, List<Position>> {
        @Override
        public List<Position> apply(Position current) {

            List<Position> positions = new ArrayList<>();
            positions.addAll(current.allLateral());
            positions.addAll(current.allHorizontal());
            return positions;
        }
    }

    public static class KnightPosition implements Function<Position, List<Position>> {
        @Override
        public List<Position> apply(Position p) {
        List<Position> positions = new ArrayList<>();
        p.advance(1)
                .ifPresent(position -> position.lateral(2)
                .ifPresent(positions::add));

        p.advance(1)
                .ifPresent(position -> position.lateral(-2)
                .ifPresent(positions::add));

        p.advance(2)
                .ifPresent(position -> position.lateral(1)
                .ifPresent(positions::add));

        p.advance(2)
                .ifPresent(position -> position.lateral(-1)
                .ifPresent(positions::add));
        //--
        p.advance(-1)
                .ifPresent(position -> position.lateral(2)
                .ifPresent(positions::add));

        p.advance(-1)
                .ifPresent(position -> position.lateral(-2)
                .ifPresent(positions::add));

        p.advance(-2)
                .ifPresent(position -> position.lateral(1)
                .ifPresent(positions::add));

        p.advance(-2)
                .ifPresent(position -> position.lateral(-1)
                .ifPresent(positions::add));
        return positions;
    }

    }

}
