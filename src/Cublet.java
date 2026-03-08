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

        Axis axis = getAxis(move);
        int turns = getTurns(move);

        moveHistory.add(new CubletMove(axis, turns, moveCount));
        move(axis, turns);
    }

    private boolean isPartOfMove(Move move) {
        return switch (move) {
            case LEFT, LEFT_PRIME, LEFT_TWICE -> this.x == -1;
            case RIGHT, RIGHT_PRIME, RIGHT_TWICE -> this.x == 1;
            case UP, UP_PRIME, UP_TWICE -> this.y == -1;
            case DOWN, DOWN_PRIME, DOWN_TWICE -> this.y == 1;
            case FRONT, FRONT_PRIME, FRONT_TWICE -> this.z == 1;
            case BACK, BACK_PRIME, BACK_TWICE -> this.z == -1;
        };
    }

    private Axis getAxis(Move move) {
        return switch (move) {
            case LEFT, LEFT_PRIME, LEFT_TWICE, RIGHT, RIGHT_PRIME, RIGHT_TWICE -> Axis.X;

            case UP, UP_PRIME, UP_TWICE, DOWN, DOWN_PRIME, DOWN_TWICE -> Axis.Y;

            case FRONT, FRONT_PRIME, FRONT_TWICE, BACK, BACK_PRIME, BACK_TWICE -> Axis.Z;
        };
    }

    private int getTurns(Move move) {
        return switch (move) {
            case LEFT, BACK_PRIME, FRONT, DOWN_PRIME, UP, RIGHT_PRIME -> 1;
            case LEFT_PRIME, BACK, FRONT_PRIME, DOWN, UP_PRIME, RIGHT -> -1;
            case LEFT_TWICE, BACK_TWICE, FRONT_TWICE, DOWN_TWICE, UP_TWICE, RIGHT_TWICE -> 2;
        };
    }

    /**
     * Move a cublet about an axis for number of quarter turns
     *
     * @param axis          the axis about which to rotate
     * @param numberOfTurns the number of quarter turns to rotate
     */
    private void move(Axis axis, int numberOfTurns) {

    }

    /**
     * A record of a move of a cublet
     *
     * @param axis          The axis about the cublet rotated
     * @param numberOfTurns The number of quarter turns about the rotation axis
     * @param moveCount     The move number this move was part of
     */
    private record CubletMove(Axis axis, int numberOfTurns, int moveCount) {
    }
}
