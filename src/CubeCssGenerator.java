import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Move> moves = rubiksCube.getMoveHistory();

        // Generate the step name
        ArrayList<String> stepNames = new ArrayList<>();

        int rotationCount = 0;
        int stepCount = 0;

        for (int i = 0; i < moves.size(); i++) {

            // Determine the move type
            String moveType = switch (moves.get(i)) {
                // X, Y, Z rotations
                case X, X_TWICE, X_PRIME,
                     Y, Y_TWICE, Y_PRIME,
                     Z, Z_TWICE, Z_PRIME -> "rotation";

                // All other moves (face turns)
                case LEFT, LEFT_TWICE, LEFT_PRIME,
                     RIGHT, RIGHT_TWICE, RIGHT_PRIME,
                     UP, UP_TWICE, UP_PRIME,
                     DOWN, DOWN_TWICE, DOWN_PRIME,
                     FRONT, FRONT_TWICE, FRONT_PRIME,
                     BACK, BACK_TWICE, BACK_PRIME -> "step";
            };

            // Increment counters
            if (moveType.equals("rotation")) {
                rotationCount++;
            } else {
                stepCount++;
            }

            stepNames.add(String.format("--%s-%03d", moveType, moveType.equals("rotation") ? rotationCount : stepCount));
        }

        // Generate custom property for each step
        for (int i = 0; i < steps; i++) {

            // Append CSS for this move
            css.append(String.format(
                    """
                            @property %s {
                                syntax: "<angle>";
                                inherits: true;
                                initial-value: 90deg;
                            }
                            """, stepNames.get(i)
            ));
        }
        // Add whitespace
        css.append("\n");

        // Add selector for button container
        css.append("body>main>div:nth-of-type(2) {\n");

        // Add transition for each step
        List<String> transitions = new ArrayList<>();

        for (String stepName : stepNames) {
            if (stepName.contains("step")) {
                transitions.add(String.format("        %s 0.3s ease-in-out", stepName));
            } else {
                transitions.add(String.format("        %s 0.5s ease-in-out 0.5s", stepName));
            }
        }

        css.append("    transition:\n");
        css.append(String.join(",\n", transitions));
        css.append(";\n\n");


        // Generate button check for each step
        int lastStepNumber = 0;
        for (String stepName : stepNames.reversed()) {

            if (stepName.contains("step")) {
                lastStepNumber++;
            }
            css.append(String.format(
                    """
                                &:has(div:nth-of-type(2)>:nth-child(4)>div:nth-child(%d) > label > input[required]:checked) {
                                    %s: 0deg;
                                }
                            """, lastStepNumber, stepName
            ));

        }

        // Add whitespace
        css.append("\n");

        // Add selector for cube container
        css.append("    &>div:nth-of-type(1) {\n");

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

                moveHistorySB.append(String.format("rotate%s(calc(var(%s) * %d))\n                ",
                        rotationAxis, stepNames.get(moveCount), numberOfTurns * rotationSign));
            }

            // Use movement history in final selector
            css.append(String.format("""
                            &>div:nth-of-type(%d)>div {
                                transform:
                                    %svar(--base-translate);
                            }
                    """, i + 1, moveHistorySB));
        }

        // Close selector groups
        css.append("    }\n}");

        // Store the result in the specified path
        try (PrintWriter output = new PrintWriter(filepath)) {
            output.print(css);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


//body>main>div:nth-of-type(2)>div:nth-of-type(2)>:nth-child(4)>div