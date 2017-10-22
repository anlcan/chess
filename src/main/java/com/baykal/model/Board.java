package com.baykal.model;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:52
 */
public class Board {

    public static final List<String> Y = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");

    private final List<Piece> pieces;
    private boolean check = false;

    public boolean isCheck() {
        return check;
    }
    public final List<Move> moveTexts = new ArrayList<>();



    public Board(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Board() {
        this.pieces = new ArrayList<>();

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


    public Board apply(Move nextMove) {
        assert nextMove.getNext() != null;
        Optional<Piece> pieceO = findPiece(nextMove.getCurrent());

        assert pieceO.isPresent();
        Piece piece = pieceO.get();

        if (nextMove.isCapture()) {
            Piece captured = nextMove.getTarget();
            assert captured.getType() != piece.getType();

            Optional<Piece> targetO = findPiece(captured.getCurrent());
            assert targetO.isPresent();
            pieces.remove(targetO.get());
        }
        piece.setPosition(nextMove.getNext());

        //check promote
        if(piece.canPromote()){
            Piece autoPromote = new Piece(Kind.QUEEN, piece.getType(), piece.getCurrent());
            pieces.remove(piece);
            pieces.add(autoPromote);
            nextMove.setPromote(Kind.QUEEN);
        }

        // if this player can capture KING on my next move, this is check
        this.check = canCaptureKing(piece.getType());
        nextMove.setCheck(check);
        this.moveTexts.add(nextMove);

        return this;
    }

    public boolean canCaptureKing(Type type) {
        return possibleMoves(type).stream()
                .anyMatch(move -> move.isCapture() && move.getTarget().getKind() == Kind.KING);
    }

    // does this move save me from check?
    public boolean isCheckValidMove(Move move) {
        Board testBoard = this.clone();
        testBoard.apply(move);

        return !testBoard.canCaptureKing(move.getOrigin().getType().opposite());
    }

    public List<Move> possibleMoves(Type type) {
        return pieces.parallelStream()
                .filter(p -> p.getType().equals(type))
                .map(p -> p.moves(this))
                .flatMap(Collection::stream)
                .collect(toList());
    }



    public Optional<Piece> findPiece(Position position) {
        return pieces.stream().filter(p -> p.getCurrent().equals(position)).findFirst();
    }

    public List<Piece> getPieces() {
        return pieces.stream().map(Piece::copy).collect(toList());
    }

    public Board clone() {
        return new Board(getPieces());
    }

    public List<Move> getMoves() {
        return moveTexts;
    }

    public boolean equals(Board board){

        if (getPieces().size() != board.getPieces().size())
            return false;

        for (Piece p : board.pieces){
            if (findPiece(p.getCurrent()).isPresent()) {
                Piece p2 = findPiece(p.getCurrent()).get();
                if (p2.getType() == p.getType() && p2.getKind() == p.getKind())
                    continue;
            }
            return false;
        }
        return true;
    }

    public String toRichString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 8; i > 0; i--) {
            for (String s : Y) {
                Optional<Piece> piece = findPiece(new Position(s, i));
                if (piece.isPresent()) {
                    sb.append((char) 27)
                            .append(piece.get().getType() == Type.BLACK ? "[31m" : "[33m")
                            .append(piece.get().getKind().toString().charAt(0))
                            .append((char) 27)
                            .append("[0m");


                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Board{" +
                "pieces=" + pieces +
                ", check=" + check +
                ", moveTexts=" + moveTexts +
                '}';
    }

    // https://lichess.org/import
    public String toPgnString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0, k = 1; i < moveTexts.size(); i++) {
            if (i % 2 == 0) {
                sb.append("\n").append(k++).append(".");
            }

            sb.append(moveTexts.get(i));
        }

        return sb.toString();
    }

}
