package com.baykal.model;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:43
 */
public class Player {

    private final String name;
    private final Type type;

    public Player(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    private boolean canUncheck(Board board, Move move) {
        return !new Board(board.getPieces()).apply(move).nextMoveMate(type.opposite());
    }

    public Move getMove(Board board) {
        // GAME LOGIC GOES HERE

        List<Move> moves = board.possibleMoves(this.type);

        if (board.isCheck()) {
            //select a move that will unCheck!
            moves = moves.stream()
                    .peek(move -> System.out.println(move))
                    .filter(move -> canUncheck(board, move))
                    .collect(Collectors.toList());

        }
        if (moves.size()  == 0) {
            return null;
        }

        return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
    }
}
