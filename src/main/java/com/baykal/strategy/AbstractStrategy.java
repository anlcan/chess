package com.baykal.strategy;

import com.baykal.model.Board;
import com.baykal.model.Move;
import com.baykal.model.Type;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractStrategy implements Function<Board, Move> {

    protected final Type type;

    public AbstractStrategy(Type type) {
        this.type = type;
    }


    @Override
    public Move apply(Board board) {

        List<Move> allMoves = board.possibleMoves(this.type);

        // eliminate not allowed rules
        List<Move> moves = allMoves.stream()
                .filter(board::isCheckValidMove)
                .collect(Collectors.toList());

        moves = evaluate(board, moves);

        if (moves.size()  == 0) {
            System.out.println("no move left to play?");
            return null;
        }


        return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
    }

    protected abstract List<Move> evaluate(Board board, List<Move> moves);
}
