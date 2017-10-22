package com.baykal.strategy;

import com.baykal.model.Board;
import com.baykal.model.Move;
import com.baykal.model.Type;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CaptureOptimize implements Function<Board,Move>{

    private final Type type;

    public CaptureOptimize(Type type) {
        this.type = type;
    }


    @Override
    public Move apply(Board board) {

        List<Move> allMoves = board.possibleMoves(this.type);

        // eliminate not allowed rules
        List<Move>moves = allMoves.stream()
                .filter(board::isCheckValidMove)
                .collect(Collectors.toList());

        List<Move> captures = moves.stream()
                .filter(move -> move.isCapture() &&
                    move.getOrigin().getKind().value > move.getTarget().getKind().value)
                .collect(Collectors.toList());

        if (captures.size() > 0)
            moves = captures;

        return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
    }


}
