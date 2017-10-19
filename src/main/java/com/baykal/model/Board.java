package com.baykal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:52
 */
public class Board {

    public static final List<String> Y  = Arrays.asList("a", "b", "c" , "d" , "e" , "f" , "g" , "h");

    private final List<Piece> pieces;
    private boolean check = false;
    private final List<MoveText> moveTexts = new ArrayList<>();

    //
    private final class MoveText {
        private final Kind kind;
        private final Position from;
        private final Position to;
        private final Boolean isKill;

        public MoveText(Kind kind, Position from, Position to, boolean isKill) {
            this.kind = kind;
            this.from = from;
            this.to = to;
            this.isKill = isKill;
        }

        @Override
        public String toString() {
            String sep = isKill? "x": "";
            return  kind.getSign()
                    + sep
                    + from.x
                    + to.toString()
                    + " ";
        }
    }

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Board() {
        this.pieces = new ArrayList<Piece>();

        //pawns
        for (String s : Y) {
           pieces.add(new Piece(Kind.PAWN, Type.WHITE, new Position(s, 2)));
           pieces.add(new Piece(Kind.PAWN, Type.BLACK, new Position(s, 7)));
        }

        // rooks
        pieces.add(new Piece(Kind.ROOK, Type.WHITE, new Position("a", 1)));
        pieces.add(new Piece(Kind.ROOK, Type.WHITE, new Position("h", 1)));
        pieces.add(new Piece(Kind.ROOK, Type.BLACK, new Position("a", 8)));
        pieces.add(new Piece(Kind.ROOK, Type.BLACK, new Position("h", 8)));

        //Knight
        pieces.add(new Piece(Kind.KNIGHT, Type.WHITE, new Position("b", 1)));
        pieces.add(new Piece(Kind.KNIGHT, Type.WHITE, new Position("g", 1)));
        pieces.add(new Piece(Kind.KNIGHT, Type.BLACK, new Position("b", 8)));
        pieces.add(new Piece(Kind.KNIGHT, Type.BLACK, new Position("g", 8)));

        //BISHOP
        pieces.add(new Piece(Kind.BISHOP, Type.WHITE, new Position("c", 1)));
        pieces.add(new Piece(Kind.BISHOP, Type.WHITE, new Position("f", 1)));
        pieces.add(new Piece(Kind.BISHOP, Type.BLACK, new Position("c", 8)));
        pieces.add(new Piece(Kind.BISHOP, Type.BLACK, new Position("f", 8)));

        // QUEEN
        pieces.add(new Piece(Kind.QUEEN, Type.WHITE, new Position("d", 1)));
        pieces.add(new Piece(Kind.QUEEN, Type.BLACK, new Position("d", 8)));

        // KING
        pieces.add(new Piece(Kind.KING, Type.WHITE, new Position("e", 1)));
        pieces.add(new Piece(Kind.KING, Type.BLACK, new Position("e", 8)));
    }


    public void apply(Move nextMove) {
        Optional<Piece> piece = findPiece(nextMove.getCurrent());
        if ( piece.isPresent()) {
            Piece p = piece.get();
            Optional<Piece> captured = findPiece(nextMove.getNext());
            if (captured.isPresent()){
                assert captured.get().getType() != p.getType();
                pieces.remove(captured.get());
            }

            p.setPosition(nextMove.getNext());
            this.moveTexts.add(
                    new MoveText(p.getKind(), nextMove.getCurrent(), nextMove.getNext(),
                            captured.isPresent()));

        }
    }

    public List<Move> possibleMoves(Player player){
        return getPieces().parallelStream()
                .filter(p -> p.getType().equals(player.getType()))
                .map(p -> p.moves(this))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 8; i > 0; i--) {
            for (String s : Y) {
                Optional<Piece> piece =  findPiece(new Position(s, i));
                if (piece.isPresent()){
                    sb.append((char)27)
                            .append(piece.get().getType()==Type.BLACK?"[31m":"[33m")
                            .append(piece.get().getKind().toString().charAt(0))
                    .append((char)27)
                            .append("[0m");


                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // https://lichess.org/import
    public String toPgnString(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0, k=1; i <moveTexts.size(); i++) {
            if (i%2==0) {
                sb.append("\n").append(k++).append(".");
            }

            sb.append(moveTexts.get(i)) ;
        }

        return sb.toString();
    }



    public Optional<Piece> findPiece(Position position){
          return pieces.stream().filter(p -> p.getCurrent().equals(position)).findFirst();
    }



    public boolean isMate() {
        return false;
    }

    public boolean isDraw() {
        return false;
    }

    public List<Piece> getPieces() {
        return pieces;
    }


}
