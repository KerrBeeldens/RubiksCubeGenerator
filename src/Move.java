/**
 * This represents a Rubik's Cube, where
 * L, R, U, D, F, B represent Left, Right, Up, Down, Front and Back faces
 * x, y, z represent a rotation of the whole cube
 * L represents a clockwise rotation by 90 degrees, L' a counterclockwise rotation
 * L2 represents a rotation by 180 degrees
 */
public enum Move {
    LEFT("L"),
    RIGHT("R"),
    UP("U"),
    DOWN("D"),
    FRONT("F"),
    BACK("B"),
    X("x"),
    Y("y"),
    Z("z"),

    LEFT_PRIME("L'"),
    RIGHT_PRIME("R'"),
    UP_PRIME("U'"),
    DOWN_PRIME("D'"),
    FRONT_PRIME("F'"),
    BACK_PRIME("B'"),
    X_PRIME("x'"),
    Y_PRIME("y'"),
    Z_PRIME("z'"),

    LEFT_TWICE("L2"),
    RIGHT_TWICE("R2"),
    UP_TWICE("U2"),
    DOWN_TWICE("D2"),
    FRONT_TWICE("F2"),
    BACK_TWICE("B2"),
    X_TWICE("x2"),
    Y_TWICE("y2"),
    Z_TWICE("z2");

    private final String move;

    Move(String move) {
        this.move = move;
    }

    /**
     * Convert a given move to a move Enum
     *
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