public class Scribe {
    private static final String formatString =
            "<html>" +
                    "<head>" +
                    "<title>Seed %d</title>" +
                    "<style>" +
                    "td { width: 20px } " +
                    ".boom { color: red } " +
                    ".mine { color: blue } " +
                    ".good-flag { color: green } " +
                    ".bad-flag { color: orange } " +
                    ".unknown { color: white } " +
                    "</style>" +
                    "</head>" +
                    "<body>\n%s\n</body>" +
             "</html>";

    private static final String formatCell =
            "<td class=\"%s\">%s</td>";

    // TODO: decide way to calculate exploded mine
    public static final int EXPLODED_MINE = 99;
    public static final int UNTOUCHED_MINE = 9;
    public static final int CORRECT_FLAG = 11;
    public static final int INCORRECT_FLAG = 12;
    public static final int UNKNOWN = 10;

    public static String htmlGame(long seed, Space[][] board) {
        return String.format(formatString, seed, boardAsTable(board));
    }

    private static String boardAsTable(Space[][] board) {
        StringBuilder table = new StringBuilder();
        table.append("<table>\n");

        for (Space[] row : board) {
            table.append("<tr>");

            for (Space space : row) {
                int val;

                if (space == null) {
                    val = UNKNOWN;
                } else {
                    val = space.numMines;

                    if (space.isMine) {
                        val = (space.flagged) ? CORRECT_FLAG : UNTOUCHED_MINE;
                    } else if (space.flagged) {
                        val = INCORRECT_FLAG;
                    }
                }

                table.append(spaceToData(val));
            }

            table.append("</tr>\n");
        }

        return table.toString();
    }

    private static String spaceToData(int space) {
        String style = "";
        String data = "";

        switch (space) {
            case UNTOUCHED_MINE :
                style = "mine";
                data = "X";
                break;
            case EXPLODED_MINE :
                style = "boom";
                data = "*";
                break;
            case CORRECT_FLAG :
                style = "good-flag";
                data = "F";
                break;
            case INCORRECT_FLAG :
                style = "bad-flag";
                data = "F";
                break;
            case UNKNOWN :
                style = "unknown";
            default :
                data = Integer.toString(space);
                break;
        }

        return String.format(formatCell, style, data);
    }
}
