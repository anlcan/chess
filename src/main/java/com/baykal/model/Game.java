package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:45
 */
public class Game {

    private Player one;
    private Player two;

    private Board board;

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


    public Player start() {
        Player nextTurn = one.getType() == Type.WHITE? one : two;
        while(board.moveTexts.size() < 120) {
            Move nextMove = nextTurn.getMove(board);
            if ( null == nextMove) {
                return nextTurn==one?two : one;
            }
            board.apply(nextMove);
            nextTurn = nextTurn == one? two : one;
        }

        return nextTurn;
    }

    public Board getBoard() {
        return board;
    }
}
