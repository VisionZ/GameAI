package chess;

import common.Board;
import java.util.HashMap;

public class ChessBoard extends Board {
    
    /**
     * A Map of the king's position
     */
    private HashMap<Boolean, String> kingPos;
    
    /**
     * The square a pawn is promoting from<br>
     * Controls promotion
     */
    private String promotingFrom = null;
    
    /**
     * Counts how many times a position repeats<br>
     * Controls threefold repetition
     */
    private HashMap<String, Integer> positions;
    
    /**
     * Default constructor.
     */
    public ChessBoard() {
        super(8, 8);
        kingPos = new HashMap<>();
        allLegalMoves = new HashMap<>();
        positions = new HashMap<>();
        recalculateMoves();
    }

    @Override
    public Board deepCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void boardSetup() {
        for(int i = 0;i<8;i++) {
            board[i][1] = new Pawn(false);
            board[i][6] = new Pawn(true);
        }
        
        board[0][0] = new Rook(false);
        board[1][0] = new Knight(false);
        board[2][0] = new Bishop(false);
        board[3][0] = new Queen(false);
        board[4][0] = new King(false);
        board[5][0] = new Bishop(false);
        board[6][0] = new Knight(false);
        board[7][0] = new Rook(false);
        
        board[0][7] = new Rook(true);
        board[1][7] = new Knight(true);
        board[2][7] = new Bishop(true);
        board[3][7] = new Queen(true);
        board[4][7] = new King(true);
        board[5][7] = new Bishop(true);
        board[6][7] = new Knight(true);
        board[7][7] = new Rook(true);
        
        kingPos.put(true, "e1");
        kingPos.put(false, "e8");
    }

    @Override
    public void pieceFromTo(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY) {
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