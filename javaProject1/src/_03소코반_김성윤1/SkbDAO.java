package _03소코반_김성윤1;

import java.util.Random;
import java.util.Scanner;

public class SkbDAO {
	Random r=new Random();
	Scanner s=new Scanner(System.in);

	Skb el=new Skb();
	int[][] map=el.getMap();
	int[] info=el.getObjInfo();
	int win=el.getWin();
	
	void setObjects( int t, int obj) {
		
		int wcnt=0;
		int inputWall=0;
		int a=0;
		int b=0;
		if(t==0) {
			System.out.print("설치할 벽의 갯수를 입력하다. : ");
			inputWall=s.nextInt();
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
				wcnt++;
				if(wcnt==inputWall) break;
			}
			else break;
		}
	}
	
	void setMap() {
		int t=0;
		//벽 설치
		setObjects(t, 9);
		t=1;
		
		//골대 설치
		setObjects(t, 7);
		
		//공 설치
		setObjects(t, 3);
		
		//플레이어 배치
		setObjects(t, 2);
	}
	
	void play() {
		while(true) {
			int[] cn=findBallAndCharacter(info);
			showMap();
			if(win==1) {System.out.println("[★CLEAR★]");break;}
			moveObjects(cn);
		}
	}
	
	double game() {
		long beforeTime = System.currentTimeMillis();
		for(int i =0; i < 1000000;){
			play();
			break;
		}
		long afterTime = System.currentTimeMillis();
		double secDiffTime = (afterTime - beforeTime) / 1000.0;

		System.out.println("[클리어타임 : " + secDiffTime+"초.]");
		return secDiffTime;
	}
	
	void showMap() {
		for(int[] i:map) {
			for(int j:i) {
				if(j==0) System.out.print("[ ]");
				if(j==2) System.out.print("[옷]");
				if(j==3) System.out.print("[o]");
				if(j==7) System.out.print("[=]");
				if(j==9) System.out.print("[■]");
			}System.out.println();
		}
	}
	
	int[] findBallAndCharacter( int[] info) {
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if(map[i][j]==2) {
					info[0]=i; info[1]=j;
				}
				if(map[i][j]==3) {
					info[2]=i; info[3]=j;
				}
			}
		}
		return info;
	}
	

	int[] moveObjects(int[] info) {
		
		System.out.print("([1] → [2] ← [3] ↑ [4] ↓)\n움직일 방향을 입력하다. : ");
		int dir=s.nextInt();
		
		int ncy=info[0];
		int ncx=info[1];
		int nby=info[2];
		int nbx=info[3];
		
		if(dir==1 || dir==2) ncx=findNextCell(dir,ncy,ncx);
		if(dir==3 || dir==4) ncy=findNextCell(dir,ncy,ncx);
		if(ncy==-1 || ncx==-1 || map[ncy][ncx]==9) return info;
		
		if(map[ncy][ncx]==3) {
			if(dir==1 || dir==2) nbx=findNextCell(dir,nby,nbx);
			if(dir==3 || dir==4) nby=findNextCell(dir,nby,nbx);
			if(nby==-1 || nbx==-1 || map[nby][nbx]==9) return info;
			if(map[nby][nbx]==7) win=1;
			
			map[info[2]][info[3]]=0;
			info[2]=nby;
			info[3]=nbx;
			map[info[2]][info[3]]=3;
		}
		
		map[info[0]][info[1]]=0;
		info[0]=ncy;
		info[1]=ncx;
		map[info[0]][info[1]]=2;
		
		return info;
	}
	
	int findNextCell(int dir, int y, int x) {
		int changedValue=0;
		if(dir==1) changedValue=x+1;
		if(dir==2) changedValue=x-1;
		if(dir==3) changedValue=y-1;
		if(dir==4) changedValue=y+1;
		
		if(7<=changedValue || changedValue<0) return -1;
		return changedValue;
	}
	
}
