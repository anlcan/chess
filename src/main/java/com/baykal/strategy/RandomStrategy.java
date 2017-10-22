package com.baykal.strategy;

import com.baykal.model.Board;
import com.baykal.model.Move;
import com.baykal.model.Type;

import java.util.List;

public class RandomStrategy extends AbstractStrategy {


    public RandomStrategy(Type type) {
        super(type);
    }

    @Override
    protected List<Move> evaluate(Board board, List<Move> moves) {

        return moves;
    }
}
