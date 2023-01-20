package _03소코반2_김성윤2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameDAO {

	static ArrayList<Game> gameList=new ArrayList<Game>();
	Skb el=new Skb();
	
	void saveGame(int[][] map, int userNum) {
		int[][] temp=map;
		int idx=fineUserIdxByUserNumFromGameList(userNum);
		gameList.get(idx).setSavedGame(temp);
		for(int i=0;i<gameList.size();i++) {
			System.out.println(gameList.get(i).getUserNum()+" "+gameList.get(i).getBestRecord()+" "+gameList.get(i).getSavedGame());
		}
	}
	
	void saveGameInfo() {
		setRank();
		String fileName="src/_03소코반2_김성윤2/sokobansavefile.txt";
		FileWriter fw=null;
		try {
			 fw=new FileWriter(fileName);
			 for(int i=0;i<gameList.size();i++) {
				 String text="";
				 String aInfo="";
				 if(gameList.get(i).getSavedGame()!=null) {
					 for(int[] j:gameList.get(i).getSavedGame()) {
						 for(int k:j) aInfo+=String.valueOf(k);
					 }
				 }
				 text+=String.valueOf(gameList.get(i).getUserNum())+"/"+aInfo+"/"+gameList.get(i).getBestRecord()+"/"+gameList.get(i).getRank()+",";
				 fw.write(text);
			 }
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				fw.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void loadGameInfo() {
		String fileName="src/_03소코반2_김성윤2/sokobansavefile.txt";
		FileReader fr=null;
		String data="";
		try {
			fr=new FileReader(fileName);
			int read=0;
			while(read!=-1) {
				read=fr.read();
				data+=(char)read;
			}
			
		} catch (FileNotFoundException e) { 
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fr!=null) {
				try {
					fr.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String[] text=data.split(",");
		int size=text.length-1;
		
		for(int i=0;i<size;i++) {
			String info[]=text[i].split("/");
			Game g=new Game();
			g.setUserNum(Integer.parseInt(info[0]));
			int[][] t=new int[7][7];
			int a=info[1].length()/7;
			int cnt=0;
			if(a==0) {t=null;}
			else {
				for(int j=0;j<a;j++) {
					for(int k=0;k<a;k++) {
						t[j][k]=Character.getNumericValue(info[1].charAt(cnt));
						cnt++;
					}
				}
			}
			g.setSavedGame(t);
			double d = Double.parseDouble(info[2]);  
			g.setBestRecord(d);
			g.setRank(Integer.parseInt(info[3]));
			gameList.add(g);
		}
	}
	
	int fineUserIdxByUserNumFromGameList(int userNum) {
		int idx=-1;
		for(int i=0;i<GameDAO.gameList.size();i++) {
			if(GameDAO.gameList.get(i).getUserNum()==userNum) idx=i;
		}
		return idx;
	}
	
	void setRank() {
		int zeroCutter=0;
		for(int i=0;i<GameDAO.gameList.size();i++) {
			if(gameList.get(i).getBestRecord()==0) zeroCutter++;
		}
		
		int cnt=1;
		double[] rank=new double[GameDAO.gameList.size()-zeroCutter];
		int a=0;
		for(int i=0;i<gameList.size();i++) {
			if(gameList.get(i).getBestRecord()!=0) {rank[a]=gameList.get(i).getBestRecord();a++;}
		}
		Arrays.sort(rank);
		for(int i=0;i<rank.length;i++) {
			for(int j=0;j<gameList.size();j++) {
				if(gameList.get(j).getBestRecord()==rank[i]) {gameList.get(j).setRank(cnt);cnt++;}
			}
		}
		
//		for(int i=0;i<gameList.size();i++) {
//			System.out.println(gameList.get(i).getRank());
//		}
	}

}
