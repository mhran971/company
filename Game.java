import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Game {
    private State state;
    private int currentPlayer; 
    private Random dice;
    private Scanner scanner;
    private Set<String> allowedPositions; 
    private Map<Integer, Set<String>> playerPieces; 

    public Game() {
        state = new State();
        currentPlayer = 0;
        dice = new Random();
        scanner = new Scanner(System.in);
        initializeAllowedPositions();
        playerPieces = new HashMap<>();
        playerPieces.put(0, new HashSet<>()); 
        playerPieces.put(1, new HashSet<>()); 
    }

    private void initializeAllowedPositions() {
        allowedPositions = new HashSet<>();
        int[][] positions = {
                {18, 8}, {17, 8}, {16, 8}, {15, 8}, {14, 8},
                {12, 6}, {12, 5}, {12, 4}, {12, 3}, {12, 2}, {12, 1},
                {10, 1}, {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6},
                {6, 8}, {5, 8}, {4, 8}, {3, 8}, {2, 8}, {1, 8}, {1, 10},
                {1, 12}, {2, 12}, {3, 12}, {4, 12}, {5, 12}, {6, 12},
                {8, 14}, {8, 15}, {8, 16}, {8, 17}, {8, 18}, {8, 19},
                {10, 19}, {12, 19}, {12, 18}, {12, 17}, {12, 16}, {12, 15},
                {12, 14}, {14, 12}, {15, 12}, {16, 12}, {17, 12}, {18, 12},
                {19, 12}, {19, 10}
        };
        for (int[] pos : positions) {
            allowedPositions.add(pos[0] + "," + pos[1]);
        }
    }

    public void playTurn() {
        System.out.println("Player " + (currentPlayer == 0 ? "Blue" : "Green") + ", press Enter to roll the dice.");
        scanner.nextLine();

        int diceRoll = rollDice();
        System.out.println("Player " + (currentPlayer == 0 ? "Blue" : "Green") + " rolled: " + diceRoll);

        int piecesOutside = countPiecesOutside();

        if (diceRoll != 6 && piecesOutside == 0) {
            System.out.println("No pieces outside the base and you didn't roll a 6. Turn ends.");
            switchPlayer();
            return;
        }

        if (diceRoll == 6) {
            if (piecesOutside == 0) {
                System.out.println("No pieces outside the base. A new piece will be brought out.");
                movePieceToStart();
            } else {
                int choice;
                do {
                    System.out.println("You rolled a 6! You can move a piece or bring a new one out.");
                    System.out.println("Press 1 to bring a new piece out, or 2 to move an existing piece.");
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        movePieceToStart();
                    } else if (choice == 2) {
                        promptAndMovePiece(diceRoll);
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } while (choice != 1 && choice != 2);
            }
            System.out.println("Player " + (currentPlayer == 0 ? "Blue" : "Green") + " gets another turn!");
            printBoard();
            playTurn();
        } else {
            promptAndMovePiece(diceRoll);
            switchPlayer();
        }
    }

    private int rollDice() {
        return dice.nextInt(6) + 1;
    }

    private void movePieceToStart() {
        String playerSymbol = currentPlayer == 0 ? "B" : "G";
        int startRow = currentPlayer == 0 ? 18 : 2;
        int startCol = currentPlayer == 0 ? 8 : 12;

        state.getBoard()[startRow][startCol] += playerSymbol; 
        playerPieces.get(currentPlayer).add(startRow + "," + startCol); 
        System.out.println("Player " + (currentPlayer == 0 ? "Blue" : "Green") + " brought a piece out of the base.");
        clearBasePosition();
        printBoard();
    }

    private int countPiecesOutside() {
        String playerSymbol = currentPlayer == 0 ? "B" : "G";
        int count = 0;

        int[][] basePositions = currentPlayer == 0
                ? new int[][]{{15, 2}, {15, 4}, {17, 2}, {17, 4}}
                : new int[][]{{2, 15}, {2, 17}, {4, 15}, {4, 17}};

        for (int[] pos : basePositions) {
            int row = pos[0];
            int col = pos[1];
            if (!state.getBoard()[row][col].contains(playerSymbol)) {
                count++;
            }
        }
        return count;
    }

    private void clearBasePosition() {
        String playerSymbol = currentPlayer == 0 ? "B" : "G";

        int[][] basePositions = currentPlayer == 0
                ? new int[][]{{15, 2}, {15, 4}, {17, 2}, {17, 4}}
                : new int[][]{{2, 15}, {2, 17}, {4, 15}, {4, 17}};

        for (int[] pos : basePositions) {
            int row = pos[0];
            int col = pos[1];
            if (state.getBoard()[row][col].contains(playerSymbol)) {
                state.getBoard()[row][col] = " "; 
                break;
            }
        }
    }

    private void promptAndMovePiece(int diceRoll) {
        while (true) {
            System.out.println("Choose the piece to move (enter row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            scanner.nextLine();

            String playerSymbol = currentPlayer == 0 ? "B" : "G";

            if (row >= 0 && row < state.getBoard().length && col >= 0 && col < state.getBoard()[0].length) {
                if (state.getBoard()[row][col].contains(playerSymbol)) { 
                    movePiece(row, col, diceRoll);
                    break; // Exit the loop if a valid piece is selected
                } else {
                    System.out.println("Invalid piece selection. Valid pieces are at: " + getValidPiecePositions());
                }
            } else {
                System.out.println("Invalid input. Please enter valid row and column numbers.");
            }
        }
    }

    private String getValidPiecePositions() {
        StringBuilder validPositions = new StringBuilder();
        String playerSymbol = currentPlayer == 0 ? "B" : "G";

        for (String position : playerPieces.get(currentPlayer)) {
            validPositions.append(position).append(" ");
        }

        return validPositions.toString().trim();
    }

    private void movePiece(int row, int col, int diceRoll) {
        String playerSymbol = currentPlayer == 0 ? "B" : "G";

        state.getBoard()[row][col] = state.getBoard()[row][col].replaceFirst(playerSymbol, ""); 

        int[][] allowedPositionsArray = currentPlayer == 0
            ? new int[][]{ 
                {18, 8}, {17, 8}, {16, 8}, {15, 8}, {14, 8},
                {12, 6}, {12, 5}, {12, 4}, {12, 3}, {12, 2}, {12, 1},
                {10, 1}, {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6},
                {6, 8}, {5, 8}, {4, 8}, {3, 8}, {2, 8}, {1, 8}, {1, 10},
                {1, 12}, {2, 12}, {3, 12}, {4, 12}, {5, 12}, {6, 12},
                {8, 14}, {8, 15}, {8, 16}, {8, 17}, {8, 18}, {8, 19},
                {10, 19}, {12, 19}, {12, 18}, {12, 17}, {12, 16}, {12, 15},
                {12, 14}, {14, 12}, {15, 12}, {16, 12}, {17, 12}, {18, 12},
                {19, 12}, {19, 10}
            }
            : new int[][]{ 
                {2, 12}, {3, 12}, {4, 12}, {5, 12}, {6, 12}, {8, 14}, {8, 15},
                {8, 16}, {8, 17}, {8, 18}, {8, 19}, {10, 19}, {12, 19},
                {12, 18}, {12, 17}, {12, 16}, {12, 15}, {12, 14}, {14, 12},
                {13, 12}, {16, 12}, {17, 12}, {18, 12}, {19, 12}, {19, 10},
                {19, 8}, {18, 8}, {17, 8}, {16, 8}, {15, 8}, {14, 8},
                {12, 6}, {12, 5}, {12, 4}, {12, 3}, {12, 2}, {12, 1},
                {10, 1}, {8, 1}, {8, 2}, {8, 3}, {8, 4}, {8, 5}, {8, 6},
                {6, 8}, {5, 8}, {4, 8}, {3, 8}, {2, 8}, {1, 8}, {1, 10}
            };

        int currentIndex = -1;
        for (int i = 0; i < allowedPositionsArray.length; i++) {
            if (allowedPositionsArray[i][0] == row && allowedPositionsArray[i][1] == col) {
                currentIndex = i;
                break;
            }
        }

        for (int i = 0; i < diceRoll; i++) {
            currentIndex++;
            if (currentIndex >= allowedPositionsArray.length) {
                System.out.println("The piece has reached the end of the board.");
                return; 
            }

            row = allowedPositionsArray[currentIndex][0];
            col = allowedPositionsArray[currentIndex][1];
        }

        String key = row + "," + col;
        if (!allowedPositions.contains(key)) {
            System.out.println("Invalid move. Position is not allowed.");
            return;
        }

        state.getBoard()[row][col] += playerSymbol;
        playerPieces.get(currentPlayer).add(row + "," + col);
        System.out.println("Piece moved to (" + row + "," + col + ").");
        printBoard();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    private void printBoard() {
        String[][] board = state.getBoard();
        System.out.println("Current Board:");
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
