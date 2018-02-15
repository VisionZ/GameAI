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
    /**
     * A HashMap that keeps track of whether a player can do a jump
     */
    private static HashMap<Boolean, Boolean> hasJump = new HashMap<>();

    public AbstractChecker(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public abstract LinkedList<String> allLegalMoves(Board b, String currentPosition);

    @Override
    public LinkedList<String> legalMoves(Board b, String currentPosition) {
        LinkedList<String> output = new LinkedList<>();
        
        return output;
    }

    @Override
    public abstract LinkedList<String> legalCaptures(Board b, String currentPosition);

    @Override
    public abstract String getCharRepresentation();
}