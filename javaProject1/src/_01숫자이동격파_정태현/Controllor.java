package _01숫자이동격파_정태현;

import java.util.Scanner;

public class Controllor {
	Game game;
	Controllor(){
		game = new Game();
		game.init();
		
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		game.findWall();
		while (true) {
			game.print();
			System.out.println("어느 방향으로 이동하시겠습니까 ?");
			System.out.println("1. 좌 2. 우 3. 상 4. 하");
			int sel = sc.nextInt();
			if (sel < 1 || sel > 4) {
				System.out.println("입력 오류 1 - 4 중 선택");
				continue;
			}
			else if (sel == 1) {
				game.moveLeft();
			}
			else if (sel == 2) {
				game.moveRight();
			}
			else if (sel == 3) {
				game.moveUp();
			}
			else if (sel == 4) {
				game.moveDown();
			}

		}
		
	}
}
