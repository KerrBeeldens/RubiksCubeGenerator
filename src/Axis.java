/**
 * This Represents a 3D axis
 * Can be considered as a unit vector parallel to the given axis and pointing in the positive or negative direction
 */
enum Axis {
    X, Y, Z,
    X_NEGATIVE, Y_NEGATIVE, Z_NEGATIVE;

    public Axis inverse() {
        return switch(this) {
            case X -> X_NEGATIVE;
            case X_NEGATIVE -> X;
            case Y -> Y_NEGATIVE;
            case Y_NEGATIVE -> Y;
            case Z -> Z_NEGATIVE;
            case Z_NEGATIVE -> Z;
        };
    }
}