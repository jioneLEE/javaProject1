package _03소코반2_김성윤2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

	static private Scanner sc = new Scanner(System.in);
	
	static String getValue(String msg) {
		System.out.print(msg+": ");
		String val=null;
		try {
			val = sc.next();
		} 
		catch(Exception e) {
			System.out.println("입력 오류.");
		}
		sc.nextLine();
		return val;
	}
	
	static int getValue(String msg, int min, int max) {
		System.out.printf("[%d ~ %d] ", min, max);
		System.out.print(msg+": ");
		int num=-1;
		try {
			num=sc.nextInt();
			if(num<min || num>max) {
				throw new Exception();
			}
		} 
		catch(InputMismatchException e) {
			System.err.println("입력 오류.");
		} 
		catch(Exception e) {
			num=-1;
			System.err.println("입력범위 에러.");
		}
		sc.nextLine();
		return num;
	}
	
}
