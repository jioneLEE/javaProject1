package _02모두의마블_김영균;

import java.util.Random;
import java.util.Scanner;

public class GamePlay {
	int turn;
	Board b;
	Player[] p;

	void init() {
		turn = 0;
		b = new Board(); // 보드 불러오기
		for (int i = 0; i < b.board.length; i++) { // 기본 게임판 입력
			for (int j = 0; j < b.board[i].length; j++) {
				int last = b.board.length - 1;
				if (i == 0 || i == last || j == 0 || j == last) {
					b.board[i][j] = 0;
				} else {
					b.board[i][j] = 9;
				}
			}
		}

		p = new Player[2];
		for (int i = 0; i < p.length; i++) { // 플레이어 기본 정보 입력
			Player temp = new Player();
			temp.y = 0;
			temp.x = 0;
			temp.playerNum = i + 1;
			temp.round = 0;
			temp.dir = 0;
			p[i] = temp;
		}
	}

	void game() {
		Scanner sc = new Scanner(System.in);
		Random rd = new Random();

		while (!b.win) {
			turn++;

			int now = (turn % 2 == 1) ? 0 : 1;

			System.out.printf("[ %d Player 차례 ]\n", p[now].playerNum);


			int num = rd.nextInt(6) + 1;

			System.out.printf("[ %d Player , 주사위 : %d ]\n", p[now].playerNum, num);
			System.out.println();
			move(now, num);

			b.print();
		}
	}

	void move(int pl, int num) {
		leave(pl, p[pl].y, p[pl].x); // 출발자리가 웃, 훗 이 있는지 확인
		for (int i = num; i > 0; i--) {
			if (p[pl].dir == 0) { // 상단라인이동
				p[pl].x += 1;
			} else if (p[pl].dir == 1) { // 우측라인이동
				p[pl].y += 1;
			} else if (p[pl].dir == 2) { // 하단라인이동
				p[pl].x -= 1;
			} else { // 그외(좌측라인이동)
				p[pl].y -= 1;
				if (p[pl].y == 0) {
					p[pl].round += 1;
				}
			}
			locationCheck(pl, p[pl].y, p[pl].x);
		}
		arrive(pl, p[pl].y, p[pl].x); // 도착자리가 웃, 훗 이 있는지 확인
		nowResult(pl);
	}

	void leave(int pl, int y, int x) {
		pl += 1;
		if (b.board[y][x] == 3) {
			b.board[y][x] -= pl;
		} else {
			b.board[y][x] = 0;
		}
	}

	void arrive(int pl, int y, int x) {
		pl += 1;
		if (b.board[y][x] != 0) {
			b.board[y][x] += pl;
		} else {
			b.board[y][x] = pl;
		}
	}

	void locationCheck(int pl, int y, int x) {
		int last = b.board.length - 1;
		if (p[pl].y == 0 && p[pl].x < last) { // 상단라인이동
			p[pl].dir = 0;
		} else if ((p[pl].x == last && p[pl].y < last)) { // 우측라인이동
			p[pl].dir = 1;
		} else if ((p[pl].y == last && p[pl].x > 0)) { // 하단라인이동
			p[pl].dir = 2;
		} else { // 그외(좌측라인이동)
			p[pl].dir = 3;
		}
	}

	void nowResult(int pl) {
		for (int i = 0; i < p.length; i++) {
			System.out.printf("[ %d Player : %d 바퀴 ]\n", i + 1, p[i].round);
		}
		if (p[pl].round == 3) {
			System.out.printf("[ !! %d Player 승리 !! ]\n", pl + 1);
			b.win = true;
		}
	}

	void run() { // 실행
		init();
		b.print();
		game();
	}

}