import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PatternUtility {
    public static final String PATTERN_FILE = "resources/patterns.dat";

    private static final char IRRELEVANT = '_';
    private static final char UNKNOWN = '-';
    private static final char FLAGGED = 'F';
    private static final Space IRRELEVANT_SPACE = new Space();
    private static final Space UNKNOWN_SPACE = null;

    private static List<Space[][]> patternList;

    private PatternUtility() {}

    public static void printPatterns() {
        if (patternList == null) {
            System.out.println("No patterns loaded.");
            return;
        }

        for (Space[][] pattern : patternList) {
            for (Space[] row : pattern) {
                for (Space space : row) {
                    if (space == null) {
                        System.out.print(UNKNOWN);
                    } else if (space.flagged) {
                        System.out.print(FLAGGED);
                    } else if (space.numMines == IRRELEVANT_SPACE.numMines) {
                        System.out.print(IRRELEVANT);
                    } else {
                        System.out.print(space.numMines);
                    }
                }

                System.out.println();
            }

            System.out.println();
        }
    }

    /**
     * Loads the patterns from a pattern file and by default calculates their permutations
     * @return if the file load succeeded
     */
    public static boolean loadPatterns() {
        File source = new File(PATTERN_FILE);

        try {
            Scanner reader = new Scanner(source);
            patternList = new ArrayList<>();

            List<String> currentPattern = new ArrayList<>();
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isEmpty()) {
                    Space[][] pattern = makePattern(currentPattern);
                    patternList.add(pattern);
                    patternList.addAll(transformPattern(pattern));
                    currentPattern.clear();
                } else {
                    currentPattern.add(line);
                }
            }

            // in case the file doesn't end with a blank line
            if (!currentPattern.isEmpty()) {
                Space[][] pattern = makePattern(currentPattern);
                patternList.add(pattern);
                patternList.addAll(transformPattern(pattern));
            }

            reader.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private static Space[][] makePattern(List<String> patternString) {
        Space[][] pattern = new Space[patternString.size()][];

        for (int i = 0; i < patternString.size(); i++) {
            String patternLine = patternString.get(i);
            Space[] line = new Space[patternLine.length()];

            char[] spaceChars = patternLine.toCharArray();
            for (int j = 0; j < spaceChars.length; j++) {
                Space space;
                switch (spaceChars[j]) {
                    case IRRELEVANT:
                        space = IRRELEVANT_SPACE;
                        break;
                    case UNKNOWN:
                        space = UNKNOWN_SPACE;
                        break;
                    case FLAGGED:
                        space = new Space();
                        space.flagged = true;
                        break;
                    default:
                        space = new Space(Integer.valueOf(""+spaceChars[j]), false);
                        break;
                }

                line[j] = space;
            }

            pattern[i] = line;
        }

        return pattern;
    }

    private static List<Space[][]> transformPattern(Space[][] pattern) {
        Space[][] rotate90 = rotate90(pattern);
        Space[][] rotate180 = rotate90(rotate90);
        Space[][] rotate270 = rotate90(rotate180);

        return Arrays.asList(rotate90, rotate180, rotate270);
    }

    private static Space[][] rotate90(Space[][] pattern) {
        int origWidth = pattern[0].length;
        int origHeight = pattern.length;

        Space[][] rotate90 = new Space[origWidth][origHeight];

        for (int i = 0; i < origHeight; i++) {
            for (int j = 0; j < origWidth; j++) {
                rotate90[j][origHeight - i - 1] = pattern[i][j];
            }
        }

        return rotate90;
    }

    /*
      1 2
      3 4
      5 6

      5 3 1
      6 4 2

      6 5
      4 3
      2 1

      2 4 6
      1 3 5
     */
}
