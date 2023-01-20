package _01경마_김범진;

import java.util.Scanner;

public class HorseControll {
	Scanner sc = new Scanner(System.in);
	HorseDAO hdao = new HorseDAO();
	Utill u;
	
	
	public void run() {

		while (true) {
			System.out.println("\n[ 경마 게임 ]");
			System.out.println("[1]실행 [2]순위 [3]저장 [4]불러오기 [5]종료");

			int sel = u.getValue(1, 5); 

			if (sel == 1) {
				hdao.game();
			} else if (sel == 2) {
				hdao.printRank();
			} else if (sel == 3) {
				hdao.save();
			} else if (sel == 4) {
				hdao.load();
			} else if (sel == 5) {
				break;
			}
		}
	}

}
