package pe.edu.upeu.gatofx.modelo;

public class GatoTO {
    private String[][] board;
    private String currentPlayer;

    public GatoTO() {
        this.board = new String[3][3];
        this.currentPlayer = "X";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCell(int row, int col, String value) {
        board[row][col] = value;
    }

    public String getCell(int row, int col) {
        return board[row][col];
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || !board[row][col].isEmpty()) {
            return false;
        }
        board[row][col] = currentPlayer;
        return true;
    }

    public boolean isGameOver() {
        return checkWin() || isBoardFull();
    }

    private boolean checkWin() {
        return checkLine(0, 0, 0, 1, 0, 2) ||
               checkLine(1, 0, 1, 1, 1, 2) ||
               checkLine(2, 0, 2, 1, 2, 2) ||
               checkLine(0, 0, 1, 0, 2, 0) ||
               checkLine(0, 1, 1, 1, 2, 1) ||
               checkLine(0, 2, 1, 2, 2, 2) ||
               checkLine(0, 0, 1, 1, 2, 2) ||
               checkLine(0, 2, 1, 1, 2, 0);
    }

    private boolean checkLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        return !board[row1][col1].isEmpty() &&
               board[row1][col1].equals(board[row2][col2]) &&
               board[row2][col2].equals(board[row3][col3]);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
