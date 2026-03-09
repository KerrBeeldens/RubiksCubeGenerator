import java.util.ArrayList;

/**
 * This represents a Rubik's Cube of size 3x3 consisting of 27 cublets
 */
public class RubiksCube {

    private final ArrayList<Cublet> cublets = new ArrayList<>();
    private int moveCount = 0;
    private ArrayList<Move> moveHistory = new ArrayList<>();

    public RubiksCube() {

        // The cube is ordered from the top layer (-y is pointing up) to the bottom layer,
        // starting from the back left cublet, moving to the right and then to the left middle cublet etc.
        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    this.cublets.add(new Cublet(x, y, z));
                }
            }
        }
    }

    /**
     * Move the cube given a move notation
     *
     * @param move the move made
     */
    public void move(Move move) {
        // Move cublets that are part of the move
        for (Cublet cublet : cublets) {
            cublet.move(move, moveCount);
        }

        moveHistory.add(move);
        moveCount++;
    }

    // GETTERS
    public ArrayList<Cublet> getCublets() {return cublets;}
    public int getMoveCount() {return moveCount;}
    public ArrayList<Move> getMoveHistory() {return moveHistory;}
}
