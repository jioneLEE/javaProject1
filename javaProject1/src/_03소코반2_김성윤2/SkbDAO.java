package _03소코반2_김성윤2;

import java.util.ArrayList;
import java.util.Random;

public class SkbDAO {

	Skb el;
	int win;
	View v;
	int[][] map;
	SkbDAO(){
		el=new Skb();
		win=el.getWin();
		v=new View();
		map = el.getMap();
	}
	void clearMap(int sel2) {
		win=0;
		GameDAO.gameList = new ArrayList<Game>();
		el.setMap(new int[7][7]);
		Game g=new Game();
		g.setUserNum(sel2);
		GameDAO.gameList.add(g);
		map = el.getMap();
	}
	
	void setObjects( int obj, int ck) {

		Random r=new Random();
		int a=0;
		int b=0;
		int inputw=-1;
		if (ck==0) {
			while(inputw==-1) {
				inputw=Input.getValue("설치할 벽의 갯수를 입력하다. ", 1, ((map.length)*(map.length))/4);
			}
		}
		while(true) {
			if(obj==3) {
				a=r.nextInt(map.length-2)+1;
				b=r.nextInt(map.length-2)+1;
			}
			else {
				a=r.nextInt(map.length);
				b=r.nextInt(map.length);
			}
			if(map[a][b]!=0) {
				continue;
			}
			map[a][b]=obj; 
			
			if(obj==9) {
				inputw--;
				if(inputw==0) break;
			}
			else break;
		}
		el.setMap(map);
	}
	
	void setMap() {
		int ck=0;
		setObjects( 9, ck);
		ck=1;
		setObjects(3, ck);
		setObjects( 7, ck);
		setObjects( 2, ck);
		
	}
	
	void showMap() {
		for(int[] i:el.getMap()) {
			for(int j:i) {
				if(j==0) System.out.print("[ ]");
				if(j==2) System.out.print("[옷]");
				if(j==3) System.out.print("[o]");
				if(j==7) System.out.print("[=]");
				if(j==9) System.out.print("[■]");
			}System.out.println();
		}
	}
	
	int[] findCharAndBall(int[] info) {
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if(map[i][j]==2) {info[0]=i; info[1]=j;}
				if(map[i][j]==3) {info[2]=i; info[3]=j;}
			}
		}
		return info;
	}
	
	void playGame(int sel2) {
	
		int idx=fineUserIdxByUserNumFromGameList(sel2);
		if(idx==-1) {
			Game g=new Game();
			g.setUserNum(sel2);
			GameDAO.gameList.add(g);
			idx=fineUserIdxByUserNumFromGameList(sel2);
		}
		double record=0;
		long beforeTime = System.currentTimeMillis();
		for(int z=0;z<1000000;){
			while(true) {
				int[] info=findCharAndBall( el.getInfo());
				showMap();
				if(win==1) {System.out.println("★CLEAR★");break;}
				int c=moveObjects(info,sel2);
				if(c==7) return;
			}
			break;
		}
		long afterTime=System.currentTimeMillis();
		double secDiffTime=(afterTime-beforeTime);
		record=secDiffTime/1000;
		System.out.println("[소요시간 : "+record+"초.]");
		
		double m=GameDAO.gameList.get(idx).getBestRecord();
		if(m!=0) {
			if(m>record) GameDAO.gameList.get(idx).setBestRecord(record);
			else GameDAO.gameList.get(idx).setBestRecord(m);
		}
		else GameDAO.gameList.get(idx).setBestRecord(record);
		v.view2();
	}
	
	int moveObjects(int[] info, int userNum) {
		int dir=-1;
		System.out.println("\n[1]→ [2]← [3]↑ [4]↓\n[5]저장 [6]불러오기 [7]뒤로가기");
		while(dir==-1) {
			dir=Input.getValue("이동할 방향을 입력하다. ", 1, 7);
		}
		v.view2();
		if(dir==5) {
			GameDAO gd=new GameDAO();
			gd.saveGame(el.getMap(), userNum);
		}
		if(dir==6) {
			int idx=fineUserIdxByUserNumFromGameList(userNum);
			int[][] temp=GameDAO.gameList.get(idx).getSavedGame();
			if(temp==null) {System.out.println("저장된 데이터가 존재하지 않는다.");return dir;}
			else {
				clearMap(idx);
				el.setMap(GameDAO.gameList.get(idx).getSavedGame());
				return dir;
			}
		}
	
		
		int ncy=info[0];
		int ncx=info[1];
		int nby=info[2];
		int nbx=info[3];
		
		if(dir==1 || dir==2) ncx=findNextCell(dir,ncy,ncx);
		if(dir==3 || dir==4) ncy=findNextCell(dir,ncy,ncx);
		if(ncx==-1 || ncy==-1 || el.getMap()[ncy][ncx]==9) return dir;
		
		if(el.getMap()[ncy][ncx]==3) {
			if(dir==1 || dir==2) nbx=findNextCell(dir,nby,nbx);
			if(dir==3 || dir==4) nby=findNextCell(dir,nby,nbx);
			if(nbx==-1 || nby==-1 || el.getMap()[nby][nbx]==9) return dir;
			if(el.getMap()[nby][nbx]==7) win=1;
			
			el.getMap()[ info[2] ][ info[3] ]=0;
			info[2]=nby;
			info[3]=nbx;
			el.getMap()[ info[2] ][ info[3] ]=3;
		}
		
		el.getMap()[ info[0] ][ info[1] ]=0;
		info[0]=ncy;
		info[1]=ncx;
		el.getMap()[ info[0] ][ info[1] ]=2;
		
		return dir;
	}
	
	int findNextCell(int dir, int y, int x) {
		int changedValue=0;
		
		if(dir==1) changedValue=x+1;
		if(dir==2) changedValue=x-1;
		if(dir==3) changedValue=y-1;
		if(dir==4) changedValue=y+1;
		
		if(changedValue>el.getMap().length-1 || changedValue<0) return -1;
		
		return changedValue;
	}
	
	int fineUserIdxByUserNumFromUserList(int userNum) {
		int idx=-1;
		for(int i=0;i<UserDAO.userList.size();i++) {
			if(UserDAO.userList.get(i).getUserNum()==userNum) idx=i;
		}
		return idx;
	}
	
	int fineUserIdxByUserNumFromGameList(int userNum) {
		int idx=-1;
		for(int i=0;i<GameDAO.gameList.size();i++) {
			if(GameDAO.gameList.get(i).getUserNum()==userNum) idx=i;
		}
		return idx;
	}
	
}
