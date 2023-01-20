package _01경마_김범진;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
[경마 게임]
[1] 5 * 20 의 배열이 있다.
[2] 각 줄은 말이 달릴 트랙을 의미한다.
[3] 매 턴당 랜덤으로 1~4칸의 거리를 이동할수있다.
[4] 말이 전부 도착하면 게임은 끝난다. 각말들의 순위를 출력한다. 
[5] 단, 말이 동시에 도착하면 등수는 같다. 
*/
public class HorseDAO {

	int horse[][];
	Random rd = new Random();
	int r;

	int malmal[];
	int rank[] = new int[5];
	int ranking;
	boolean flag; // 동시에 도착한 말의 등수 판단을 위함.
	int endGame;

	FileWriter fw = null;
	String fileName = "src/경마_김범진/test.txt";
	FileReader fr = null;
	String data;

	void init() {
		horse = new int[5][20];
		r = 0;
		malmal = new int[5];
		rank = new int[5];
		ranking = 0;
		flag = true;
		endGame = 0;
	}

	public void game() {
		// TODO Auto-generated method stub
		init();
		while (true) {
			if (flag) {
				ranking += 1;
			}
			flag = false;

			for (int i = 0; i < horse.length; i++) {
				r = rd.nextInt(4) + 1;
				// System.out.println(r);
				malmal[i] += r;

				// System.out.println("합 : " + malmal[i]);

				// 배열안에 존재할때.
				if (malmal[i] < 20) {
					// 현위치 표시
					horse[i][malmal[i]] = 9;
					// 전위치 삭제
					if (malmal[i] - r > -1) {
						horse[i][malmal[i] - r] = 0;
					}
					// rank 부여
					if (malmal[i] == 19 && rank[i] == 0) {
						rank[i] = ranking;
						flag = true;
					}
					// 현재 배열을 넘어갈때.
				} else {
					if (malmal[i] - r < 20) {
						// 전위치 삭제
						horse[i][malmal[i] - r] = 0;
						// 현위치 표시
						horse[i][19] = 9;
					}
					// rank 부여
					if (rank[i] == 0) {
						rank[i] = ranking;
						flag = true;
					}
				}

				// 한게임당 결과 출력
				horse[i][0] = i + 1;
				for (int j = 0; j < horse[i].length; j++) {
					if (horse[i][j] == 0) {
						System.out.print("[ ]");
					} else if (horse[i][j] == 9) {
						System.out.print("[■]");
					} else {
						System.out.print("[" + horse[i][j] + "]");
					}
				}
				System.out.println();
			}
			// printGame();
			System.out.println("============================================================");

			// rank가 다 부여되었다면
			for (int j = 0; j < rank.length; ++j) {
				if (rank[j] > 0) {
					++endGame;
				}
			}
			// 게임 종료
			if (endGame == rank.length) {
				break;
				// 반복문 체크 초기화를 위함.
			} else {
				endGame = 0;
			}
		}
	}

	void printGame() {
		for (int i = 0; i < horse.length; i++) {
			horse[i][0] = i + 1;
			for (int j = 0; j < horse[i].length; j++) {
				if (horse[i][j] == 0) {
					System.out.print("[ ]");
				} else if (horse[i][j] == 9) {
					System.out.print("[■]");
				} else {
					System.out.print("[" + horse[i][j] + "]");
				}
			}
			System.out.println();
		}
	}

	public void printRank() {
		for (int i = 0; i < rank.length; ++i) {
			System.out.println(i + 1 + "번 마 : " + rank[i] + "등");
		}
	}

	public void save() {
		data = "";
		for (int i = 0; i < rank.length; ++i) {
			data += i + 1 + "번 마 : " + rank[i] + "등\n";
		}

		try {
			fw = new FileWriter(fileName);
			fw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("파일" + fileName + " 생성");
		}
	}

	public void load() {
		data = "";
		try {
			fr = new FileReader(fileName);
			int read = 0;
			while (true) {
				read = fr.read();
				if (read != -1) {
					data += (char) read;
				} else {
					break;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

		String[] data2 = data.split("등\n");
		rank = new int[data2.length];
		for (int i = 0; i < data2.length; i++) {
			rank[i] = Integer.parseInt(data2[i].substring(7));
		}
		System.out.println("불러오기 완료");
	}
}
