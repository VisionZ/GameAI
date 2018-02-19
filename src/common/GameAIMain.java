package common;

import checkers.CheckerBoard;

/**
 * The Main class
 * @author Jed Wang
 */
public class GameAIMain {
    /**
     * The Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board cb = new CheckerBoard();
        TreeNode tn = new TreeNode(cb);
        double start = System.currentTimeMillis();
        for (int j = 0; j < 10000; j++) {
            System.out.println(j);
            tn.selectAction();
        }
        double total = System.currentTimeMillis() - start;
        System.out.println((total/1000) + " seconds");
        TreeNode best = tn.select();
        best.getBoard().printBoard();
        /*cb = best.getBoard();
        best = tn.bestChild();
        /*CheckerBoard cb = new CheckerBoard();
        while(!cb.isFinished()) {
            cb.recalculateMoves();
            int random = (int) (Math.random() * cb.numOfLegalMoves());
            cb.movePiece(random);
        }
        System.out.println("DONE: " + cb.getResult());*/
    }
}
