package common;

import checkers.CheckerBoard;
import java.util.LinkedList;
import java.util.List;

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
        List<TreeNode> list = new LinkedList<>();
        list.add(null);
        TreeNode tn = new TreeNode(new CheckerBoard());
        long start = System.nanoTime();
        tn.selectAction();
        long time = System.nanoTime() - start;
        System.out.println(time + " nanos");
    }
}
