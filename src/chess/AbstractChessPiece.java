package chess;

import common.AbstractPiece;
import common.Board;
import java.util.LinkedList;

public abstract class AbstractChessPiece extends AbstractPiece {
    public AbstractChessPiece(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public LinkedList<String> legalMoves(Board b, String currentPosition) {
        if(b instanceof ChessBoard) {
            return legalMoves((ChessBoard) b, currentPosition);
        } else throw new IllegalArgumentException("Can only use chess boards.");
    }
    
    /**
     * Returns all of the legal moves this piece could make, taking into account check
     * @param cb the current state of the chess game
     * @param currentPosition the current place of the piece
     * @return the legal moves this piece can make
     */
    public LinkedList<String> legalMoves(ChessBoard cb, String currentPosition) {
        LinkedList<String> allLegal = allLegalMoves(cb, currentPosition);
        LinkedList<String> output = new LinkedList<>();
        AbstractPiece[][] initLayout = new AbstractPiece[cb.getBoard().length][cb.getBoard()[0].length];
        for(int i = 0; i < cb.getBoard().length; i++) {
            for(int j = 0; j < cb.getBoard()[i].length; j++) {
                initLayout[i][j] = cb.getBoard()[i][j];
            }
        }
        for(String square:allLegal) {
            cb.maybeMove(currentPosition, square);
            if(getCharRepresentation().equals("P") && 
                    (ChessBoard.getRow(square) == 0 
                    || ChessBoard.getRow(square) == 7)) 
                cb.placePiece(new Queen(isWhite), square);
            if(!cb.inCheck(isWhite)) output.add(square);
            cb.setBoard(initLayout);
            if(getCharRepresentation().equals("K")) cb.resetKingPos(isWhite);
        }
        return output;
    }

    @Override
    public LinkedList<String> allLegalMoves(Board b, String currentPosition) {
        if(b instanceof ChessBoard) {
            return allLegalMoves((ChessBoard) b, currentPosition);
        } else throw new IllegalArgumentException("Can only use chess boards.");
    }
    
    /**
     * Returns all of the legal moves this piece could make
     * @param cb the current state of the chess game
     * @param currentPosition the current place of the piece
     * @return all legal moves
     */
    public abstract LinkedList<String> allLegalMoves(ChessBoard cb, String currentPosition);

    @Override
    public LinkedList<String> legalCaptures(Board b, String currentPosition) {
        if(b instanceof ChessBoard) {
            return legalCaptures((ChessBoard) b, currentPosition);
        } else throw new IllegalArgumentException("Can only use chess boards.");
    }
    
    /**
     * Returns all of the legal captures this piece could make
     * @param cb the current state of the chess game
     * @param currentPosition the current place of the piece
     * @return all legal captures
     */
    public abstract LinkedList<String> legalCaptures(ChessBoard cb, String currentPosition);

    @Override
    public abstract String getCharRepresentation();
}