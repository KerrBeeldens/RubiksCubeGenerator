import java.util.ArrayList;

/**
 * This represents a singular cube inside a Rubik's Cube i.e. a complete 3x3 cube consists of 27 cublets
 * Each cublet has a location and its own local coordinate system with respect the global coordinates
 */
public class Cublet {

    private int x;
    private int y;
    private int z;

    // globalXAxis, globalYAxis and globalZAxis contain around what local axis the cublet would need to rotate,
    // to result in the desired global rotation.
    // i.e. when globalXAxis = Axis.Z_NEGATIVE, a counterclockwise rotation about the Z axis would result in a clockwise
    // rotation about the global X axis.
    private Axis globalXAxis = Axis.X;
    private Axis globalYAxis = Axis.Y;
    private Axis globalZAxis = Axis.Z;

    private final ArrayList<CubletMove> moveHistory = new ArrayList<>();

    /**
     * Create a singular cublet inside a Rubik's Cube.
     *
     * @param x X start position of the cublet
     * @param y Y start position of the cublet
     * @param z Z start position of the cublet
     */
    public Cublet(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Move a cublet given a move notation
     * A Cublet may not move if it is not part of the move
     *
     * @param move the move to be applied
     */
    public void move(Move move, int moveCount) {
        if (!isPartOfMove(move)) return;

        Axis moveAxis = getAxis(move);
        int turns = getTurns(move);

        Axis localAxis = switch (moveAxis) {
            case Axis.X -> this.globalXAxis;
            case Axis.Y -> this.globalYAxis;
            case Axis.Z -> this.globalZAxis;
            default -> throw new IllegalStateException("Unexpected value: " + moveAxis);
        };

        moveHistory.add(new CubletMove(localAxis, turns, moveCount));
        move(moveAxis, turns);
    }

    /**
     * Check if this cublet is part of the move
     *
     * @param move the move to be made
     * @return true if the cublet is part of the move, false if not
     */
    private boolean isPartOfMove(Move move) {
        return switch (move) {
            case LEFT, LEFT_PRIME, LEFT_TWICE -> this.x == -1;
            case RIGHT, RIGHT_PRIME, RIGHT_TWICE -> this.x == 1;
            case UP, UP_PRIME, UP_TWICE -> this.y == -1;
            case DOWN, DOWN_PRIME, DOWN_TWICE -> this.y == 1;
            case FRONT, FRONT_PRIME, FRONT_TWICE -> this.z == 1;
            case BACK, BACK_PRIME, BACK_TWICE -> this.z == -1;

            // Whole cube rotations involve all cublets
            case X, X_TWICE, X_PRIME, Y, Y_TWICE, Y_PRIME, Z, Z_TWICE, Z_PRIME  -> true;
        };
    }

    /**
     * Get The axis about which the move will rotate
     *
     * @param move the move to be made
     * @return the axis about which the move will rotate
     */
    private Axis getAxis(Move move) {
        return switch (move) {
            case LEFT, LEFT_PRIME, LEFT_TWICE, RIGHT, RIGHT_PRIME, RIGHT_TWICE, X, X_TWICE, X_PRIME -> Axis.X;
            case UP, UP_PRIME, UP_TWICE, DOWN, DOWN_PRIME, DOWN_TWICE, Y, Y_TWICE, Y_PRIME -> Axis.Y;
            case FRONT, FRONT_PRIME, FRONT_TWICE, BACK, BACK_PRIME, BACK_TWICE, Z, Z_TWICE, Z_PRIME -> Axis.Z;
        };
    }


    /**
     * Get the number of turns involved in the move
     *
     * @param move the move to be made
     * @return the number of moves involved. 2 is a 180-degree turn, -1 is a 90-degree counterclockwise turn
     * and 1 is a 90-degree clockwise turn
     */
    private int getTurns(Move move) {
        return switch (move) {
            case LEFT, BACK, FRONT_PRIME, DOWN_PRIME, UP, RIGHT_PRIME, X_PRIME, Y, Z_PRIME -> -1;
            case LEFT_PRIME, BACK_PRIME, FRONT, DOWN, UP_PRIME, RIGHT, X, Y_PRIME, Z -> 1;
            case LEFT_TWICE, BACK_TWICE, FRONT_TWICE, DOWN_TWICE, UP_TWICE, RIGHT_TWICE, X_TWICE, Y_TWICE, Z_TWICE -> 2;
        };
    }

    /**
     * Move a cublet about an axis for number of quarter turns
     *
     * @param axis          the axis about which to rotate
     * @param numberOfTurns the number of quarter turns to rotate
     */
    private void move(Axis axis, int numberOfTurns) {
        switch (axis) {
            case X -> rotateAboutX(numberOfTurns);
            case Y -> rotateAboutY(numberOfTurns);
            case Z -> rotateAboutZ(numberOfTurns);
        }
    }

    /**
     * Rotate the cublet about the X axis for a number of quarter turns
     *
     * @param numberOfTurns the number of quarter turns to rotate
     */
    private void rotateAboutX(int numberOfTurns) {
        numberOfTurns = (numberOfTurns % 4 + 4) % 4; // normalize to 0-3
        for (int i = 0; i < numberOfTurns; i++) {
            // Update position
            int temp = y;
            y = -z;
            z = temp;

            // Update local axes mapping to global axes
            Axis tempAxis = globalYAxis;
            globalYAxis = globalZAxis.inverse();
            globalZAxis = tempAxis;
        }
    }

    /**
     * Rotate the cublet about the Y axis for a number of quarter turns
     *
     * @param numberOfTurns the number of quarter turns to rotate
     */
    private void rotateAboutY(int numberOfTurns) {
        numberOfTurns = (numberOfTurns % 4 + 4) % 4; // normalize to 0-3
        for (int i = 0; i < numberOfTurns; i++) {
            // Update position
            int temp = x;
            x = z;
            z = -temp;

            // Update local axes mapping to global axes
            Axis tempAxis = globalXAxis;
            globalXAxis = globalZAxis;
            globalZAxis = tempAxis.inverse();
        }
    }

    /**
     * Rotate the cublet about the Z axis for a number of quarter turns
     *
     * @param numberOfTurns the number of quarter turns to rotate
     */
    private void rotateAboutZ(int numberOfTurns) {
        numberOfTurns = (numberOfTurns % 4 + 4) % 4; // normalize to 0-3
        for (int i = 0; i < numberOfTurns; i++) {
            // Update position
            int temp = x;
            x = -y;
            y = temp;

            // Update local axes mapping to global axes
            Axis tempAxis = globalXAxis;
            globalXAxis = globalYAxis.inverse();
            globalYAxis = tempAxis;
        }
    }

    // GETTERS
    public ArrayList<CubletMove> getMoveHistory() {return moveHistory;}

    /**
     * A record of a move of a cublet
     *
     * @param axis          The axis about the cublet rotated
     * @param numberOfTurns The number of quarter turns about the rotation axis
     * @param moveCount     The move number this move was part of
     */
    public record CubletMove(Axis axis, int numberOfTurns, int moveCount) {
    }
}
