package _03틱텍토_박병준;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class TicTacToe {
	String[] game = new String[9];
	int size = game.length;
	String check = "";
	boolean over = false;
	int count = 2;
	int turn = 1;

	void setGame() {
		System.out.println("===틱택토===");
		for (int i = 0; i < size; i++) {
			game[i] = "[ ]";
			game[0] = "[O]";
			game[4] = "[X]";
			
		}

	}

	void showGame() {
		for (int i = 0; i < size; i++) {
			System.out.print(game[i] + " ");
			if (i % 3 == 2) {
				System.out.println();
			}
		}
	}

	private int checkValue(String msg, int start, int end) {
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		int sel = sc.nextInt();
		if (sel < start || sel > end) {
			System.out.println("범위 오류 0~8입력");

			return -1;
		} else if (!game[sel].equals("[ ]")) {
			System.out.println("이미 선택되엇습니다");
			return -1;
		}
		return sel;
	}

	void choiceIdx() {
		int idx = -1;
		while (idx == -1) {
			idx = checkValue("사용자위치선택", 0, 8);

		}
		selectPlayer(idx);

	}

	private void selectPlayer(int idx) {

		if (turn == 1) {

			game[idx] = "[O]";
			check = "[O]";

		}
		count++;
		turn = turn == 1 ? 2 : 1;
	}

	private int checkCom() {
		Random rd = new Random();
		int pick = rd.nextInt(9);
		if (!game[pick].equals("[ ]")) {
			return -1;
		}
		return pick;
	}

	void choiceCom() {
		int idx = -1;
		while (idx == -1) {
			idx = checkCom();
		}
		selectCom(idx);
	}

	private void selectCom(int idx) {

		if (turn == 2) {

			game[idx] = "[X]";
			check = "[X]";

		}
		count++;
		turn = turn == 2 ? 1 : 2;
	}

	boolean exitWidth() {// 가로 승리 조건
		for (int i = 0; i < size; i += 3) {
			if (game[i].equals(check) && game[i + 1].equals(check) && game[i + 2].equals(check)) {

				over = true;

				return true;
			}

		}
		return false;
	}

	boolean exitHeight() {//세로 승리 조건
		for (int i = 0; i < size / 3; i++) {
			if (game[i].equals(check) && game[i + 3].equals(check) && game[i + 6].equals(check)) {
				over = true;

				return true;
			}
		}
		return false;
	}

	boolean exitCross() {//대각선 승리조건
		for (int i = 0; i < size; i++) {

			if ((game[0].equals(check) && game[4].equals(check) && game[8].equals(check))
					|| game[2].equals(check) && game[4].equals(check) && game[6].equals(check)) {
				over = true;

				return true;
			}
		}
		return false;
	}

	boolean finishGame() {

		if (over) {
			System.out.println(turn == 2 ? "사용자 승리" : "컴퓨터 승리");
			return true;
		}
		if (count == size) {
			System.out.println("무승부");
			return true;
		}

		return false;
	}

	void run() {
		setGame();
		while (!finishGame()) {
			showGame();
			if (turn == 1) {
				choiceIdx();
				System.out.println("======사용자======");
			} else {
				System.out.println("======com======");
				choiceCom();
			}
			exitWidth();
			exitHeight();
			exitCross();

		}

	}
}
public class 틱택토 {

	public static void main(String[] args) {

		TicTacToe t = new TicTacToe();
		t.run();
	}

}

