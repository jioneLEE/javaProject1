package _02지뢰찾기_박일권;

public class Timer {
	
	long beforeTime = System.currentTimeMillis(); 
	long settime(int size){
		return beforeTime + (size * 10000 );
	}

}
