package _02Game1to50_강민기;

import java.util.Random;
import java.util.Scanner;

class Num {
	int num;
	boolean check;
}

class Game {
	Scanner scan = new Scanner(System.in);
	Random rd = new Random();
	int size = 25;
	Num[] front = new Num[size];
	Num[] back = new Num[size];
	int number = 1;
	int end = 0;

	void init() {
		int num1 = 1;
		for (int i = 0; i < front.length; i++) {
			front[i] = new Num();
			front[i].num = num1;
			num1 += 1;
		}

		System.out.println("앞면 끝번호 num" + num1);
		for (int i = 0; i < back.length; i++) {
			back[i] = new Num();
			back[i].num = num1;
			num1 += 1;
		}

		System.out.println("뒷면 끝번호 num" + num1);
	}

	void suffleCard() {
		for (int i = 0; i < front.length; i++) {
			int r = rd.nextInt(size);
			int temp = front[0].num;
			front[0].num = front[r].num;
			front[r].num = temp;

			r = rd.nextInt(size);
			temp = back[0].num;
			back[0].num = back[r].num;
			back[r].num = temp;
		}
	}

	void showCard() {
		int in = 0;
		for (int i = 0; i < front.length; i++) {
			System.out.print("[" + (i + 1) + "]");
			System.out.print(front[i].num + "\t");
			if (i % 5 == 4) {
				System.out.println();
				System.out.println();
			}
		}

	}

	void selectCard() {
		System.out.print("위치 입력 >> ");
		int idx = scan.nextInt() - 1;
		int cnt = 0;
		if (number == front[idx].num) {

			if (number <= 25) {
				front[idx] = back[number - 1];
			} else {
				front[idx].num = 0;
				front[idx].check = true;
			}
			number += 1;
		}
		for (int i = 0; i < front.length; i++) {
			if (front[i].check) {
				cnt++;
			}
		}
		if (cnt == 25) {
			System.out.println("[ 게임 종료 ]");
			end = -1;
		}
		if (number <= 50)
			System.err.println("맞출 숫자" + number);
	}

	void run() {
		int cnt = 0;
		init();
		suffleCard();
		long beforeTime = System.currentTimeMillis();
		while (true) {
			showCard();
			// System.out.println(cnt);
			selectCard();
			if (end == -1) {
				long afterTime = System.currentTimeMillis();
				double diffTime = (afterTime - beforeTime) / 1000.0;
				System.out.printf("기록 : %.2f 초 \n" , diffTime);
				
				return;
			}
		}
		
	}
}

public class Game1to50{

	public static void main(String[] args) {
		Game g = new Game();
		g.run();

	}

}
