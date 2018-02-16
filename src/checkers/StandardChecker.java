package checkers;

import common.Board;
import java.util.LinkedList;

/**
 * A class that represents a standard checker piece
 * @author Jed Wang
 */
public class StandardChecker extends AbstractChecker {
    /**
     * A constructor for a new standard checker
     * @param isWhite whether this checker is white / lighter
     */
    public StandardChecker(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public LinkedList<String> allLegalMoves(Board b, String currentPosition) {
        if(!Board.isValidSquare(currentPosition)) throw new IllegalArgumentException("Invalid square");
        if(!(b.getPiece(currentPosition).getCharRepresentation().equals("O"))) throw new IllegalArgumentException("This isn\'t a king!");
        LinkedList<String> output = new LinkedList<>();
        
        return output;
    }

    @Override
    public LinkedList<String> legalCaptures(Board b, String currentPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCharRepresentation() {
        return "O";
    }
}