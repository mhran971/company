public class LudoBoard {
    public static void main(String[] args) {
        State state = new State();
        Game game = new Game();
        state.printBoard(); 

        boolean gameWon = false;
        while (!gameWon) {
            game.playTurn();
            gameWon = checkWinCondition(state, game);
        }
        System.out.println("Player " + (game.getCurrentPlayer() == 0 ? "Blue" : "Green") + " wins!");
    }

    private static boolean checkWinCondition(State state, Game game) {
        String playerSymbol = game.getCurrentPlayer() == 0 ? "B" : "G";
        int piecesInEndZone = 0;

        int[][] endZonePositions = game.getCurrentPlayer() == 0
                ? new int[][]{{18, 8}, {19, 8}, {20, 8}} 
                : new int[][]{{2, 12}, {2, 14}, {2, 16}}; 

        for (int[] pos : endZonePositions) {
            int row = pos[0];
            int col = pos[1];
            if (state.getBoard()[row][col].equals(playerSymbol)) {
                piecesInEndZone++;
            }
        }

        return piecesInEndZone == endZonePositions.length;
    }
}
