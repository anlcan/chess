package com.baykal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Piece {

    private final Kind kind;
    private final Type type;
    private final List<Position> past = new ArrayList<>();
    private Position current;

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

    public Type getType() {
        return type;
    }


    public List<Move> moves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        List<Position> positions = kind == Kind.PAWN ? pawnPositions(board) : kind.positions.apply(current);


        for (Position next : positions) {
            evaluate(next, board).ifPresent(moves::add);
        }

        return moves;
    }

    private Optional<Move> evaluate(Position next, Board board) {
        Optional<Piece> targetPiece = board.findPiece(next);
        if (targetPiece.isPresent()) {
            if (targetPiece.get().getType() == type) {
                return Optional.empty();
            } else {

                //todo
                if (kind == Kind.PAWN && this.current._x == targetPiece.get().current._x) {
                    return Optional.empty();
                }

            }
        }

        //anybody on the way
        if (kind != Kind.KNIGHT) {
            boolean present = current.diff(next).stream()
                    .map(board::findPiece)
                    .anyMatch(Optional::isPresent);
            if (present) {
                return Optional.empty();
            }
        }
        return Optional.of(new Move(this, next, targetPiece));
    }

    @Override
    public String toString() {
        return kind.toString() +
                type.toString() +
                current.toString();

    }


    public List<Position> pawnPositions(Board board) {
        List<Position> positions = new ArrayList<>();

        //
        int mul = type == Type.BLACK ? -1 : 1;
        current.advance(mul).ifPresent(positions::add);
        if (type == Type.BLACK && current.y == 7) {
            current.advance(-2).ifPresent(positions::add);
        } else if (type == Type.WHITE && current.y == 2) {
            current.advance(2).ifPresent(positions::add);
        }

        for(int i : Position.PM) {
            current.diagonal(i, mul)
                    .ifPresent(position -> board.findPiece(position)
                            .filter(piece -> piece.type != this.type)
                            .ifPresent(piece -> positions.add(position)));
        }

        return positions;
    }

    public boolean canPromote(){
        return ((getKind() == Kind.PAWN && getType() ==Type.WHITE && current.y() == 8 )
                || (getKind() == Kind.PAWN && getType() ==Type.BLACK && current.y() == 1 ));
    }

    public Piece copy() {
        return new Piece(this.kind, this.type, new Position(current.x, current.y));
    }
}
