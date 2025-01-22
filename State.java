class State {
    private String[][] board;

    public State() {
        board = new String[21][21];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == 0 || i == board.length - 1) {
                    board[i][j] = "-";
                } else if (j == 0 || j == board[i].length - 1) {
                    board[i][j] = "|";
                } else {
                    board[i][j] = " ";
                }
            }
        }
        board[2][2] = "E";
        board[2][4] = "E";
        board[4][2] = "E";
        board[4][4] = "E";

        board[1][7] = "|";
        board[2][7] = "|";
        board[3][7] = "|";
        board[4][7] = "|";
        board[5][7] = "|";
        board[6][7] = "|";
        
        board[1][9] = "|";
        board[2][9] = "|";
        board[3][9] = "|";
        board[4][9] = "|";
        board[5][9] = "|";
        board[6][9] = "|";
        
        board[1][11] = "|";
        board[2][11] = "|";
        board[3][11] = "|";
        board[4][11] = "|";
        board[5][11] = "|";
        board[6][11] = "|";
        
        board[1][13] = "|";
        board[2][13] = "|";
        board[3][13] = "|";
        board[4][13] = "|";
        board[5][13] = "|";
        board[6][13] = "|";

        board[2][15] = "G";
        board[2][17] = "G";
        board[4][15] = "G";
        board[4][17] = "G";
        
        board[15][2] = "B";
        board[15][4] = "B";
        board[17][2] = "B";
        board[17][4] = "B";
        
        board[14][7] = "|";
        board[15][7] = "|";
        board[16][7] = "|";
        board[17][7] = "|";
        board[18][7] = "|";
        board[19][7] = "|";

        board[14][9] = "|";
        board[15][9] = "|";
        board[16][9] = "|";
        board[17][9] = "|";
        board[18][9] = "|";
        board[19][9] = "|";

        board[14][11] = "|";
        board[15][11] = "|";
        board[16][11] = "|";
        board[17][11] = "|";
        board[18][11] = "|";
        board[19][11] = "|";
        
        board[14][13] = "|";
        board[15][13] = "|";
        board[16][13] = "|";
        board[17][13] = "|";
        board[18][13] = "|";
        board[19][13] = "|";

        board[15][15] = "E";
        board[15][17] = "E";
        board[17][15] = "E";
        board[17][17] = "E";

        board[8][7] = "|";
        board[10][7] = "|";
        board[12][7] = "|";
        board[8][13] = "|";
        board[10][13] = "|";
        board[12][13] = "|";

        for (int j = 1; j <= 19; j++) {
            board[7][j] = "-";
        }
        for (int j = 1; j <= 19; j++) {
            board[9][j] = "-";
        }
        for (int j = 1; j <= 19; j++) {
            board[11][j] = "-";
        }
        for (int j = 1; j <= 19; j++) {
            board[13][j] = "-";
        }
        for (int j = 7; j <= 13; j++) {
            board[9][j] = " ";
            board[11][j] = " ";
        }
        board[9][9] = "\\";
        board[9][11] = "/";
        board[11][9] = "/";
        board[11][11] = "\\";
        board[12][8] = "/";
        board[12][12] = "\\";

        board[8][10] = "G";
        board[12][10] = "Y";
        board[10][8] = "R";
        board[10][12] = "B";

        board[8][12] = "/";
        board[8][8] = "\\";

        board[17][12] = "*";
        board[12][3] = "*";
        board[3][8] = "*";
        board[8][17] = "*";
    }

    public String[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
