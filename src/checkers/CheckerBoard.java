package checkers;

import common.Board;

/**
 * A class that represents a checker board
 * @author Jed Wang
 */
public class CheckerBoard extends Board {
    /**
     * Instantiates a CheckerBoard.
     */
    public CheckerBoard() {
        super(8, 8);
    }

    @Override
    protected void boardSetup() {
        board[1][0] = new StandardChecker(false);
        board[3][0] = new StandardChecker(false);
        board[5][0] = new StandardChecker(false);
        board[7][0] = new StandardChecker(false);
        board[0][1] = new StandardChecker(false);
        board[2][1] = new StandardChecker(false);
        board[4][1] = new StandardChecker(false);
        board[6][1] = new StandardChecker(false);
        board[1][2] = new StandardChecker(false);
        board[3][2] = new StandardChecker(false);
        board[5][2] = new StandardChecker(false);
        board[7][2] = new StandardChecker(false);
        
        board[0][5] = new StandardChecker(true);
        board[2][5] = new StandardChecker(true);
        board[4][5] = new StandardChecker(true);
        board[6][5] = new StandardChecker(true);
        board[1][6] = new StandardChecker(true);
        board[3][6] = new StandardChecker(true);
        board[5][6] = new StandardChecker(true);
        board[7][6] = new StandardChecker(true);
        board[0][7] = new StandardChecker(true);
        board[2][7] = new StandardChecker(true);
        board[4][7] = new StandardChecker(true);
        board[6][7] = new StandardChecker(true);
    }

    @Override
    public void pieceFromTo(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Board deepCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}