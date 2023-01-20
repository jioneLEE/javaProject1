package _02여행_이지원;

import java.util.Arrays;
import java.util.Scanner;

/*
(0북) (1동) (2남) (3서)
input의 데이터는 1은 한칸전진이고 2는 좌회전(90도) 3은 우회전(90도)이다.
 회전은 방향만 바뀌고 움직이는건 없다.
*/


class tripDAO{
	Scanner sc = new Scanner(System.in);
	int[][] map;
//	int[] key;
	int key = -1;
	int dir;
	VO_Node player;
//	Node wall = new Node();
	void init() {
		player = new VO_Node();
		int[][] temp= {
				{0,0,0,0,0,0,0},	
				{0,7,7,7,7,0,0},	
				{0,7,0,0,7,0,0},	
				{0,7,0,8,7,0,0},	
				{0,0,0,0,7,0,0},	
				{0,0,0,0,7,7,0},	
				{0,0,0,0,0,0,0}
		};
		map = temp;
//		 int input[] = {1,1,2,1,1,2,1,1,3,1,1};
//		 key = input;
		 // 플레이어 위치 초기값 입력
		for(int i =0; i<map.length; i++) {
			for(int k =0; k<map[i].length; k++) {
				if(map[i][k] == 8) {
					player.y = i;
					player.x = k;
				}
			}
		}
	}
	
	VO_Node moveUp(VO_Node node) {
		node.y--;
		return node;
	}
	VO_Node moveLeft(VO_Node node) {
		node.x--;
		return node;
	}
	VO_Node moveDown(VO_Node node) {
		node.y++;
		return node;
	}
	VO_Node moveRight(VO_Node node) {
		node.x++;
	  return node;
	}
	
	
	void printMap() {
		for(int i =0; i<map.length; i++) {
			System.out.println(Arrays.toString(map[i]));
		}
	}
	
	boolean isRoad(VO_Node node) {
		if(node.x <0 || node.y <0 || node.x>= map.length ||  node.y>= map.length) {
			System.out.println("벽넘어 이동 할 수 없습니다");
			return false;
		}
		return true;
	}
	boolean checkWall(VO_Node node) {
		
		if(map[node.y][node.x] == 7) {
			System.out.println("이동 할 수 없습니다");
			return false;
		}
		return true;
	}
	
	void updatePlayer(VO_Node node) {
		map[player.y][player.x] = 0;
		player.x = node.x;
		player.y = node.y;
		map[player.y][player.x] = 8;
		
	}
	
	int inputKey() {
		System.out.printf("0)종료\n1)전진\n2)좌회전\n3)우회전\n");
		return sc.nextInt();
	}
	
		
	}
	
 

