package _02지뢰찾기_test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Saveload {

	//       0         1         2         3         4
	// 0 {0,0,0,1},{0,1,0,1},{0,1,0,1},{0,1,0,1},{0,1,0,1},
	// 1 {0,0,0,1},{0,1,0,1},{0,1,0,1},{1,0,0,1},{0,1,0,1},
	// 2 {0,0,0,1},{0,1,0,3},{0,1,0,2},{0,1,0,1},{0,1,0,1},
	// 3 {0,1,0,1},{0,0,0,1},{0,1,0,1},{0,1,0,1},{0,1,0,1},
	// 4 {0,0,0,1},{0,1,0,1},{0,1,0,0},{0,1,0,1},{0,1,0,1},

	// 39s\n
	// 0001/1010/1110/0101/0111\n
	// 0001/1010/1110/0101/0111\n
	// 0001/1010/1110/0101/0111\n
	// 0001/1010/1110/0101/0111\n

	void saveGame(int size, long time) {
		int arr[][][] = new int[size][size][4];

		for (int i = 0; i < arr.length; i++) {
			for (int k = 0; k < arr[i].length; k++) {
				arr[i][k][0] = mineDAO.game[i][k].mine == true ? 1 : 0;
				arr[i][k][1] = mineDAO.game[i][k].flag == true ? 1 : 0;
				arr[i][k][2] = mineDAO.game[i][k].chk == true ? 1 : 0;
				arr[i][k][3] = mineDAO.game[i][k].cnt;

			}
		}

		String fileName = "src/_02지뢰찾기_test/mine.txt";
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			fw.write(mineDAO.game.length+ "\n");
			fw.write(mineDAO.counter+ "\n");
			fw.write(mineDAO.unlock + "\n");
			fw.write(time + "\n");
			for (int i = 0; i < arr.length; i++) {
				for (int k = 0; k < arr[i].length; k++) {
					for (int j = 0; j < arr[i][j].length; j++) {
						fw.write(arr[i][k][j] + "");
					}
					fw.write("/");
				}
				fw.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("파일" + fileName + " 생성 완료");
		}
	}

	long time;
	void loadGame() {
		String fileName = "src/_02지뢰찾기_test/mine.txt";
		FileReader fr = null;
		String data = "";

		try {
			fr = new FileReader(fileName);
			int read = 0;

			while (read != -1) {

				read = fr.read();
				data += (char) read;
			}

		} catch (FileNotFoundException e) { // 파일이 존재하지 않으면 에러발생
			System.err.println("파일이 존재하지 않습니다");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String arr[] = data.split("\n");
		mineDAO.gameSize = Integer.parseInt(arr[0]);
		mineDAO.counter = Integer.parseInt(arr[1]);
		mineDAO.unlock = Integer.parseInt(arr[2]);
		int size = mineDAO.gameSize;
		int tarr[][][] = new int[size][size][4];

		time = Integer.parseInt(arr[3]);
		String darr[][] = new String[size][];

		for (int i = 4; i < arr.length - 1; i++) {
			for (int k = 0; k < darr.length; k++) {
				darr[i - 4] = arr[i].split("/");
			}
		}

		for (int i = 0; i < darr.length; i++) {
			for (int k = 0; k < darr[i].length; k++) {
				for (int j = 0; j < darr[i][k].length(); j++) {
					tarr[i][k][j] = darr[i][k].charAt(j) - '0';
				}
			}
		}

		mapping(tarr, size);

	}

	private void mapping(int arr[][][], int size) {
		mineDAO.game = new Map[size][size];

		for (int i = 0; i < arr.length; i++) {
			for (int k = 0; k < arr[i].length; k++) {
				mineDAO.game[i][k] = new Map();
				mineDAO.game[i][k].mine = arr[i][k][0] == 1 ? true : false;
				mineDAO.game[i][k].flag = arr[i][k][1] == 1 ? true : false;
				mineDAO.game[i][k].chk = arr[i][k][2] == 1 ? true : false;
				mineDAO.game[i][k].cnt = arr[i][k][3];
			}
		}
	}

	long caltime(int size) {
		long afterTime = System.currentTimeMillis();
		loadGame();
		long calctime = afterTime + time;
		return calctime;
	}
}
