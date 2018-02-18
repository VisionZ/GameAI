package common;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class that represents any game board.
 * @author Jed Wang
 */
public abstract class Board {
    /**
     * The board
     */
    protected AbstractPiece[][] board;
    
    /**
     * How many rows and columns this board has
     */
    protected int rows, columns;
    
    /**
     * Whether the current player is white
     */
    protected boolean playerIsWhite;
    
    /**
     * A Map of all of the legal moves possible
     */
    protected HashMap<String, LinkedList<String>> allLegalMoves;
    
    /**
     * A private instantiator for basic stuff.
     */
    private Board() {
        playerIsWhite = true;
        allLegalMoves = new HashMap<>();
    }
    
    /**
     * Instantiates a Board with a set number of rows and columns
     * @param rows the number of rows in a board
     * @param columns the number of columns in a board
     */
    public Board(int rows, int columns) {
        this();
        this.rows = rows;
        this.columns = columns;
        board = new AbstractPiece[columns][rows];
        boardSetup();
        recalculateMoves();
    }
    
    /**
     * Instantiates a Board from an existing Board
     * @param b the Board to copy
     * @deprecated can use deepCopy instead
     */
    @Deprecated
    public Board(Board b) {
        this(b.rows, b.columns);
        for(int i = 0; i < b.rows; i++) {
            System.arraycopy(b.board[i], 0, board[i], 0, b.board[i].length);
        }
    }
    
    /**
     * Creates a deep copy of the current instance of Board
     * @return a deep copy of this
     */
    public abstract Board deepCopy();
    
    /**
     * Places all needed pieces onto the starting position.
     */
    protected abstract void boardSetup();
    
    /**
     * Determines which piece occupies a square
     * @param square a square
     * @return the piece on that square, and if none, null
     */
    public AbstractPiece getPiece(String square) {
        if(isValidSquare(square)) {
            return board[getColumn(square)][getRow(square)];
        } else throw new IllegalArgumentException("Invalid square");
    }
    
    /**
     * Determines which piece occupies a space represented by ABSOLUTE coordinates<br>
     * i.e. (0, 0) represents the top left corner
     * @param col the ABSOLUTE column
     * @param row the ABSOLUTE row
     * @return the piece on that square, and if none, null
     */
    public AbstractPiece getPiece(int col, int row) {
        if(isValidSquare(col, row)) {
            return board[col][row];
        } else throw new IllegalArgumentException("Invalid square");
    }
    
    /**
     * Determines whether a square is empty
     * @param square a square
     * @return whether that square is empty
     */
    public boolean isEmptySquare(String square) {
        return getPiece(square) == null;
    }
    
    /**
     * Determines whether a space represented by ABSOLUTE coordinates is empty
     * @param col the ABSOLUTE column
     * @param row the ABSOLUTE row
     * @return whether that square is empty
     */
    public boolean isEmptySquare(int col, int row) {
        return getPiece(col, row) == null;
    }
    
    /**
     * Determines the validity of the square
     * @param s a square
     * @return whether the square is valid
     */
    public static boolean isValidSquare(String s) {
        if(s == null) return false;
        if(s.length() == 2) {
            int col = s.charAt(0)-'a', 
                    row = 8 - Integer.parseInt(s.charAt(1) + "");
            return Character.isLowerCase(s.charAt(0)) && 
                    Character.isDigit(s.charAt(1)) && isValidSquare(col, row);
        } else return false;
    }
    
    /**
     * Determines the validity of the square
     * @param col the ABSOLUTE column
     * @param row the ABSOLUTE row
     * @return whether the square is valid
     */
    public static boolean isValidSquare(int col, int row) {
        return col >= 0 && col <= 7 && row >= 0 && row <= 7;
    }
    
    /**
     * Determines which column a square is referring to<br>
     * <br>
     * The columns are ordered as such:<br>
     * <code>|_|_|_|_|...|_|<br>
     * |0 1 2 3 ... n<br>
     * |a b c d ...(char)(a+n)</code><br>
     * Where n represents the number of columns in the board.
     * @param s a square
     * @return which column the String is referring to
     */
    public static int getColumn(String s) {
        if(isValidSquare(s)) {
            return s.charAt(0)-'a';
        } else throw new IllegalArgumentException("Invalid square");
    }
    
