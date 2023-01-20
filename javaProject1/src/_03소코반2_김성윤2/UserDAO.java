package _03소코반2_김성윤2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserDAO {

	static ArrayList<User> userList=new ArrayList<User>();
	int userCnt=1;
	private static int num = 1001;
	public static int getNum() {
		return num;
	}
	View v=new View();
	
	int firstMenu() {
		while(true) {
			System.out.println("[1]로그인\n[2]회원가입\n[3]랭킹보기\n[4]종료");
			int sel=-1;
			while(sel==-1) {sel=Input.getValue("메뉴를 선택하다. ", 0, 4);}
			v.view2();
			if(sel==1) {
				if(userList.size()==0) {System.err.println("가입한 회원이 존재하지 않는다."); continue;}
				int userNum=login();
				if(userNum==-1) continue;
				else return userNum;
			}
			else if(sel==2) {
				join();
			}
			else if(sel==3) {
				if(userList.size()==0) {System.err.println("가입한 회원이 존재하지 않는다."); continue;}
				showRank();
			}
			else if(sel==0) showWholeInfo();
			else return -2;
		}
	}
	
	int secondMenu(int sel1) {
		while(true) {
			System.out.println("[1]게임시작\n[2]내 정보\n[3]랭킹보기\n[4]로그아웃\n[5]종료");
			int sel=-1;
			while(sel==-1) {sel=Input.getValue("메뉴를 선택하다. ", 1, 5);}
			v.view2();
			if(sel==1) return sel1;
			else if(sel==2) {
				int curUser=sel1;
				showUserInfo(curUser);
				continue;
			}
			else if(sel==3) {v.view2();showRank();continue;}
			else if(sel==4) {System.out.println("[로그아웃 하다.]");return 0;}
			else return -2;
		}
	}
	
	int login() {
		int num=0;
		String id=Input.getValue("아이디를 입력하다.");
		User u=checkId(id);
		if(u!=null) {
			while(true) {
				String pwd=Input.getValue("패스워드를 입력하다.");
				if(u.getUserPwd().equals(pwd)) {
					num=u.getUserNum();
					System.out.println("["+id+"] 로그인 성공.");
					v.view2();
					break;
				}
				else {System.out.println("패스워드 입력 오류.");continue;}
			}
		} else {
			System.out.println("존재하지 않는 아이디.");
			num=-1;
		}
		return num;
	}
	
	void join() {
		String id=Input.getValue("가입할 아이디를 입력하다.");
		User u=checkId(id);
		if(u==null) {
			String pwd=Input.getValue("패스워드를 입력하다.");
			u=new User(userCnt,id,pwd);
			userList.add(u);
			userCnt++;
			System.out.print("["+id+"] 회원가입 완료.\n");
			v.view2();
		}
		else {System.out.println("이미 존재하는 아이디.");v.view2();}
	}
	
	User checkId(String id) {
		for(User u:userList) {
			if(u.getUserId().equals(id)) return u;
		}
		return null;
	}
	
	void showRank() {
		for(int i=0;i<userList.size();i++) {
			System.out.print(userList.get(i).getUserNum()+" "+userList.get(i).getUserId()+" ");
			int idx=findIdxByUserNumFromGameList(userList.get(i).getUserNum());
			if(idx==-1) System.out.println("");
			try {
				System.out.print(GameDAO.gameList.get(idx).getBestRecord()+"초  ");
				if(GameDAO.gameList.get(idx).getRank()!=0) System.out.println(GameDAO.gameList.get(idx).getRank()+"위");
				else System.out.println();
			} catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
			}
		}
		v.view2();
	}
	
	void showUserInfo(int curUser) {
		SkbDAO sd=new SkbDAO();
		int idx=sd.fineUserIdxByUserNumFromGameList(curUser);
		v.view2();
		v.view3();
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getUserNum()==curUser) {
				System.out.print("[User "+userList.get(i).getUserNum()+"] "+userList.get(i).getUserId()+"  ");
				if(idx!=-1) {
					System.out.print(GameDAO.gameList.get(idx).getBestRecord()+"초  ");
					if(GameDAO.gameList.get(idx).getRank()!=0) System.out.println(GameDAO.gameList.get(idx).getRank()+"위");
					else System.out.println();
				}
				else System.out.println();
			}
		}
		v.view2();
	}
	
	void showWholeInfo() {
		for(int i=0;i<userList.size();i++) {
			System.out.println(userList.get(i).getUserNum()+" "+userList.get(i).getUserId());
		}
		v.view2();
	}
	
	int findIdxByUserNumFromGameList(int userNum) {
		int idx=-1;
		for(int i=0;i<GameDAO.gameList.size();i++) {
			if(GameDAO.gameList.get(i).getUserNum()==userNum) idx=i;
		}
		return idx;
	}
	
	void saveUserInfo() {
		String fileName="src/_03소코반2_김성윤2/userListfile.txt";
		FileWriter fw=null;
		try {
			 fw=new FileWriter(fileName);
			 for(int i=0;i<userList.size();i++) {
				 String text="";
				 text+=String.valueOf(userList.get(i).getUserNum())+"/"+userList.get(i).getUserId()+"/"+userList.get(i).getUserPwd()+",";
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
	
	void loadUserInfo() {
		String fileName="src/_03소코반2_김성윤2/userListfile.txt";
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
//			System.out.println("파일이 존재하지 않습니다");
			//e.printStackTrace();
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
			User u=new User(Integer.parseInt(info[0]),info[1],info[2]);
			userList.add(u);
			userCnt++;
		}
	}
}
