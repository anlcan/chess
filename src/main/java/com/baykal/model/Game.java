package com.baykal.model;

import java.util.Collection;
import java.util.List;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:45
 */
public class Game {

    private Player one;
    private Player two;

    private Board board;
    private int moveCount = 0;

    public Game(Player one, Player two) {
        if (one.getType() == Type.BLACK) {
            assert (Type.WHITE == two.getType());
        } else {
            assert (Type.WHITE == one.getType());
        }

        this.one = one;
        this.two = two;
        this.board = new Board();
    }

    boolean isCheck(Player player) {
        return board.getPieces().stream()
                .filter(p -> p.getType().equals(player.getType()))
                .map(piece -> piece.moves(board))
                .flatMap(Collection::stream)
                .filter(Move::isCheck)
                .findFirst()
                .isPresent();



    }

    public Player start(){
        Player nextTurn = one.getType() == Type.WHITE? one : two;
        while(moveCount < 46) {
            System.out.println(board.toString());
            Move nextMove = nextTurn.getMove(board);
            board.apply(nextMove);
            if (isCheck(nextTurn)){
                break;
            }

            moveCount++;
            nextTurn = nextTurn == one? two : one;


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf(board.toPgnString());
        return nextTurn;
    }

    public Board getBoard() {
        return board;
    }
}
