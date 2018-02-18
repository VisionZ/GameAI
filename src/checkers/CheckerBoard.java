package checkers;

import common.AbstractPiece;
import common.Board;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class that represents a checker board
 * @author Jed Wang
 */
public class CheckerBoard extends Board {
    /**
     * The number of moves that have been made without capturing.
     */
    private int movesWOCapture = 0;
    
    /**
     * Instantiates a CheckerBoard.
     */
    public CheckerBoard() {
        super(8, 8);
    }
    
    /**
     * A private constructor that copies an existing CheckerBoard
     * @param cb the CheckerBoard to copy
     */
    private CheckerBoard(CheckerBoard cb) {
        super(8, 8);
        playerIsWhite = cb.playerIsWhite;
        for(int i = 0;i<8;i++) {
            // copy from 0-8 from cb.board[i] to board[i]
            System.arraycopy(cb.board[i], 0, board[i], 0, 8);
        }
        allLegalMoves = new HashMap<>(cb.allLegalMoves);
        recalculateMoves();
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
        board[6][3] = new StandardChecker(false);
        board[7][2] = new StandardChecker(false);
        
        board[1][4] = new StandardChecker(true);
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
    public void movePiece(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY) {
        super.movePiece(fromWhereX, fromWhereY, toWhereX, toWhereY);
        if(Math.abs(fromWhereX - toWhereX) == 2) {
            movesWOCapture = 0;
            board[(fromWhereX + toWhereX)/2][(toWhereY + fromWhereY)/2] = null;
        }
        else movesWOCapture++;
    }

    @Override
    public void pieceFromTo(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY) {
        AbstractPiece toMove = board[fromWhereX][fromWhereY];
        String fromWhere = toSquare(fromWhereX, fromWhereY), toWhere = toSquare(toWhereX, toWhereY);
        if(toMove.isLegalMove(this, fromWhere, toWhere)) {
            if((toWhereY == 0) || (toWhereY == 7)) {
                toMove = new KingChecker(toMove.isWhite);
            }
            board[toWhereX][toWhereY] = toMove;
            board[fromWhereX][fromWhereY] = null;
        }
    }
    
    @Override
    public void recalculateMoves() {
        allLegalMoves = new HashMap<>();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == null) continue;
                if(board[i][j].isWhite == playerIsWhite) {
                    String current = Board.toSquare(i, j);
                    LinkedList<String> moves = board[i][j].legalMoves(this, current);
                    allLegalMoves.put(current, moves);
                }
            }
        }
    }

    @Override
    public Board deepCopy() {
        return new CheckerBoard(this);
    }

    @Override
    public boolean isFinished() {
        if(isDraw()) return true;
        boolean hasWhite = false, hasBlack = false;
        for(int i = 0;i<board.length;i++) {
            for(int j = 0;j<board[i].length;j++) {
                if(board[i][j] != null) {
                    if(board[i][j].isWhite)
                        hasWhite = true;
                    else 
                        hasBlack = true;
                }
                if(hasWhite && hasBlack) return false;
            }
        }
        return true;
    }
    
    /**
     * Determines whether the current game has ended in a draw
     * @return whether the current game has ended in a draw
     */
    public boolean isDraw() {
        return movesWOCapture >= 40;
    }

    @Override
    public int getResult() {
        if(isDraw()) return 0;
        boolean hasWhite = false, hasBlack = false;
        for(int i = 0;i<board.length;i++) {
            for(int j = 0;j<board[i].length;j++) {
                if(board[i][j] != null) {
                    if(board[i][j].isWhite)
                        hasWhite = true;
                    else 
                        hasBlack = true;
                }
                if(hasWhite && hasBlack) return 0;
            }
        }
        if(hasWhite) return 1;
        else if(hasBlack) return -1;
        else return 0;
    }
    
    /**
     * Determines whether one side can jump.
     * @param isWhite which side to check
     * @return whether one side can jump
     */
    public boolean hasJump(boolean isWhite) {
        for(int i = 0;i<board.length;i++) {
            for(int j = 0;j<board[i].length;j++) {
                if(board[i][j] != null) {
                    AbstractPiece temp = board[i][j];
                    if(temp.isWhite == isWhite) {
                        if(!temp.legalCaptures(this, toSquare(i, j)).isEmpty()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}