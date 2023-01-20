package _02지뢰찾기_test;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class mineDAO {

	//                      v      v     v     v
	private int y[] = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
	private int x[] = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

	static Map game[][];
	static int gameSize;
	int clone[][];
	static int counter;
	static int unlock;
	int log;
	int ty, tx;
	private Saveload sl;

	void saveGame(long time) {
		sl.saveGame(game.length, time);
	}

	Saveload loadGame() {
		
		Saveload sl = new Saveload();
		sl.loadGame();
		setClone();
		return sl;
	}

	// 초기화
	void init() {
		int size = earlyset();
		sl = new Saveload();

		game = new Map[size][size];
		for (int i = 0; i < game.length; i++) {
			for (int k = 0; k < game[i].length; k++) {
				game[i][k] = new Map();
			}
		}
		clone = new int[size][size];
		insertmine(size);
		//
	}

	// 지뢰찾기 게임부
	void playing2(int y, int x, int sel) {

		if (!chk(y, x)) {
			err("(이미 선택된 좌표) 좌표");
			return;
		} else if (game[y][x].mine && sel != 1) {
			System.out.println("지뢰밟았습니다.");
			for (int i = 0; i < game.length; i++) {
				for (int k = 0; k < game[i].length; k++) {
					if (game[i][k].mine)
						game[i][k].chk = true;
				}
			}
			log = 2;
			printmap();
			return;
		}
		if (sel == 2) {
			game[y][x].chk = true;
			clone[y][x] = 2;
			if (game[y][x].cnt != 0) {
				return;
			}
			if (wayexplore(y, x)) {
				search();
			}
		} else {
			game[y][x].flag = game[y][x].flag ? false : true;
		}
	}

	// 숫자입력(제대로 입력 받을 때까지)
	int getNumber(int n, int m, String msg, String err) {
		while (true) {
			int num = 0;
			System.out.println(msg + "를 입력하세요.");
			Scanner sc = new Scanner(System.in);
			try {
				num = sc.nextInt();
				if (num < n || num > m) {
					throw new Exception();
				}
			} catch (InputMismatchException e) {
				System.err.println("문자값은 입력될수 없습니다.");
				continue;
			} catch (Exception e) {
				err(err);
				continue;
			}
			return num;
		}
	}

	private void countUnlock() {
		unlock = 0;
		for (int i = 0; i < game.length; i++) {
			for (int k = 0; k < game[i].length; k++) {
				if (game[i][k].mine && game[i][k].flag) {
					unlock++;
				}
			}
		}
	}

	// 맵출력
	void printmap() {

		countUnlock();

		for (int i = 0; i < game.length; i++) {

			for (int k = 0; k < game[i].length; k++) {

				if (game[i][k].chk && game[i][k].mine) {
					System.out.printf(" %-1s ", "※");
				} else if (game[i][k].flag) {
					if (unlock == counter && game[i][k].mine) {
						System.out.printf(" %-1s ", "¶※");
					} else {
						System.out.printf(" %-1s ", "¶");
					}
				} else if (!game[i][k].chk) {
					System.out.printf(" %-1s ", "■");
				} else if (game[i][k].cnt != 0) {
					System.out.printf(" %-1d ", game[i][k].cnt);
				} else if (game[i][k].chk) {
					System.out.printf(" %-1s ", "□");
				}
			}
			System.out.println();
		}
		if (unlock == counter) {
			System.out.println("이겻습니다 ");
			log = 1;
			return;
		}
	}

	// 지뢰탐색
	private void search() {
		while (true) {
			boolean chk = false;
			for (int i = 0; i < clone.length; i++) {
				for (int k = 0; k < clone[i].length; k++) {
					if (clone[i][k] == 2 && wayexplore(i, k)) {
						chk = true;
					}
				}
			}
			if (!chk) {
				break;
			}
		}
		for (int i = 0; i < game.length; i++) {
			for (int k = 0; k < game[i].length; k++) {
				if (clone[i][k] == 2) {
					game[i][k].chk = true;
					for (int j = 0; j < x.length; j++) {
						int ty = i + this.y[j];
						int tx = k + this.x[j];
						if (chkindex(ty, tx)) {
							continue;
						} else if (!game[ty][tx].mine
								&& game[ty][tx].cnt != 0) {
							game[ty][tx].chk = true;
						}
					}
				}
			}
		}
	}

	// 주변탐색 (4방향)
	private boolean wayexplore(int y, int x) {

		for (int i = 1; i < this.y.length; i += 2) {
			int ty = y + this.y[i];
			int tx = x + this.x[i];
			if (chkindex(ty, tx)) {
				continue;
			} else if (clone[ty][tx] == 0) {
				clone[ty][tx] = 2;
				return true;
			}
		}
		return false;
	}

	// 주변탐색 (8방향)
	private int cnt(int y, int x) {
		int total = 0;
		for (int i = 0; i < this.y.length; i += 1) {
			int ty = y + this.y[i];
			int tx = x + this.x[i];
			if (chkindex(ty, tx)) {
				continue;
			} else if (game[ty][tx].mine) {
				total++;
			}
		}
		return total;
	}

	// 유효한 인덱스인지 체크
	private boolean chkindex(int ty, int tx) {
		if (ty < 0 || tx < 0 || ty > game.length - 1 || tx > game.length - 1) {
			return true;
		}
		return false;
	}

	// 선택한 위치인지 체크
	private boolean chk(int y, int x) {
		if (game[y][x].chk) {
			return false;
		}
		return true;
	}

	// 초기세팅입력
	private int earlyset() {
		Scanner sc = new Scanner(System.in);
		int arr[] = {5, 8, 10};
		System.out.println("난이도를 설정하세요 [1]easy [2]hard [3]extreme ");
		System.out.println("맵         크기:  5 x 5   8 x 8    10 x 10 ");
		return arr[getNumber(1, 3, "(1 ~ 3)난이도를 ", "난이도번호") - 1];
	}

	// 지뢰매설
	private void insertmine(int size) {
		Random rd = new Random();
		for (int i = 0; i < size - 2; i++) {
			int y = rd.nextInt(size);
			int x = rd.nextInt(size);
			if (game[y][x].mine) {
				i--;
			} else {
				game[y][x].mine = true;
				counter++;
			}
		}
		// 그 지역 근처에 지뢰갯수 세어서 저장시킴
		for (int i = 0; i < game.length; i++) {
			for (int k = 0; k < game[i].length; k++) {
				game[i][k].cnt = cnt(i, k);
			}
		}
		// 복사 2차원배열 생성 (체크 범위 뚫어줄 용도로 쓸거)
		setClone();
	}

	// 복사 2차원배열 생성 (체크 범위 뚫어줄 용도로 쓸거)
	private void setClone() {
		int size = game.length;
		clone = new int[size][size];
		for (int i = 0; i < clone.length; i++) {
			for (int k = 0; k < clone[i].length; k++) {
				if (game[i][k].cnt != 0 || game[i][k].mine) {
					clone[i][k] = 1;
				}
			}
		}
	}

	// 에러메세지 출력.
	private void err(String str) {
		System.err.println(str + "를 잘못입력하셨습니다.");
	}
}
