package com.baykal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:56
 */
public enum Kind {

    KING("K", new Kind.KingPositions()),
    QUEEN("Q", new QueenPosition()),
    ROOK("R", new Kind.RookPositions()),
    BISHOP("B", new Kind.BishopPositions()),
    KNIGHT("N", new Kind.KnightPosition()),
    PAWN(" ", new  Kind.EmptyPositions());  //todo black_pawn≤ white_pawn?

    public final Function<Position, List<Position>> positions;

    public static final Optional<Kind> fromPgnSign(String sign){
        return Stream.of(Kind.values()).filter(kind -> kind.pgnsign.equals(sign)).findFirst();
    }

    private final String pgnsign;


    Kind(String sign, Function<Position, List<Position>> positionFunction) {
        pgnsign = sign;
        positions = positionFunction;
    }

    public String getSign() {
        return pgnsign;
    }

    public static class EmptyPositions implements Function<Position, List<Position>> {

        @Override
        public List<Position> apply(Position current) {
            return new ArrayList<>();
        }
    }

    public static class BishopPositions implements Function<Position, List<Position>> {

        @Override
        public List<Position> apply(Position current) {
            return current.allDiagonal();
        }
    }

    public static class RookPositions implements Function<Position, List<Position>> {

        @Override
        public List<Position> apply(Position current) {
            List<Position> positions = new ArrayList<>();
            positions.addAll(current.allHorizontal());
            positions.addAll(current.allLateral());
            return positions;
        }
    }

    public static class KingPositions implements Function<Position, List<Position>> {

        @Override
        public List<Position> apply(Position current) {
            List<Position> positions = new ArrayList<>();

            for(int i : Position.PM) {
                current.advance(i).ifPresent(positions::add);
                current.lateral(i).ifPresent(positions::add);
                for(int j : Position.PM) {
                    current.diagonal(i,j).ifPresent(positions::add);
                }
            }

            return positions;
        }
    }



    public static class QueenPosition implements Function<Position, List<Position>> {
        @Override
        public List<Position> apply(Position current) {

            List<Position> positions = new ArrayList<>();
            positions.addAll(current.allLateral());
            positions.addAll(current.allHorizontal());
            positions.addAll(current.allDiagonal());
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
