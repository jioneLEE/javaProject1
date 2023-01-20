package _03윷놀이_윤준영;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class YutDAO {
	private static ArrayList<Yut> yList = new ArrayList<>();
	static Random rd;

	static ArrayList<Yut> getYutList() {
		return yList;
	}

	// 윷 던지기
	static void throwYut() {
		rd = new Random();
		int[] arr = new int[4];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = rd.nextInt(2);
		}
		int ran = rd.nextInt(4);
		int cnt = checkYut(arr, ran);
		yList.add(new Yut(arr, checkMove(cnt), yutMsg(checkMove(cnt)), ran));
	}

	// 뒤집어진 윷 갯수 체크
	static int checkYut(int[] arr, int ran) {
		int cnt = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 1) {
				cnt++;
			}
		}
		if (cnt == 1 && arr[ran] == 1) {
			return -1;
		}
		return cnt;
	}

	// 뒤집어진 윷 갯수로 이동값 체크
	static int checkMove(int cnt) {
		switch (cnt) {
		case -1:
			return -1;
		case 0:
			return 5;
		case 1:
			return 1;
		case 2:
			return 2;
		case 3:
			return 3;
		case 4:
			return 4;
		default:
			return 0;
		}
	}

	// 이동값 확인을 통해 메세지 출력
	static String yutMsg(int move) {
		switch (move) {
		case -1:
			return "뒷도";
		case 1:
			return "도";
		case 2:
			return "개";
		case 3:
			return "걸";
		case 4:
			return "윷";
		case 5:
			return "모";
		default:
			return null;
		}
	}

	// 윷이나 모가 나올경우 다시 던지기
	static void checkYutCnt() {
		while (true) {
			throwYut();
			if (yList.get(yList.size() - 1).move != 4 && yList.get(yList.size() - 1).move != 5) {
				break;
			}
		}
	}

}
