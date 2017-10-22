package com.baykal.strategy;

import com.baykal.model.Board;
import com.baykal.model.Kind;
import com.baykal.model.Move;
import com.baykal.model.Type;

import java.util.List;
import java.util.stream.Collectors;

public class CheckOptimizer extends AbstractStrategy{
    public CheckOptimizer(Type type) {
        super(type);
    }

    @Override
    protected List<Move> evaluate(Board board, List<Move> moves) {
        List<Move> captures = moves.stream()
                .filter(move -> move.isCapture() &&
                        move.getTarget().getKind() == Kind.KING)
                .collect(Collectors.toList());

        if (captures.size() > 0) {
            return captures;
        }


        return moves;
    }
}