    /**
     * Determines which row a square is referring to<br>
     * <br>
     * The rows are ordered as such:<br>
     * <code>_____<br>
     * 0  |_<br>
     * 1  |_ <br>
     * 2  |_<br>
     * .   .<br>
     * .   .<br>
     * .   .<br>
     * m  |_<br></code>
     * ___P1<br>
     * Where m is the number of rows in the board and PI is the first player.
     * @param s the square
     * @return the column / file
     */
    public static int getRow(String s) {
        if(isValidSquare(s)) {
            return 8 - Integer.parseInt(s.substring(1));
        } else throw new IllegalArgumentException("Invalid square");
    }
    
    /**
     * Determines where a square is after a shift (a.k.a. moving it left and right, up and down).
     * @param col current column
     * @param row current row
     * @param colShift how much to shift the columns
     * @param rowShift how much to shift the rows
     * @return the shifted square
     */
    public static String shiftSquare(int col, int row, int colShift, int rowShift) {
        if(isValidSquare(col, row)) {
            int shiftedCol = col + colShift, shiftedRow = row + rowShift;
            if(isValidSquare(shiftedCol, shiftedRow)) {
                return toSquare(shiftedCol, shiftedRow);
            } else throw new IllegalArgumentException("Invalid shift");
        } else throw new IllegalArgumentException("Invalid square");
    }
    
    /**
     * Determines where a square is after a shift (a.k.a. moving it left and right, up and down)
     * @param s the current square
     * @param colShift how much to shift the columns
     * @param rowShift how much to shift the rows
     * @return the shifted square
     */
    public static String shiftSquare(String s, int colShift, int rowShift) {
        if(isValidSquare(s)) {
            int col = getColumn(s), row = getRow(s);
            int shiftedCol = col + colShift, shiftedRow = row + rowShift;
            if(isValidSquare(shiftedCol, shiftedRow)) {
                return toSquare(shiftedCol, shiftedRow);
            } else throw new IllegalArgumentException("Invalid shift");
        } else throw new IllegalArgumentException("Invalid square");
    }
    
    /**
     * Checks if a shift is valid
     * @param col current column
     * @param row current row
     * @param colShift how much to shift the columns
     * @param rowShift how much to shift the rows
     * @return whether the shift is valid
     */
    public static boolean isValidShift(int col, int row, int colShift, int rowShift) {
        if(isValidSquare(col, row)) {
            int shiftedCol = col + colShift, shiftedRow = row + rowShift;
            return isValidSquare(shiftedCol, shiftedRow);
        } else return false;
    }
    
    /**
     * Checks if this shift is valid
     * @param s current square
     * @param colShift how much to shift the columns
     * @param rowShift how much to shift the rows
     * @return whether the shift is valid
     */
    public static boolean isValidShift(String s, int colShift, int rowShift) {
        return isValidShift(
                Board.getColumn(s), Board.getRow(s), 
                colShift, rowShift
        );
    }
    
    /**
     * Determines the square represented by the row and column
     * @param column the ABSOLUTE column
     * @param row the ABSOLUTE row
     * @return the square that is represented by the row and column
     */
    public static String toSquare(int column, int row) {
        return "" + (char)('a' + column) + (8 - row);
    }

    /**
     * Returns the current state of the game
     * @return the current state of the game
     */
    public AbstractPiece[][] getBoard() {
        return board;
    }
    
    /**
     * Moves a piece from fromWhere to toWhere
     * @param fromWhere from where a piece is moved
     * @param toWhere where to move a piece
     */
    public void movePiece(String fromWhere, String toWhere) {
        movePiece(
                Board.getColumn(fromWhere), 
                Board.getRow(fromWhere), 
                Board.getColumn(toWhere), 
                Board.getRow(toWhere)
        );
    }
    
