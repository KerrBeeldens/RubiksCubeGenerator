import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CubeCssGenerator {

    /**
     * Generate CSS logic to display the Rubik's cube moves
     *
     * @param rubiksCube The Rubik's cube
     * @param filepath   The filepath to save the generated CSS to
     */
    public static void generateCss(RubiksCube rubiksCube, String filepath) {
        StringBuilder css = new StringBuilder();

        // Generate a custom property for each step
        int steps = rubiksCube.getMoveCount();
        for (int i = 0; i < steps; i++) {
            css.append(String.format(
                    """
                            @property --step-%03d {
                                syntax: "<angle>";
                                inherits: false;
                                initial-value: 0deg;
                            }
                            
                            body:has(> div:first-of-type > div:nth-child(%d) > label > input[required]:checked)>div>div {
                                --step-%03d: 90deg;
                            }
                            
                            """, i, i + 1, i
            ));
        }

        // Add whitespace
        css.append("\n");

        // Generate a CSS Selector + formatting for each cublet
        ArrayList<Cublet> cublets = rubiksCube.getCublets();
        for (int i = 0; i < cublets.size(); i++) {

            // Get movement history
            StringBuilder moveHistorySB = new StringBuilder();
            ArrayList<Cublet.CubletMove> moveHistory = cublets.get(i).getMoveHistory();

            for (Cublet.CubletMove cubletMove : moveHistory) {

                int rotationSign = switch (cubletMove.axis()) {
                    case X, Y, Z -> 1;
                    case X_NEGATIVE, Y_NEGATIVE, Z_NEGATIVE -> -1;
                };

                int moveCount = cubletMove.moveCount();

                String rotationAxis = switch (cubletMove.axis()) {
                    case X, X_NEGATIVE -> "X";
                    case Y, Y_NEGATIVE -> "Y";
                    case Z, Z_NEGATIVE -> "Z";
                };

                int numberOfTurns = cubletMove.numberOfTurns();

                moveHistorySB.append(String.format("rotate%s(calc(var(--step-%03d) * %d)) ",
                        rotationAxis, moveCount, numberOfTurns * rotationSign));
            }

            // Use movement history in final selector
            css.append(String.format("""
                    body>div:nth-of-type(%d)>div {
                        transform:
                            %stranslateX(calc(var(--x) * var(--cube-width))) translateY(calc(var(--y) * var(--cube-height))) translateZ(calc(var(--z) * var(--cube-depth)));
                    }
                    
                    """, i + 2, moveHistorySB));
        }

        // Store the result in the specified path
        try (PrintWriter output = new PrintWriter(filepath)) {
            output.print(css);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
