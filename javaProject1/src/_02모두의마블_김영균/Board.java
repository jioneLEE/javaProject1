package _02모두의마블_김영균;

public class Board {
	int[][] board = new int[5][5];
	boolean win = false;

	void print() {
		for (int i = 0; i < board.length; i++) {
			for (int k = 0; k < board.length; k++) {
				if (board[i][k] == 0) {
					System.out.print(" □ ");
				} else if (board[i][k] == 9) {
					System.out.print(" ■ ");
				} else if (board[i][k] == 1) {
					System.out.print(" 옷 ");
				} else if (board[i][k] == 2) {
					System.out.print(" 흣 ");
				} else {
					System.out.print("옷-훗");
				}
			}
			System.out.println();
		}
	}
}
