package checkers;

import common.AbstractPiece;
import common.Board;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class that represents a checker
 * @author Jed Wang
 */
public abstract class AbstractChecker extends AbstractPiece {

    public AbstractChecker(boolean isWhite) {
        super(isWhite);
    }

    /**
     * Returns a LinkedList with all normal moves
     * @param b the current state of the game
     * @param currentPosition the current position of the checker
     * @return a LinkedList with all normal moves
     */
    @Override
    public LinkedList<String> allLegalMoves(Board b, String currentPosition) {
        if(b instanceof CheckerBoard) {
            return allLegalMoves((CheckerBoard) b, currentPosition);
        } else throw new IllegalArgumentException("Can only use checker boards.");
    }
    
    /**
     * Returns a LinkedList with all normal moves
     * @param cb the current state of the game
     * @param currentPosition the current position of the checker
     * @return a LinkedList with all normal moves
     */
    public abstract LinkedList<String> allLegalMoves(CheckerBoard cb, String currentPosition);

    @Override
    public LinkedList<String> legalMoves(Board b, String currentPosition) {
        if(b instanceof CheckerBoard) {
            return legalMoves((CheckerBoard) b, currentPosition);
        } else throw new IllegalArgumentException("Can only use checker boards.");
    }
    
    /**
     * Returns all of the legal moves this checker could make
     * @param cb the current state of the game
     * @param currentPosition the current place of the checker
     * @return the legal moves this checker can make
     */
    public LinkedList<String> legalMoves(CheckerBoard cb, String currentPosition) {
        if(cb.hasJump(isWhite)) {
            return legalCaptures(cb, currentPosition);
        } else return allLegalMoves(cb, currentPosition);
    }

    /**
     * Returns a LinkedList with all captures this checker can make
     * @param b the current state of the game
     * @param currentPosition the current position of the checker
     * @return a LinkedList with all captures this checker can make
     */
    @Override
    public LinkedList<String> legalCaptures(Board b, String currentPosition) {
        if(b instanceof CheckerBoard) {
            return legalCaptures((CheckerBoard) b, currentPosition);
        } else throw new IllegalArgumentException("Can only use checker boards.");
    }
    
    /**
     * Returns a LinkedList with all captures this checker can make
     * @param b the current state of the game
     * @param currentPosition the current position of the checker
     * @return a LinkedList with all captures this checker can make
     */
    public abstract LinkedList<String> legalCaptures(CheckerBoard b, String currentPosition);

    @Override
    public abstract String getCharRepresentation();
}