package _03소코반2_김성윤2;

public class Game {

	private int userNum=UserDAO.getNum();
	private int[][] savedGame;
	private double bestRecord;
	private int rank;
	
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int[][] getSavedGame() {
		return savedGame;
	}
	public void setSavedGame(int[][] savedGame) {
		this.savedGame = savedGame;
	}
	public double getBestRecord() {
		return bestRecord;
	}
	public void setBestRecord(double bestRecord) {
		this.bestRecord = bestRecord;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
}
