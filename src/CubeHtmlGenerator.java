import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

        for (int i = 0; i < moveHistory.size(); i++) {
            Move correctMove = moveHistory.get(i);

            html.append("    <div>\n"); // start of step div

            for (Move move : Move.values()) {
                String moveStr = move.toString();

                // Checkbox id: replace invalid characters for HTML id
                String id = moveStr.replace("'", "p") + "-" + String.format("%03d", i);

                // Append 'v' to display label if this move was performed
                String displayLabel = move.equals(correctMove) ? moveStr + "v" : moveStr;

                html.append(String.format(
                        "        <label>%s\n" +
                                "            <input id=\"%s\" type=\"checkbox\"%s>\n" +
                                "        </label>\n",
                        displayLabel,
                        id,
                        displayLabel.endsWith("v") ? " required" : ""
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