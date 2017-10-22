package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:45
 */
public class Game {

    public Player nextTurn;
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
        this.nextTurn = one.getType() == Type.WHITE? one : two;
    }

    private Player opposite(Player p1) {
        return p1 == one? two : one;
    }


    public Player start() {

        while(board.moveTexts.size() < 180) {
            boolean isMate = step();
            nextTurn = opposite(nextTurn);

            if (isMate){
                break;
            }
        }

        return nextTurn;
    }

    public boolean step() {
        Move nextMove = nextTurn.getMove(board);
        if ( null == nextMove) {
            // mate
            return true;
        }

        board.apply(nextMove);
        return false;
    }

    public Board getBoard() {
        return board;
    }
}
