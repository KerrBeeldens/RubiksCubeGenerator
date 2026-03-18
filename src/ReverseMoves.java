void main() {

    String[] tokens = new String[0];

    // Read moves.txt and store individual moves in tokens
    try (Scanner input = new Scanner(new File("resources/moves.txt"))) {
        String line = input.nextLine();
        tokens = line.split(" "); // Split by whitespace
    } catch (FileNotFoundException ex) {
        ex.printStackTrace();
    }

    List<String> tokensArrayList = Arrays.stream(tokens).toList();

    List<String> tokensReversed =  tokensArrayList.reversed();

    for (String token : tokensReversed) {
        System.out.print(Move.fromString(token).inverse() + " ");
    }
}
