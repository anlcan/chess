package com.baykal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Pos;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Piece {

    private final Kind kind;
    private final Type type;
    private final List<Position> past = new ArrayList<Position>();
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

    private List<Position> positions(Board board){

        List<Position> positions = new ArrayList<>();



        switch (kind){
            case KING:
                current.advance(1).ifPresent(positions::add);
                current.advance(-1).ifPresent(positions::add);
                current.lateral(1).ifPresent(positions::add);
                current.lateral(-1).ifPresent(positions::add);

                break;
            case QUEEN:
                  positions.addAll(current.allLateral());
                  positions.addAll(current.allHorizontal());
                  break;
            case ROOK:
                  positions.addAll(current.allLateral());
                  break;
            case BISHOP:
                break;
            case KNIGHT:

                  current.advance(1)
                          .ifPresent(position -> position.lateral(2)
                                  .ifPresent(positions::add));

                  current.advance(1)
                          .ifPresent(position -> position.lateral(-2)
                                  .ifPresent(positions::add));

                  current.advance(2)
                          .ifPresent(position -> position.lateral(1)
                                  .ifPresent(positions::add));

                  current.advance(2)
                          .ifPresent(position -> position.lateral(-1)
                                  .ifPresent(positions::add));
                  //--
                  current.advance(-1)
                          .ifPresent(position -> position.lateral(2)
                                  .ifPresent(positions::add));

                  current.advance(-1)
                          .ifPresent(position -> position.lateral(-2)
                                  .ifPresent(positions::add));

                  current.advance(-2)
                          .ifPresent(position -> position.lateral(1)
                                  .ifPresent(positions::add));

                  current.advance(-2)
                          .ifPresent(position -> position.lateral(-1)
                                  .ifPresent(positions::add));

                break;
            case PAWN:
//                   no going back
                int mul = type==Type.BLACK? -1 : 1;
                current.advance(1 * mul).ifPresent(positions::add);
                if ( type==Type.BLACK && current.y == 7) {
                    current.advance(-2).ifPresent(positions::add);
                } else if (type==Type.WHITE && current.y == 2) {
                    current.advance(2).ifPresent(positions::add);
                }

                break;
            default:
                break;

        }

        return positions;
    }

    public List<Move> moves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();


        for (Position next : positions(board)) {
            evaluate(next, board).ifPresent(moves::add);
        }

        return moves;
    }

    private Optional<Move> evaluate(Position next, Board board){
        Optional<Piece> pieceOptional = board.findPiece(next);
        if (pieceOptional.isPresent()) {
            if (pieceOptional.get().getType() == type) {
                return Optional.empty();
            } else {
                //todo
                if (kind==Kind.PAWN) {
                    return Optional.empty();
                }

            }
        }

        //anybody on the way
        if (kind != Kind.KNIGHT) {
            boolean present = current.diff(next).stream()
                    .map(board::findPiece)
                    .filter(Optional::isPresent)
                    .findFirst()
                    .isPresent();
            if (present) {
                return Optional.empty();
            }
        }
        boolean isCheck = pieceOptional.isPresent() && pieceOptional.get().kind==Kind.KING;
        return Optional.of(new Move(current, next, isCheck));
    }

    @Override
    public String toString() {
        return  kind.toString() +
                 type.toString() +
                 current.toString() ;

    }
}
