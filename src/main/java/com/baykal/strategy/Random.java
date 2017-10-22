package com.baykal.strategy;

import com.baykal.model.Board;
import com.baykal.model.Move;
import com.baykal.model.Type;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Random implements Function<Board, Move> {

    private final Type type;

    public Random(Type type) {
        this.type = type;
    }


    @Override
    public Move apply(Board board) {
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
