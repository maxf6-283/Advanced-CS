import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;

public class Game implements Serializable {
    public static final long serialVersionUID = 6283L;
    private char[][] board;
    private char turn = 'X';

    public Game() {
        board = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean insertXO(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = turn;
            turn = turn == 'X' ? 'O' : 'X';
            return true;
        } else {
            return false;
        }
    }

    public char winner() {
        // rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] != ' ') {
                    return board[i][0];
                }
            }
        }

        // cols
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] != ' ') {
                    return board[0][i];
                }
            }
        }

        // diagonals
        if (board[1][1] == ' ') {
            return ' ';
        } else {
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                return board[1][1];
            }
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                return board[1][1];
            }
        }
        return ' ';
    }

    public void drawMe(Graphics g, int x, int y, int w, int h) {
        //bg
        g.setColor(Color.WHITE);
        g.fillRect(x, y, w, h);

        // draw the board
        g.setColor(Color.BLACK);
        g.fillRect(x + w / 3 - 4, y, 8, h);
        g.fillRect(x + 2 * w / 3 - 4, y, 8, h);
        g.fillRect(x, y + h / 3 - 4, w, 8);
        g.fillRect(x, y + 2 * h / 3 - 4, w, 8);

        // draw the letters
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') {
                    drawX(g, x + j * w / 3 + 5, y + i * h / 3 + 5, w / 3 - 10, h / 3 - 10);
                } else if (board[i][j] == 'O') {
                    drawO(g, x + j * w / 3 + 5, y + i * h / 3 + 5, w / 3 - 10, h / 3 - 10);
                }
            }
        }
    }

    private void drawX(Graphics g, int x, int y, int w, int h) {
        g.setColor(Color.BLUE);
        g.fillPolygon(new int[] { x + 5, x + w, x + w - 5, x }, new int[] { y, y + h - 5, y + h, y + 5 }, 4);
        g.fillPolygon(new int[] { x + 5, x + w, x + w - 5, x }, new int[] { y + h, y + 5, y, y + h - 5 }, 4);
    }

    private void drawO(Graphics g, int x, int y, int w, int h) {
        g.setColor(Color.RED);
        g.fillOval(x, y, w, h);
        g.setColor(Color.WHITE);
        g.fillOval(x + 6, y + 6, w - 12, h - 12);
    }
}