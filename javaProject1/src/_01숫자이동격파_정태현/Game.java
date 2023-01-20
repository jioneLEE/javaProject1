package _01숫자이동격파_정태현;

import java.util.Arrays;
import java.util.Scanner;

class Game {
	int[][] game;
	Node player;
	Node[] walls;
	Scanner sc;

	void init() {
		int[][] temp = { { 0, 0, 1, 0, 0, 0, 0, 1, 0 },
						 { 0, 0, 1, 0, 2, 0, 0, 1, 0 }, 
						 { 0, 0, 1, 0, 0, 0, 0, 1, 0 } };
		game = temp;
		player = new Node();
		player.y = 1;
		player.x = 4;
		walls = new Node[6];

		sc = new Scanner(System.in);

		for (int i = 0; i < walls.length; i++) {
			walls[i] = new Node();
		}
//		for(int i = 0; i < walls.length; i++) {
//			walls[i] = new Node();
//		}

	}

	void print() {
		
//		for(int i = 0; i < game.length; i++) {
//			System.out.println(Arrays.toString(game[i]));
//		}
		for (int i = game.length - 1; i >= 0; i--) {
			System.out.println(Arrays.toString(game[i]));
		}
	}

	void findWall() {
		// wall 찾아서 리스트에 넣기
		int count = 0;
		for (int i = 0; i < game.length; i++) {
			for (int k = 0; k < game[i].length; k++) {
				if (game[i][k] == 1) {
					walls[count].y = i;
					walls[count].x = k;
					count++;
//					System.out.print("x = "+walls[cnt].x+" ");
//					System.out.println("y = "+walls[cnt].y);
				}
			}
		}
	}

	void moveLeft() {
		// 플레이어 위치 좌로 이동

		if (player.x != 0) {
			if (!breakWall(-1)) {
				player.x++;
			} else {
				game[player.y][player.x] = 0;
				player.x--;
				game[player.y][player.x] = 2;
			}

		}else if(player.x <= 0 ) {
			game[player.y][player.x] = 0;
			player.x = game[player.y].length-1;
			game[player.y][player.x] = 2;		
		}

	}

	void moveRight() {
		// 플레이어 위치 우 로 이동
		if (player.x != game[0].length - 1) {
			if (!breakWall(+1)) {
				player.x--;
			} else {
				game[player.y][player.x] = 0;
				player.x++;
				game[player.y][player.x] = 2;
			}
		}else if(player.x >= game[0].length-1 ) {
			game[player.y][player.x] = 0;
			player.x = 0;
			game[player.y][player.x] = 2;		
		}
	}


	void moveUp() {
		// 좌, 우 끝으로 이동하면 올라갈 수 있도록
		if (player.y != game.length - 1) {
			game[player.y][player.x] = 0;
			player.y++;
			game[player.y][player.x] = 2;
		} else {
			System.out.println("더 이상 올라갈 수 없습니다.");
		}
	}

	void moveDown() {
		// 좌, 우 끝으로 이동하면 내려갈 수 있도록
		if (player.y != 0) {
			game[player.y][player.x] = 0;
			player.y--;
			game[player.y][player.x] = 2;
		} else {
			System.out.println("더 이상 내려갈 수 없습니다.");
		}
	}

	boolean breakWall(int cnt) {
		// x , y가 벽이 있는 x , y와 같을때 if문을 통해서 벽을 부술지 말지 결정

		try {
			
			for (int i = 0; i < walls.length; i++) {

				if (walls[i].x == player.x+cnt && walls[i].y == player.y && game[player.y][player.x+cnt] == 1) {

//					System.out.println("walls = " + walls[player.y].x);
//					System.out.println("game = " + game[player.y][player.x]);
					int log = -1;
					while (log == -1) {
						System.out.println("[ 앞에 벽이 있습니다.");
						System.out.println("벽을 부수시겠습니까 ?");
						System.out.println("1. 예 2. 아니오");
						int sel = sc.nextInt();
						if (sel == 1) {
							log = 1;
							return true;
						}
						if (sel == 2) {
							System.out.println("현재 위치를 유지합니다.");
							log = 1;
							return false;
						} else {
							System.out.println("1 - 2 값 입력");
							continue;
						}
						
					}
					
				}else {
//					System.out.println("walls[i].x = " +walls[i].x);
//					System.out.println("x = "+ player.x);
//					System.out.println("walls[i].y = " + walls[i].y);
//					System.out.println("y = "+ player.y);
//					System.out.println("game = " + game[player.y][player.x+cnt]);
//					System.out.println(1);
//					
				}
				
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	void run() {
		init();
		findWall();
		while (true) {
			print();
			System.out.println("어느 방향으로 이동하시겠습니까 ?");
			System.out.println("1. 좌 2. 우");
			int sel = sc.nextInt();
			if (sel < 1 || sel > 2) {
				System.out.println("입력 오류 1 - 2 중 선택");
				continue;
			}
			if (sel == 1) {
				moveLeft();
			}
			if (sel == 2) {
				moveRight();
			}

		}
		
	}
	
}