package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:45
 */
public class Game {

    private Player one;
    private Player two;

    private Board board;
    private int moveCount;

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

    public Player start(){
        Player next = one.getType() == Type.WHITE? one : two;
        while(true || moveCount == 5) {
            Move nextMove = next.getMove(board);

            board.apply(nextMove);
//            if (board.isCheck()){
//
//                if (board.isMate()) {
//                    return next;
//                }
//            }  else if (board.isDraw()){
//                return null;
//            }
            moveCount++;
            next = next == one? two : one;

            System.out.print("\033[H\033[2J");
            System.out.println("\f");
            System.out.println(board.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return next;
    }

    public Board getBoard() {
        return board;
    }
}
