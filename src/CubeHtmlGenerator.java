import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;

public class CubeHtmlGenerator {

    /**
     * Generate HTML checkboxes to display the Rubik's cube moves
     *
     * @param rubiksCube The Rubik's cube
     * @param filepath   The filepath to save the generated HTML to
     */
    public static void generateHtml(RubiksCube rubiksCube, String filepath) {
        StringBuilder html = new StringBuilder();
        html.append("<div>\n");

        ArrayList<Move> moveHistory = rubiksCube.getMoveHistory();

        // Moves to exclude
        EnumSet<Move> excludedMoves = EnumSet.of(
                Move.X, Move.X_TWICE, Move.X_PRIME,
                Move.Y, Move.Y_TWICE, Move.Y_PRIME,
                Move.Z, Move.Z_TWICE, Move.Z_PRIME
        );

        for (int i = 0; i < moveHistory.size(); i++) {
            Move correctMove = moveHistory.get(i);

            // Skip excluded moves
            if (excludedMoves.contains(correctMove)) {
                continue;
            }

            html.append("    <div>\n"); // start of step div

            for (Move move : Move.values()) {
                // Skip excluded moves
                if (excludedMoves.contains(move)) {
                    continue;
                }

                String moveStr = move.toString();

                // Checkbox id: replace invalid characters for HTML id
                String id = moveStr.replace("'", "p") + "-" + String.format("%03d", i);

                html.append(String.format(
                        "        <label>%s\n" +
                                "            <input id=\"%s\" type=\"checkbox\"%s>\n" +
                                "        </label>\n",
                        moveStr,
                        id,
                        move.equals(correctMove) ? " required" : ""
                ));
            }

            html.append("    </div>\n"); // end of step div
        }

        html.append("</div>\n"); // end of outer div

        // Write HTML to file
        try (PrintWriter output = new PrintWriter(filepath)) {
            output.print(html);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}