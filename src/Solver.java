import java.awt.Point;
import java.util.Random;

public class Solver {
    private static final Random SEED_GENERATOR = new Random();

    private Random random;
    private long seed;
    private Space[][] board;
    private Space[][] visibleBoard;

    private Point nextMove;
    private boolean nextFlag;

    public Solver() {
        this(SEED_GENERATOR.nextLong());
    }

    public Solver(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }

    /**
     * A method to poll the solver for its next move
     * @param gameBoard the visible game board
     * @return the coordinates of the next move
     */
    public Point pollMove(Space[][] gameBoard) {
        visibleBoard = gameBoard;
        calculateNextMove();
        return nextMove;
    }

    /**
     * Should be called after pollMove
     * @return whether the coordinate returned in pollMove should have its flag toggled
     */
    public boolean willToggleFlag() {
        return nextFlag;
    }

    public void moveResult(int row, int col, boolean lost) {

    }

    public String toHTML() {
        return Scribe.htmlGame(seed, visibleBoard);
    }

    private void calculateNextMove() {

    }
}
