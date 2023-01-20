package _03윷놀이_윤준영;

import java.util.Scanner;

public class Controller {
	Player p;
	Scanner sc;
	int turn;

	void init() {
		sc = new Scanner(System.in);
		turn = 1;
	}

	void run() {
		while (true) {
			PrintData.printMap();
			YutDAO.checkYutCnt();
			while (true) {
				PrintData.printInfo(turn);
				int selMove = Util.getVal("이동할 번호를 고르세요", 1, YutDAO.getYutList().size()) - 1;

				if (PlayerDAO.movePlayer(p, selMove, turn)) {
					System.out.printf("p%d 승리!\n", turn);
					return;
				}

				if (YutDAO.getYutList().size() == 0) {
					break;
				}
				PrintData.printMap();

			}
			turn = PlayerDAO.changeTurn(turn);
		}
	}
}
