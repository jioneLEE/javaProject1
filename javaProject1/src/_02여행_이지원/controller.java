package _02여행_이지원;

import java.util.Arrays;
import java.util.Scanner;

/*
(0북) (1동) (2남) (3서)
input의 데이터는 1은 한칸전진이고 2는 좌회전(90도) 3은 우회전(90도)이다.
 회전은 방향만 바뀌고 움직이는건 없다.
*/


class controller{
	tripDAO tr = new tripDAO();
	
	void run() {
		String[] dirMsg = {"북","동","남","서"};
		tr.init();
		int cnt =0;
		
		System.out.printf("=== Y위치[%d] X위치[%d] ====\n",tr.player.y , tr.player.x);
		tr.printMap();
		      //cnt != key.length
		while (true) {
			tr.key = tr.inputKey();
			VO_Node now = new VO_Node();
				now.y = tr.player.y;
				now.x = tr.player.x;
				if (tr.key == 0) {
					System.out.println("종료되었습니다.");
					break;
				} else if (tr.key == 1) {
					if (tr.dir == 0) { // 북
						now = tr.moveUp(now);
					} else if (tr.dir == 1) { // 동
						now = tr.moveRight(now);
					} else if (tr.dir == 2) { // 남
						now = tr.moveDown(now);
					} else if (tr.dir == 3) { // 서
						now = tr.moveLeft(now);
					}

				} else if (tr.key == 2) {
					if (tr.dir > 0 && tr.dir <= 3) {
						tr.dir--;
					} else if (tr.dir == 0) {
						tr.dir = 3;
					}
				} else if (tr.key == 3) {
					if (tr.dir < 3 && tr.dir >= 0) {
						tr.dir++;
					} else if (tr.dir == 3) {
						tr.dir = 0;
					}
			}
			
			
			System.out.printf("==[%d 턴]Y위치[%d] X위치[%d] 방향[%s]==\n",cnt+1,tr.player.y , tr.player.x,dirMsg[tr.dir]);
			if(tr.isRoad(now) && tr.checkWall(now)) {
				tr.updatePlayer(now); // 체크 완료한 플레이어 인덱스 변경 
			}
			tr.printMap();
			cnt++;
		}
		
	
	}
}
	

 

