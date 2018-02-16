package checkers;

import common.Board;
import java.util.LinkedList;

/**
 * A class that represents a king
 * @author Jed Wang
 */
public class KingChecker extends AbstractChecker {
    /**
     * A constructor for a new king
     * @param isWhite whether the checker is white / lighter
     */
    public KingChecker(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public LinkedList<String> allLegalMoves(Board b, String currentPosition) {
        if(!Board.isValidSquare(currentPosition)) throw new IllegalArgumentException("Invalid square");
        if(!(b.getPiece(currentPosition).getCharRepresentation().equals("K"))) throw new IllegalArgumentException("This isn\'t a king!");
        LinkedList<String> output = new LinkedList<>();
        
        return output;
    }

    @Override
    public LinkedList<String> legalCaptures(Board b, String currentPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCharRepresentation() {
        return "K";
    }
}