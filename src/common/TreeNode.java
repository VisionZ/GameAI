package common;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A MCTS tree.
 * @author Simon Lucas
 * Tweaked by Jed Wang
 * @since 2010
 */
public class TreeNode {
    /**
     * A RNG for tie breaks.
     */
    static Random r = new Random();
    
    /**
     * The avaliable number of moves.
     * Change later on to not be final
     */
    private int nActions;
    
    /**
     * Very small number for tie breaks.
     */
    public static double EPSILON = 1e-6;

    
    /**
     * This {@code TreeNode}'s children.
     */
    private TreeNode[] children;
    
    /**
     * The number of total visits.
     */
    private double nVisits;
    
    /**
     * The value of this {@code TreeNode}.
     */
    private double totValue;
    
    /**
     * This node's state of the game
     */
    private Board b;
    
    /**
     * The starting player
     */
    private final boolean startingPlayer;
    
    /**
     * The win count
     */
    private int whiteWinCount = 0, blackWinCount = 0;

    public TreeNode(Board b) {
        this.b = b;
        nActions = b.numOfLegalMoves();
        startingPlayer = b.currentPlayer();
    }

    /**
     * Searches through the Monte Carlo tree.
     */
    public void selectAction() {
        nActions = b.numOfLegalMoves();
        // System.out.println("SELECTING");
        List<TreeNode> visited = new LinkedList<>();
        TreeNode cur = this;
        visited.add(this);
        while (!cur.isLeaf()) {
            cur = cur.select();
            if(cur == null) return;
            // System.out.println("Adding: " + cur);
            visited.add(cur);
        }
        // System.out.println("EXPANDING");
        cur.expand();
        // System.out.println("SELECTING");
        TreeNode newNode = cur.select();
        newNode.b.setCurrentPlayer(!cur.b.currentPlayer());
        visited.add(newNode);
        // System.out.println("SIMULATING");
        double value = simulate(newNode);
        // System.out.println("UPDATING");
        for(TreeNode node : visited) {
            // would need extra logic for n-player game
            // System.out.println(node.toString());
            node.updateStats(value);
        }
    }

    /**
     * Creates new {@code TreeNode}s to match all playable branches of the game.
     */
    public void expand() {
        b.recalculateMoves();
        nActions = b.numOfLegalMoves();
        // System.out.println("nActions: " + nActions);
        children = new TreeNode[nActions];
        // b.printBoard();
        for (int i=0; i<nActions; i++) {
            Board temp = b.deepCopy();
            temp.movePiece(i);
            temp.recalculateMoves();
            // temp.printBoard();
            children[i] = new TreeNode(temp);
        }
    }

    /**
     * Selects a {@code TreeNode} to start searching from
     * @return a {@code TreeNode}
     */
    public TreeNode select() {
        TreeNode selected = null;
        double bestValue = Double.MIN_VALUE;
        for (TreeNode c : children) {
            double uctValue =
                    c.totValue / (c.nVisits + EPSILON) +
                            Math.sqrt(Math.log(nVisits+1) / (c.nVisits + EPSILON)) +
                            r.nextDouble() * EPSILON;
            // small random number to break ties randomly in unexpanded nodes
            // System.out.println("UCT value = " + uctValue);
            if (uctValue > bestValue) {
                selected = c;
                bestValue = uctValue;
            }
        }
        // System.out.println("Returning: " + selected);
        return selected;
    }

    /**
     * Determines whether this {@code TreeNode} is a leaf.
     * @return whether this {@code TreeNode} is a leaf
     */
    public boolean isLeaf() {
        return children == null;
    }

    /**
     * Simulates the game from the given {@code TreeNode}
     * @param tn the {@code TreeNode} to simulate from
     * @return the result
     */
    public double simulate(TreeNode tn) {
        // ultimately a roll out will end in some value
        // assume for now that it ends in a win or a loss
        // and just return this at random
        // Use a NN to minimax through later
        Board copy = tn.b.deepCopy();
        // copy.printBoard();
        copy.setCurrentPlayer(!copy.currentPlayer());
        while(!copy.isFinished()) {
            copy.recalculateMoves();
            if(copy.numOfLegalMoves() == 0) return (copy.currentPlayer())?-1:1;
            int random = r.nextInt(copy.numOfLegalMoves());
            copy.movePiece(random);
        }
        return copy.getResult();
    }

    /**
     * Updates the statistics of this {@code TreeNode}.
     * @param value the value to change this {@code TreeNode}.
     */
    public void updateStats(double value) {
        nVisits++;
        totValue += value;
        if(value == 1) {
            whiteWinCount++;
        } else if(value == -1) {
            blackWinCount++;
        }
    }

    /**
     * Determines how many children this {@code TreeNode} has.
     * If it is a leaf, then it returns 0.
     * @return how many children this {@code TreeNode} has
     */
    public int arity() {
        return children == null ? 0 : children.length;
    }

    /**
     * Returns this {@code TreeNode}'s board
     * @return 
     */
    public Board getBoard() {
        return b;
    }
    
    /**
     * Returns this {@code TreeNode}'s best child
     * @return the best child
     */
    public TreeNode bestChild() {
        if(isLeaf()) return null;
        TreeNode best = children[0];
        for(TreeNode child : children) {
            if(best.winPercentage() < child.winPercentage()) {
                best = child;
            }
        }
        return best;
    }
    
    /**
     * Determines how much of the play-throughs are wins
     * @return the win percentage
     */
    public double winPercentage() {
        if(startingPlayer) {
            return whiteWinCount / nVisits;
        } else {
            return blackWinCount / nVisits;
        }
    }

    /**
     * Determines how many times this node has been visited
     * @return the number of times this node has been visited
     */
    public double getVisits() {
        return nVisits;
    }
    
    /**
     * Determines how many wins through a search a side has
     * @param isWhite which side
     * @return how many wins through a search a side has
     */
    public int getWins(boolean isWhite) {
        if(isWhite) {
            return whiteWinCount;
        } else {
            return blackWinCount;
        }
    }
}