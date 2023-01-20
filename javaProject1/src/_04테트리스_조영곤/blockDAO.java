package _04테트리스_조영곤;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class blockDAO {
	private int map[][];
	private Scanner sc = new Scanner(System.in);
	private Random rd = new Random();
	private block list = new block();
	private Tetris_controller tc =Tetris_controller.getInstance();
	private int[][] block;
	private int size;
	private int sy = 20;
	private int sx = 10;
	private int x = 4;
	private int y = 0;

	public int[][] getMap() {
		return map;
	}

	public int[][] setMap(int[][] map) {
		return this.map = map;
	}

	public Scanner getSc() {
		return sc;
	}

	public Random getRd() {
		return rd;
	}

	public block getList() {
		return list;
	}

	public void setList(block list) {
		this.list = list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSy() {
		return sy;
	}

	public int getSx() {
		return sx;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	void printBoard() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					System.out.print("[□]");
//				if(map[i][j] == 0) {
//					System.out.print("[ ]");
//				} else if(map[i][j]<9){
//					System.out.print("["+map[i][j]+"]");
				}else {
					System.out.print("[▦]");
				}
			}
			System.out.println();
		}
		System.out.println("-------------------------------");
		System.out.println("[1.left][2.right][3.down][4.turn][0.off]");
	}

	void oneBlock() {
		int x = rd.nextInt(list.bList.length);
		block = list.pickBlock(x);
		size = list.pickBlock(x).length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][4 + j] != 0 && list.pickBlock(x)[i][j] != 0) {
					System.err.println("NO");
					tc.setStop(true);
					break;
				}
				if (list.pickBlock(x)[i][j] != 0) {
					map[i][4 + j] = list.pickBlock(x)[i][j];
				}
			}
			if (tc.getStop()) {
				break;
			}
		}
	}
	
	boolean checkNum(int i, int j) {
		if(map[i][j]>0 && map[i][j]<9) {
			return true;
		}
		return false;
	}
	
	boolean checkLeft() {
		for(int i = size; i>=0; i--) {
			if(map[i][0] != 0) {
				return false;
			}
		}
		return true;
	}
	
	boolean checkLeft9() {
		for(int i = size ; i>=0; i--) {
			for(int j = 0; j<sx; j++) {
				if(checkNum(i, j)) {
					if(j-1<0) continue;
					if(map[i][j-1] == 9) {
						return false;
					}
				}
			}
		}
		return true;
	}

	void moveLeft() {
		boolean move = true;
		if(!checkLeft()) return;
		if(!checkLeft9()) {
			tc.setStop(true);
			return;
		}
		for(int i = size ; i>=0; i--) {
			for(int j = 0; j<sx; j++) {
				
				if(checkNum(i, j)) {
					if(j-1<0) {
						move =false;
						break;
					}
					map[i][j-1] = map[i][j];
					map[i][j] = 0;
				}
			}
			if(!move) break;
		}
		if(move) x--;
	}

	void moveRight() { 
		boolean move = true;
		if(!checkRight()) return;
		if(!checkRight9()) return;
		for(int i = size ; i>=0; i--) {
			for(int j = sx-1; j>=0; j--) {
				
				if(checkNum(i, j)) {
					if(j+1>sx-1) {
						move = false;
						break;
					}
					map[i][j+1] = map[i][j];
					map[i][j] = 0;
				}
			}
			if(!move) break;
		}
		if(move) x++;
	}
	
	boolean checkRight() {
		for(int i = size; i>=0; i--) {
			if(map[i][sx-1] != 0) {
				return false;
			}
		}
		return true;
	}
	
	boolean checkRight9() {
		for(int i = size ; i>=0; i--) {
			for(int j = sx-1; j>=0; j--) {
				if(checkNum(i, j)) {
					if(map[i][j+1] == 9) {
						System.out.println("hi");
						tc.setStop(true);
						return false;
					}
				}
			}
		}
		return true;
	}

	void moveDown() {
		int hy = 0;
		int hx = sx;
		int hh = 0;
		for(int i = 0; i<size ; i++) {
			for(int j = sx-1; j>=0; j--) {
				if(map[i][j]!= 0 && map[i][j]!=9) {
					if(hx>j) {
						hx = j;
					}
					if(hy<i) {
						hy = i;
						hh = i;
					}
				}
			}
		}
		while(checkDown(hy,hx,hh)) {
			for(int i = hy; i>=0; i--) {
				for(int j = hx; j<hx+size; j++) {
					if(j>sx-1) break;
					if(checkNum(i,j)) {
						map[i+1][j] = map[i][j];
						map[i][j]= 0;
					}
				}
			}
			hy++;
			if(hy == sy-1 || !checkDown(hy,hx,hh)) {
				for(int i = 0; i<sy; i++) {
					for(int j = 0; j<sx; j++) {
						if(map[i][j]!=0) {
							map[i][j] = 9;
						}
					}
				}
				break;
			}
		}
		
	}
	
	boolean checkDown(int hy, int hx,int hh) {
		for(int i = hy; i>=hy-hh; i--) {
			for(int j = hx; j<hx+size; j++) {
				if(j>sx-1) break;
				if(checkNum(i,j) && map[i+1][j]==9) {
					return false;
				}
			}
		}
		return true;
	}
	
	boolean checkTurn() {
		
		return true;
	}

	void turn() {
		int blocknum = 0;
		int[][] copy = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[j][size - 1 - i] = block[i][j];
				if (block[i][j] != 0) {
					blocknum = block[i][j];
				}
			}
		}
		block = copy;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (x + j < 0) {
					moveRight();
					if(blocknum == 7) {
						moveRight();
						moveRight();
					}
				}
				if(x+j>sx-1) {
					moveLeft();
					
				}
				if (map[i][x + j] !=9) {
					map[i][x + j] = 0;
				} else if (map[i][x + j] > 0 && map[i][x + j] == 9) {
					tc.setStop(true);
					break;
				}
				if (tc.getStop()) {
					break;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (block[i][j] == 9) {
					tc.setStop(true);
					break;
				}
				if (block[i][j] != 0) {
					map[i][x + j] = block[i][j];
				}
			}
			if(tc.getStop()) break;
		}
	}

	void clearLine() {
		boolean checkline = true;
		while (checkline) {
			for (int i = 19; i >= 0; i--) {
				int cnt = 0;
				for (int j = 0; j < sx; j++) {
					if (map[i][j] != 0) {
						cnt++;
					}
				}
				if (cnt == sx) {
					for (int j = i; j > 0; j--) {
						for (int k = 0; k < sx; k++) {
							map[j][k] = map[j - 1][k];

						}
					}
					i++;
				}
				if (cnt == 0 || i == 1) {
					checkline = false;
					break;
				}

			}
		}
	}

	boolean gameover() {
		for (int i = 0; i < sx; i++) {
			if (map[0][i] != 0) {
				return true;
			}
		}
		return false;
	}

}
