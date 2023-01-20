package _01크레이지아케이드_노상권;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class CrazyArcade {
	private final int ROAD;
	private final int WALL;
	private final int RANGEITEM;
	private final int PLAYER;
	private final int BOMBITEM;
	private final int BOMB;
	private final int BOOM;
	private final int ERROR;
	private final int PASS;
	private final int SIZE;
	private int[][] map;
	private int setEnableBomb;
	private ArrayList<Integer>[] setBOMB;
	private int BOMBRange;
	private int BOMBCnt;
	private int remainsWALL;
	private int getItem;
	private int pY;
	private int pX;
	private int[] selMoveY;
	private int[] selMoveX;
	private boolean gameOver;

	CrazyArcade(int SIZE) {
		this.SIZE = SIZE;
		ROAD = 0;
		RANGEITEM = 1;
		BOMBITEM = 2;
		WALL = 3;
		PLAYER = 8;
		BOMB = 4;
		BOOM = 5;
		ERROR = -1;
		PASS = 7;
		setEnableBomb = 1;
		BOMBRange = 1;
		setBOMB = new ArrayList[2];
		setBOMB[0] = new ArrayList<>();
		setBOMB[1] = new ArrayList<>();

		int[] selMoveY = { -1, 1, 0, 0 };
		int[] selMoveX = { 0, 0, -1, 1 };
		this.selMoveY = selMoveY;
		this.selMoveX = selMoveX;
		makeMap();
	}

	private void showMap(boolean blast) {
		if (!blast) {
			System.out.println("────────────────────────");
			System.out.println("     Crazy Arcade");
			System.out.println("────────────────────────");
			System.out.printf("\t[폭탄 범위 : %d ] \n", BOMBRange);
			System.out.println("────────────────────────");
			System.out.printf("◈ 설치가능 폭탄 개수 %d EA \n", setEnableBomb);
			System.out.printf("◈ 맵에있는 폭탄 개수 %d EA \n", BOMBCnt);
			System.out.printf("◈ 남아있는 벽돌 개수 %d EA \n", remainsWALL);
			System.out.printf("◈ 획득한 아이템 개수 %d EA \n", getItem);
			System.out.println("────────────────────────");
			System.out.println("◈ 설치된 폭탄의 위치");
			if (BOMBCnt == 0) {
				System.out.println("\t[없음]");
			} else {
				System.out.println("[위치]\t[y , x] ");
				for (int i = 0; i < setBOMB[0].size(); i++) {
					System.out.printf("\t[%d],[%d]\n", setBOMB[0].get(i), setBOMB[1].get(i));
				}
			}
			System.out.println("────────────────────────");
		}
		for (int[] map : map) {
			for (int m : map) {
				if (BOMBCnt != 0) {
					for (int i = 0; i < setBOMB[0].size(); i++) {
						int y = setBOMB[0].get(i);
						int x = setBOMB[1].get(i);
						if (this.map[y][x] != PLAYER && this.map[y][x] != BOOM) {
							this.map[y][x] = this.BOMB;
						}
					}
				}
				if (m == ROAD) {
					System.out.print("[ ]");
				} else if (m == RANGEITEM) {
					System.out.print("[+]");
				} else if (m == BOMBITEM) {
					System.out.print("[B]");
				} else if (m == WALL) {
					System.out.print("[■]");
				} else if (m == BOMB) {
					System.out.print("[＠]");
				} else if (m == BOOM) {
					System.out.print("[※]");
				} else {
					System.out.print("[옷]");
				}
			}
			System.out.println();
		}
		if (!blast) {
			showMenu();
		}

	}

	private void showMenu() {
		System.out.println("[1.↑][2.↓][3.←][4.→]");
		System.out.println("[5.설치][6.폭파]");
	}

	private void makeMap() {
		map = new int[SIZE][SIZE];
		Random rd = new Random();
		for (int i = 0; i < map.length; i++) {
			int rdIdx = rd.nextInt(map.length);
			for (int j = 0; j < map.length; j++) {
				if (j == rdIdx) {
					map[i][j] = ROAD;
					continue;
				}
				remainsWALL++;
				map[i][j] = WALL;

			}
		}
		int pY = rd.nextInt(map.length - 2) + 1;
		int pX = rd.nextInt(map.length - 2) + 1;
		map[pY][pX] = PLAYER;
		if (map[pY + 1][pX] == WALL) {
			remainsWALL--;
		}
		if (map[pY][pX + 1] == WALL) {
			remainsWALL--;
		}
		map[pY + 1][pX] = ROAD;
		map[pY][pX + 1] = ROAD;
		this.pY = pY;
		this.pX = pX;
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		while (true) {

			showMap(false);

			int sel = input();

			if (sel == 1) {
				sel = 0;
			} else if (sel == 2) {
				sel = 1;
			} else if (sel == 3) {
				sel = 2;
			} else if (sel == 4) {
				sel = 3;
			} else if (sel == 5) {
				sel = 4;
			} else if (sel == 6) {
				sel = 5;
			}
			if (sel == -1 || sel < 0 || sel > 5) {
				System.err.println("[ 입력 오류 ]");
				continue;
			}

			if (sel == 4) {
				setBOMB();
			} else if (sel == 5) {
				blastBOMB();
			} else {
				move(sel);
			}
			if (gameOver) {
				System.err.println("[ Game Over ]");
				return;
			}
			if (remainsWALL == 0) {
				System.out.println("[ CLEAR ]");
				return;
			}
		}
	}

	private void itemCheck() {
		if (map[pY][pX] == RANGEITEM) {
			getItem++;
			BOMBRange++;
		}
		if (map[pY][pX] == BOMBITEM) {
			getItem++;
			setEnableBomb++;
		}
	}

	private void move(int moveIdx) {
		int moveY = pY + selMoveY[moveIdx];
		int moveX = pX + selMoveX[moveIdx];
		int check = check(moveY, moveX);
		if (check == ERROR) {
			System.err.println("[ 게임 밖 ]");
			return;
		} else if (check == WALL) {
			System.err.println("[ 벽 ]");
			return;
		}
		map[pY][pX] = ROAD;
		pY = moveY;
		pX = moveX;
		itemCheck();
		map[pY][pX] = PLAYER;
	}

	private void setBOMB() {
		if (setEnableBomb == 0) {
			System.err.println("[ 설치할 폭탄 없음 ]");
			return;
		}
		boolean ERROR = false;
		for (int i = 0; i < setBOMB[0].size(); i++) {
			int y = setBOMB[0].get(i);
			int x = setBOMB[1].get(i);
			if (y == pY && x == pX) {
				ERROR = true;
				break;
			}
		}
		if (ERROR) {
			System.err.println("[ 이미 설치된 자리 ]");
			return;
		}
		setBOMB[0].add(pY);
		setBOMB[1].add(pX);

		BOMBCnt++;
		setEnableBomb--;
		System.out.println("[ 폭탄 설치 완료 ]");
	}

	private void blastBOMB() {
		if (setBOMB[0].size() == 0) {
			System.err.println("[ 폭파할 폭탄 없음 ]");
			return;
		}

		Random rd = new Random();
		int y = setBOMB[0].get(0);
		int x = setBOMB[1].get(0);
		int[][][] blastRange = new int[4][BOMBRange][2];
		int[][] makeItem = new int[4][2];

		for (int[] item : makeItem) {
			item[0] = -1;
			item[1] = -1;
		}
		for (int i = 0; i < blastRange.length; i++) {
			for (int j = 0; j < BOMBRange; j++) {
				blastRange[i][j][0] = selMoveY[i] * (j + 1) + y;
				blastRange[i][j][1] = selMoveX[i] * (j + 1) + x;
				int bY = blastRange[i][j][0];
				int bX = blastRange[i][j][1];
				int check = check(bY, bX);

				if (check == WALL) {
					map[bY][bX] = BOOM;
					remainsWALL--;
					makeItem[i][0] = bY;
					makeItem[i][1] = bX;
					break;
				}
				if (check == PLAYER) {
					gameOver = true;
				}
				if (check != ERROR) {
					map[bY][bX] = BOOM;
				}
			}
		}

		if (map[y][x] == PLAYER) {
			gameOver = true;
		}
		map[y][x] = BOOM;

		System.out.println("\n======= BOOM =======");
		showMap(true);
		System.out.println("======= BOOM =======\n");

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == BOOM) {
					map[i][j] = ROAD;
				}
			}
		}
		for (int[] item : makeItem) {
			if (item[0] != -1 && item[1] != -1) {
				map[item[0]][item[1]] = rd.nextInt(3);
			}
		}

		setBOMB[0].remove(0);
		setBOMB[1].remove(0);

		BOMBCnt--;
		setEnableBomb++;
	}

	private int input() {
		System.out.println("[ 입력 ]");
		Scanner sc = new Scanner(System.in);
		int sel = 0;
		try {
			sel = sc.nextInt();
		} catch (InputMismatchException e) {
			sel = -1;
		}
		return sel;
	}

	private int check(int y, int x) {

		try {

			if (y < 0 || x < 0 || y >= SIZE || x >= SIZE) {
				throw new ArrayIndexOutOfBoundsException();
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			return ERROR;
		}

		if (map[y][x] == WALL) {
			return WALL;
		}

		if (map[y][x] == PLAYER) {
			return PLAYER;
		}

		return PASS;
	}

}
