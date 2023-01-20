package _03윷놀이_윤준영;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {
	static Scanner sc = new Scanner(System.in);

	static int getVal(String msg, int start, int end) {
		int num = 0;
		while (true) {
			try {
				System.out.print(msg + " >> ");
				num = sc.nextInt();
				if (num < start || num > end) {
					throw new Exception(start + "-" + end + "사이값 입력");
				}
			} catch (InputMismatchException e) {
				System.err.println("숫자만 입력하세요");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			if (num >= start && num <= end) {
				break;
			}
			sc.nextLine();
		}
		return num;
	}
}