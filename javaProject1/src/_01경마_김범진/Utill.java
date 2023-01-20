package _01경마_김범진;

import java.util.Scanner;

public class Utill {

	static int getValue(int a, int b) {
		Scanner sc = new Scanner(System.in);
		System.out.println(" 메뉴 선택 : ");
		int val = 0;
		try {
			val = sc.nextInt();
			if (val < a || val > b) {
				throw new Exception();
			}

		} catch (Exception e) {
			System.err.println(a + " ~ " + b + " 사이 값 입력");
			val = 0;
		}
		return val;
	}

}
