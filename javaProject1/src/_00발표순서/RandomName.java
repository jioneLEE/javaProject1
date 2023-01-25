package _00발표순서;

import java.util.Arrays;
import java.util.Random;

public class RandomName {

	// 프로젝트 발표시 녹화기록 : alt + r
	// 프로젝트 발표시 녹화중단 : alt + p
	public static void main(String[] args) {

		Random rd = new Random();
		String[] names = {"강민기" ,"박일권","이지원", "김범진"};
		for(int i =0; i < 1000;i++) {
			int rNum = rd.nextInt(names.length);
			String temp = names[0];
			names[0] = names[rNum];
			names[rNum] = temp;
		}
		
		System.out.println(Arrays.toString(names));
		
		System.out.println("test");
		System.out.println("test");
		System.out.println("test");
		System.out.println("test");
	
		
	}

}
