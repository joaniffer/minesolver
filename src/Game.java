import java.awt.Point;
import java.util.*;

public class Game {
    public static final int EXPERT_WIDTH = 30;
    public static final int EXPERT_HEIGHT = 16;
    public static final int EXPERT_MINES = 99;

    public Solver solver;

    private static final Random SEED_GENERATOR = new Random();

    private Random random;
    private long seed;
    private Space[][] board;
    private Space[][] visibleBoard;
    private boolean firstMove;

    private int gameWidth;
    private int gameHeight;
    private int gameMines;

    public Game() {
        this(SEED_GENERATOR.nextLong(), new Solver());
    }

    public Game(long seed) {
        this(seed, new Solver());
    }

    public Game(long seed, Solver solver) {
        this.seed = seed;
        random = new Random(seed);

        this.solver = solver;

        gameWidth = EXPERT_WIDTH;
        gameHeight = EXPERT_HEIGHT;
        gameMines = EXPERT_MINES;

        random = new Random();
        board = new Space[gameHeight][gameWidth];
        for (Space[] row : board) {
            for (int i = 0; i < row.length; i++) {
                row[i] = new Space();
            }
        }

        visibleBoard = new Space[gameHeight][gameWidth];
        firstMove = true;
    }

    /**
     * Plays a move on the game board
     * @param row The row of the space "clicked" on
     * @param col The col of the space "clicked" on
     * @param flag Whether or not the player is flagging this space
     *             Assumes that this param is not true on first move
     * @return true if safe, false if lose
     */
    public boolean doMove(int row, int col, boolean flag) {
        // solver.pollMove(visibleBoard);

        if (firstMove) {
            generateBoard(row, col);
            firstMove = false;
        }

        if (flag) {
            doFlag(row, col);
        }

        if (board[row][col].isMine) {
            return false;
        }

        Space space = board[row][col];

        // The visible board space will never mark the isMine boolean, so it will always be false
        // to the user end
        visibleBoard[row][col] = new Space(space.numMines, space.flagged);
        if (space.numMines == 0) {
            for (Point coord : getSurroundingSpaces(row, col)) {
                if (visibleBoard[coord.x][coord.y] == null) {
                    doMove(coord.x, coord.y, false);
                }
            }
        }

        solver.pollMove(visibleBoard);

        return true;
    }

    public long getSeed() {
        return seed;
    }

    public String toHTML() {
        return Scribe.htmlGame(seed, board);
    }

    private void doFlag(int row, int col) {
        Space space = board[row][col];

        if (space.flagged) {
            space.flagged = false;
            gameMines ++;
        } else {
            space.flagged = true;
            gameMines --;
        }
    }

    private void generateBoard(int row, int col) {
        Set<Point> mines = new HashSet<>();
        while (mines.size() < gameMines) {
            int randomRow = random.nextInt(gameHeight);
            int randomCol = random.nextInt(gameWidth);

            if (!inCenter(randomRow, randomCol, row, col)) {
                Point mine = new Point(randomRow, randomCol);
                mines.add(mine);
            }
        }

        for (Point mine : mines) {
            annotateMine(mine.x, mine.y);
        }
    }

    private void annotateMine(int row, int col) {
        board[row][col].isMine = true;

        for (Point coord : getSurroundingSpaces(row, col)) {
            board[coord.x][coord.y].numMines ++;
        }
    }

    private boolean inCenter(int testRow, int testCol, int row, int col) {
        return Math.abs(testRow - row) <= 1 || Math.abs(testCol - col) <= 1;
    }

    private List<Point> getSurroundingSpaces(int row, int col) {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int deltaRow = 0;
            int deltaCol = 0;

            if (i > 3) {
                deltaRow = (i > 5) ? -1 : 1;
            } else if (i % 2 == 0) {
                deltaRow += 1 - i;
            }

            int j = (i + 1) % 8;
            if (j > 3) {
                deltaCol = (j > 5) ? -1 : 1;
            } else if (j % 2 == 0) {
                deltaCol += 1 - j;
            }

            int targetRow = row + deltaRow;
            int targetCol = col + deltaCol;

            if (targetRow >= 0 && targetRow < gameHeight
                    && targetCol >= 0 && targetCol < gameWidth
                    && !board[targetRow][targetCol].isMine) {
                points.add(new Point(row + deltaRow, col + deltaCol));
            }
        }

        return points;
    }
}
