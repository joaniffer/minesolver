public class Main {
    public static void main(String[] args) {
        Game g = new Game(5);
        g.doMove(5, 5, false);
        System.out.println(g.toHTML());
        System.out.println(g.solver.toHTML());
    }
}
