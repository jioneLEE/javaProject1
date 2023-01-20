package _03윷놀이_윤준영;

public class PlayerDAO {
	static Player p1 = new Player(PrintData.MAPEND, PrintData.MAPEND, 0, 1);
	static Player p2 = new Player(PrintData.MAPEND, PrintData.MAPEND, 0, 5);

	// turn 변경
	static int changeTurn(int turn) {
		if (turn == 1)
			return ++turn;
		return --turn;
	}

	// turn 값에 따라 p1, p2 반환
	private static Player getPlayerInfo(int turn) {
		return turn == 1 ? p1 : p2;
	}

	// Player 이동
	static boolean movePlayer(Player p, int selMove, int turn) {

		int move = YutDAO.getYutList().get(selMove).move;

		p = getPlayerInfo(turn);
		PrintData.map[p.y][p.x] = 0;
		moveAlgorithm(p, move);
		YutDAO.getYutList().remove(selMove);

		if (checkWin(p)) {
			return true;
		}

		if (PrintData.map[p.y][p.x] != 0) {
			catchPlayer(p, turn);

		}
		PrintData.map[p.y][p.x] += p.pNum;
		return false;

	}

	// 상대 Player 말을 잡았을때
	private static void catchPlayer(Player p, int turn) {
		System.out.println();
		System.out.println("[잡았습니다! 한번 더 던집니다]");
		System.out.println();
		if (turn == 1) {
			p2 = new Player(PrintData.MAPEND, PrintData.MAPEND, 0, 5);
		} else {
			p1 = new Player(PrintData.MAPEND, PrintData.MAPEND, 0, 1);
		}
		PrintData.map[p.y][p.x] += p.pNum;
		YutDAO.checkYutCnt();
		PrintData.map[p.y][p.x] = 0;
	}

	// 승리조건
	private static boolean checkWin(Player p) {
		return p.x > PrintData.MAPEND && (p.dir == 3 || p.dir == 5) ? true : false;
	}

	/*
	 * dir = 0 ↑ dir = 1 ← dir = 2 ↓ dir = 3 → dir = 4 ↙ dir = 5 ↘
	 */

	private static Player moveAlgorithm(Player p, int move) {
		if (move == -1) {
			if (p.dir == 0) {
				if (p.y == PrintData.MAPEND) {
					p.x--;
					p.dir = 3;
				} else {
					p.y++;
					if (p.y == PrintData.MAPEND) {
						p.dir = 3;
					}
					if (p.y == PrintData.MAPEND / 2) {
						p.y++;
					}
				}
			} else if (p.dir == 1) {
				p.x++;
				if (p.x == PrintData.MAPEND) {
					p.dir = 4;
				}
			} else if (p.dir == 2) {
				p.y--;
				if (p.y == PrintData.MAPEND / 2) {
					p.y--;
				}
				if (p.y == 0) {
					p.dir = 5;
				}
			} else if (p.dir == 3) {
				if (p.x == 0) {
					p.y--;
				} else {
					p.x--;
				}
			} else if (p.dir == 4) {
				if (p.y == 0) {
					p.y++;
				} else {
					p.x++;
					p.y--;
					if (p.y == PrintData.MAPEND / 2 && p.x == PrintData.MAPEND / 2) {
						p.dir = 5;
					}
				}
			} else if (p.dir == 5) {
				if (p.x == 0) {
					p.x++;
					p.dir = 1;
				} else {
					p.x--;
					p.y--;
				}
			}
			return p;
		}

		while (move != 0) {
			if (p.dir == 0) {
				p.y--;
				if (p.y == PrintData.MAPEND / 2) {
					p.y--;
				}
				if (p.y == 0) {
					p.dir = 1;
					if (move == 1) {
						p.dir = 4;
					}
				}
			} else if (p.dir == 1) {
				p.x--;
				if (p.x == 0) {
					p.dir = 2;
					if (move == 1) {
						p.dir = 5;
					}
				}
			} else if (p.dir == 2) {
				p.y++;
				if (p.y == PrintData.MAPEND / 2) {
					p.y++;
				}
				if (p.y == PrintData.MAPEND) {
					p.dir = 3;
				}
			} else if (p.dir == 3) {
				p.x++;
				if (p.x > PrintData.MAPEND) {
					break;
				}
			} else if (p.dir == 4) {
				p.y++;
				p.x--;
				if (p.y == PrintData.MAPEND / 2 && p.x == PrintData.MAPEND / 2 && move == 1) {
					p.dir = 5;
				}
				if (p.y == PrintData.MAPEND) {
					p.dir = 3;
				}
			} else if (p.dir == 5) {
				p.y++;
				p.x++;
				if (p.x > PrintData.MAPEND) {
					break;
				}
			}
			move--;
		}
		return p;
	}

}
