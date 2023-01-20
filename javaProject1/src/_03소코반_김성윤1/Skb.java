package _03소코반_김성윤1;

public class Skb {

	private int[][] map=new int[7][7];
	private int[] objInfo=new int[4];
	private int win=0;
	
	public int[][] getMap() {
		return map;
	}
	public void setMap(int[][] map) {
		this.map = map;
	}
	public int[] getObjInfo() {
		return objInfo;
	}
	public void setObjInfo(int[] objInfo) {
		this.objInfo = objInfo;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	
}
