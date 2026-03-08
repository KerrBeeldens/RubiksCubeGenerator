void main() {

    String[] tokens = new String[0];

    // Read moves.txt and store individual moves in tokens
    try (Scanner input = new Scanner(new File("resources/moves.txt"))) {
        String line = input.nextLine();
        tokens = line.split(" "); // Split by whitespace
    } catch (FileNotFoundException ex) {
        ex.printStackTrace();
    }

    RubiksCube rubiksCube = new RubiksCube();
}
