/*
  The space class is used by both the Solver and Game classes.
 */
public class Space {
    public int numMines;
    public boolean flagged;
    public boolean isMine;

    public Space() {
        numMines = 0;
        flagged = false;
        isMine = false;
    }

    public Space(int numMines, boolean isMine) {
        this.numMines = numMines;
        flagged = false;
        this.isMine = isMine;
    }
}
