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



    public Move getMove(Board board) {
        // GAME LOGIC GOES HERE

        List<Move> allMoves = board.possibleMoves(this.type);

        // eliminate not allowed rules
        List<Move>moves = allMoves.stream()
                .filter(board::isCheckValidMove)
                .collect(Collectors.toList());

        if ( board.isCheck()) {
            System.out.println(board.getMoves().size() /2+ ". "
                    + allMoves.size() + " -> " + moves.size());
        }

        if (moves.size()  == 0) {
            System.out.println("no move left to play?");
            return null;
        }


        Move candidate = moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
        return candidate;
    }
}
