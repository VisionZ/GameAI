package common;

import java.util.LinkedList;

/**
 * A class to represent any chess piece
 * @author Jed Wang
 */
public abstract class AbstractPiece {
    /**
     * Whether or not the piece is white
     */
    protected final boolean isWhite;
    
    /**
     * Creates a new AbstractPiece
     * @param isWhite whether or not the piece is white
     */
    public AbstractPiece(boolean isWhite) {
        this.isWhite = isWhite;
    }
    
    /**
     * Determines whether a move is legal
     * @param b the current state of the game
     * @param fromWhere the current place of the piece
     * @param toWhere to where the piece would be moved
     * @return whether the move would be legal
     */
    public boolean isLegalMove(Board b, String fromWhere, String toWhere) {
        return legalMoves(b, fromWhere).contains(toWhere);
    }
    
    /**
     * Returns all of the legal moves this piece could make
     * @param b the current state of the game
     * @param currentPosition the current place of the piece
     * @return all legal moves
     */
    public abstract LinkedList<String> allLegalMoves(Board b, String currentPosition);
    
    /**
      * Determines whether a move is legal <br>
      * However, this method does not check for checks
      * @param b
      * @param fromWhere
      * @param toWhere
      * @return 
      */
    public boolean isAllLegalMove(Board b, String fromWhere, String toWhere) {
        return allLegalMoves(b, fromWhere).contains(toWhere);
    }
    
    /**
     * Returns all of the legal moves this piece could make
     * @param b the current state of the game
     * @param currentPosition the current place of the piece
     * @return the legal moves this piece can make
     */
    public abstract LinkedList<String> legalMoves(Board b, String currentPosition);
    
    /**
     * Returns all of the legal captures this piece could make
     * @param b the current state of the  game
     * @param currentPosition the current place of the piece
     * @return all legal captures
     */
    public abstract LinkedList<String> legalCaptures(Board b, String currentPosition);
    
    /**
     * Returns the character that represents this piece
     * @return the character that represents this piece
     */
    public abstract String getCharRepresentation();
}