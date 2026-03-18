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

    public Move inverse() {
        return switch (this) {
            case LEFT -> LEFT_PRIME;
            case LEFT_PRIME -> LEFT;
            case LEFT_TWICE -> LEFT_TWICE;

            case RIGHT -> RIGHT_PRIME;
            case RIGHT_PRIME -> RIGHT;
            case RIGHT_TWICE -> RIGHT_TWICE;

            case UP -> UP_PRIME;
            case UP_PRIME -> UP;
            case UP_TWICE -> UP_TWICE;

            case DOWN -> DOWN_PRIME;
            case DOWN_PRIME -> DOWN;
            case DOWN_TWICE -> DOWN_TWICE;

            case FRONT -> FRONT_PRIME;
            case FRONT_PRIME -> FRONT;
            case FRONT_TWICE -> FRONT_TWICE;

            case BACK -> BACK_PRIME;
            case BACK_PRIME -> BACK;
            case BACK_TWICE -> BACK_TWICE;

            case X -> X_PRIME;
            case X_PRIME -> X;
            case X_TWICE -> X_TWICE;

            case Y -> Y_PRIME;
            case Y_PRIME -> Y;
            case Y_TWICE -> Y_TWICE;

            case Z -> Z_PRIME;
            case Z_PRIME -> Z;
            case Z_TWICE -> Z_TWICE;
        };
    }

    @Override
    public String toString() {
        return move;
    }
}