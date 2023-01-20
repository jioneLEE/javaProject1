package _03소코반_김성윤1;

import java.util.Scanner;

public class SkbController {
	Scanner s=new Scanner(System.in);
	SkbDAO sd=new SkbDAO();
	
	void run() {
		sd.setMap();
		double record=sd.game();
	}

}