    /**
     * Moves a piece from fromWhere(X, Y) to toWhere(X, Y)
     * @param fromWhereX from where a piece is moved
     * @param fromWhereY from where a piece is moved
     * @param toWhereX where to move a piece
     * @param toWhereY where to move a piece
     */
    public void movePiece(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY) {
        pieceFromTo(fromWhereX, fromWhereY, toWhereX, toWhereY);
        // After move stuff
        playerIsWhite = !playerIsWhite;
    }
    
    /**
     * Moves a piece from fromWhere(X, Y) to toWhere(X, Y)
     * @param fromWhereX from where a piece is moved
     * @param fromWhereY from where a piece is moved
     * @param toWhereX where to move a piece
     * @param toWhereY where to move a piece
     */
    public abstract void pieceFromTo(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY);

    /**
     * Used to check whether this move is legal
     * @param fromWhere from where to move a piece
     * @param toWhere to where to move a piece
     */
    public void maybeMove(String fromWhere, String toWhere) {
        maybeMove(
                getColumn(fromWhere), getRow(fromWhere), 
                getColumn(toWhere), getRow(toWhere)
        );
    }
    
    /**
     * Used to check whether this move is legal
     * @param fromWhereX from which column to move a piece
     * @param fromWhereY from which row to move a piece
     * @param toWhereX to which column to move a piece
     * @param toWhereY to which row to move a piece
     */
    public void maybeMove(int fromWhereX, int fromWhereY, int toWhereX, int toWhereY) {
        board[toWhereX][toWhereY] = board[fromWhereX][fromWhereY];
        board[fromWhereX][fromWhereY] = null;
    }
    
    /**
     * Moves a piece according to a move denoted by the number<br>
     * Searches through allLegalMoves to find it
     * @param whichMove which move
     */
    public void movePiece(int whichMove) {
        recalculateMoves();
        if(whichMove < 0) 
            throw new IndexOutOfBoundsException(whichMove + " under 0");
        if(whichMove >= numOfLegalMoves())
            throw new IndexOutOfBoundsException(whichMove + " over " + numOfLegalMoves());
        int copy = whichMove;
        String from = null, to = null;
        for(String key : allLegalMoves.keySet()) {
            if(copy < allLegalMoves.get(key).size()) {
                from = key;
                to = allLegalMoves.get(key).get(copy);
                break;
            } else {
                copy -= allLegalMoves.get(key).size();
            }
        }
        if(from == null || to == null) 
            assert false : "Impossible!";
        movePiece(from, to);
    }
    
    /**
     * Returns a String denoting the move in allLegalMoves
     * @param whichMove which move
     * @return a String denoting the move in allLegalMoves
     */
    public String getMove(int whichMove) {
        String from = allLegalMoves.keySet().toArray()[whichMove].toString();
        return from + " -> " + allLegalMoves.get(from);
    }
    
    /**
     * Recalculates all of the moves on a square
     */
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
    
    /**
     * Determines how many legal moves there are
     * @return how many legal moves there are
     */
    public int numOfLegalMoves() {
        int output = 0;
        for(LinkedList<String> value : allLegalMoves.values()) {
            output += value.size();
        }
        return output;
    }
    
    /**
     * Prints the current state of the game.
     */
    public void printBoard() {
        for(int i = 0;i<columns;i++) {
            for(int j = 0;j<rows;j++) {
                AbstractPiece ap = board[j][i];
                if(ap == null) {
                    System.out.print(" ");
                } else if(ap.isWhite) {
                    System.out.print(ap.getCharRepresentation());
                } else {
                    System.out.print(ap.getCharRepresentation().toLowerCase());
                }
            }
            System.out.println();
        }
    }

    /**
     * Returns the current player
     * @return the current player
     */
    public boolean currentPlayer() {
        return playerIsWhite;
    }

    /**
     * Sets this current player
     * @param playerIsWhite whether the player would be white
     */
    public void setCurrentPlayer(boolean playerIsWhite) {
        this.playerIsWhite = playerIsWhite;
    }
    
    /**
     * Determines whether this game is isFinished
     * @return whether the game is isFinished
     */
    public abstract boolean isFinished();
    
    /**
     * Determines the result of the game, if finished.
     * @return 1 if white won, 0 if draw, -1 if black won. If not finished, returns 0.
     */
    public abstract int getResult();
}