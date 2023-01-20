package _03윷놀이_윤준영;

public class PrintData {
	static final int MAPSIZE = 7;
	static final int MAPEND = MAPSIZE - 1;
	static int[][] map = new int[PrintData.MAPSIZE][PrintData.MAPSIZE];
	static int turn;

	static void printMap() {
		System.out.println("====================");
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				if (map[i][j] == 0) {
					if (i == 3 && j == 3) {
						System.out.print("[ ]");
					} else if ((i == 0 && (j == 0 || j == MAPEND)) || (i == MAPEND && (j == 0 || j == MAPEND))) {
						System.out.print(" @ ");
					} else if ((i == 0 || i == MAPEND || j == 0 || j == MAPEND || i == j || i == MAPEND - j)
							&& i != 3) {
						System.out.print(" O ");
					} else {
						System.out.print("   ");
					}
				} else if (map[i][j] == 1) {
					System.out.printf(" 옷 ");
				} else if (map[i][j] == 5) {
					System.out.printf(" 홋 ");
				}
			}
			System.out.println();
			System.out.println();
		}
		System.out.println("====================");
	}

	static void printYut(int[] arr, int backdo) {
		for (int i = 0; i < arr.length; i++) {
			if (i == backdo && arr[backdo] == 1) {
				System.out.print("[■]");
			} else if (arr[i] == 0) {
				System.out.print("[X]");
			} else if (arr[i] == 1) {
				System.out.print("[□]");
			}
		}
		System.out.println();
	}
	
	static void printYutList() {
		int i = 0;
		for (Yut y : YutDAO.getYutList()) {
			printYut(y.yut, y.backdoIdx);
			System.out.printf("%d) " + y.val + "\n", i + 1);
			i++;
		}
	}

	static void printInfo(int turn) {
		System.out.println("[P1 → 옷 || P2 → 홋]");
		System.out.printf("――――― p%d turn ―――――\n", turn);
		printYutList();
		System.out.println("―――――――――――――――――――");
	}

}
