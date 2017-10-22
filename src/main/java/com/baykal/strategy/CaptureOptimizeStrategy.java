package com.baykal.strategy;

import com.baykal.model.Board;
import com.baykal.model.Move;
import com.baykal.model.Type;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CaptureOptimizeStrategy extends AbstractStrategy {

    public CaptureOptimizeStrategy(Type type) {
        super(type);
    }

    @Override
    protected List<Move> evaluate(Board board, List<Move> moves) {

        // exchange
        List<Move> exchangelessCapture = moves.stream()
                .filter(Move::isCapture)
                .filter(move -> !exchange(move, board))
                .sorted(Move::compareCaptureValue)
                .limit(1)
                .collect(Collectors.toList());

        if (exchangelessCapture.size() > 0) {
            System.out.println("exhangeless found");
            return exchangelessCapture;

        }

        List<Move> captures = moves.stream()
                .filter(move -> move.isCapture() &&
                        move.getOrigin().getKind().value <= move.getTarget().getKind().value)
                .sorted(Comparator.comparingInt(m -> m.getTarget().getKind().value))
                .limit(1)
                .collect(Collectors.toList());

        if (captures.size() > 0) {
            System.out.println("exhange found");
            return captures;
        } else {
            return moves;
        }
    }

    private boolean exchange(Move m, Board b){
        Board moveApplied = b.clone().apply(m);
        List<Move> moves = moveApplied.possibleMoves(type.opposite()).stream()
                .filter(move -> move.isCapture()
                        && move.getTarget().getCurrent() == m.getNext())
                .collect(Collectors.toList());

        return moves.size()>0;
    }
}
