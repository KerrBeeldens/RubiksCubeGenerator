import java.util.ArrayList;

/**
 * This represents a Rubik's Cube of size 3x3 consisting of 27 cublets
 */
public class RubiksCube {

    ArrayList<Cublet> cublets = new ArrayList<>();

    public RubiksCube() {

        // The cube is ordered from the top layer (-y is pointing up) to the bottom layer,
        // starting from the back left cubelet, moving to the right and then to the left middle cublet etc.
        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    this.cublets.add(new Cublet(x, y, z));
                }
            }
        }
    }
}
