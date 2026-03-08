/**
 * This represents a Rubik's Cube, where
 * L, R, U, D, F, B represent Left, Right, Up, Down, Front and Back faces
 * L represents a clockwise rotation by 90 degrees, L' a counterclockwise rotation
 * L2 represents a rotation by 180 degrees
 */
public enum Move {
    LEFT("L"),
    LEFT_TWICE("L2"),
    LEFT_PRIME("L'"),
    RIGHT("R"),
    RIGHT_TWICE("R2"),
    RIGHT_PRIME("R'"),
    UP("U"),
    UP_TWICE("U2"),
    UP_PRIME("U'"),
    DOWN("D"),
    DOWN_TWICE("D2"),
    DOWN_PRIME("D'"),
    FRONT("F"),
    FRONT_TWICE("F2"),
    FRONT_PRIME("F'"),
    BACK("B"),
    BACK_TWICE("B2"),
    BACK_PRIME("B'");

    private final String move;

    Move(String move) {
        this.move = move;
    }

    /**
     * Convert a given move to a move Enum
     * @param moveString String representing a move
     * @return Enum Move with type represented by provided string
     */
    public static Move fromString(String moveString) {

        for (Move move : Move.values()) {
            if (move.move.equalsIgnoreCase(moveString)) {
                return move;
            }
        }
        throw new IllegalArgumentException("No constant with text " + moveString + " found");
    }

    @Override
    public String toString() {
        return move;
    }
}