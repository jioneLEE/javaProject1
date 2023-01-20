package _04테트리스_조영곤;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Tetris_controller {
	private blockDAO blockDAO;
	static private Tetris_controller instance = new Tetris_controller();

	static Tetris_controller getInstance() {
		return instance;
	}

	private boolean stop = false;

	private Tetris_controller() {

	}

	public boolean getStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	void init() {
		blockDAO = new blockDAO();
		int[][] map = blockDAO.getMap();
		map = new int[blockDAO.getSy()][blockDAO.getSx()];
		blockDAO.setMap(map);
	}

	void run() {
		init();
		while (true) {
			blockDAO.oneBlock();
			blockDAO.setX(4);
			blockDAO.setY(0);
			if (stop) {
				blockDAO.printBoard();
				System.out.println("GAME OVER!");
				break;
			}
			while (true) {
				blockDAO.printBoard();
				int sel = -1;
				try {
					sel = blockDAO.getSc().nextInt();
					if (sel < 0 || sel > 4) {
						throw new Exception("범위 오류!");
					}
				} catch (InputMismatchException e) {
					System.err.println("숫자만 입력하세요!");
					blockDAO.getSc().next();
					sel = -1;
				} catch (Exception e) {
					System.err.println(e.getMessage());
					sel = -1;
				}
				
				if (sel == 1) {
					blockDAO.moveLeft();

				} else if (sel == 2) {
					blockDAO.moveRight();

				} else if (sel == 3) {
					blockDAO.moveDown();
					blockDAO.clearLine();
					stop = blockDAO.gameover();
					break;
				} else if (sel == 4) {
					blockDAO.turn();
				} else if (sel == 0) {
					stop = true;
					break;
				}
				if (stop) {
					break;
				}
			}
			if (stop) {
				System.out.println("[GAME OVER!]");
				break;
			}
		}
	}
}
