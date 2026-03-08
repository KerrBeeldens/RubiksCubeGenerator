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

    public Cublet(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Move a cublet given a move notation
     * @param move the move the cublet is part of
     */
    public void move(Move move) {
    }
    // GETTERS
    public int getX() {return x;}
    public int getY() {return y;}
    public int getZ() {return z;}
}
