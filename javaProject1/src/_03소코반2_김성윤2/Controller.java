package _03소코반2_김성윤2;

public class Controller {

	SkbDAO sd=new SkbDAO();
	UserDAO ud=new UserDAO();
	GameDAO gd=new GameDAO();
	View v=new View();
	
	void init() {
		ud.loadUserInfo();
		gd.loadGameInfo();
	}
	
	void run() {
		
		int sel1=0;
		int sel2=0;
		v.view1();
		while(true) {
			if(sel2==0) {
				sel1=ud.firstMenu();
				if(sel1==-2) {
					ud.saveUserInfo();
					gd.saveGameInfo();
					System.out.println("[프로그램 종료.]");
					return;
				}
			}
			sel2=ud.secondMenu(sel1);
			if(sel2==0) continue;
			else if(sel2==-2) {
				ud.saveUserInfo();
				gd.saveGameInfo();
				System.out.println("[프로그램 종료.]");
				return;
			}
			else {
				System.out.println("tyest");
				v.view2();
				sd.setMap();
				sd.playGame(sel2);
				sd.clearMap(sel2);
			}
		}
		
	}
	
}
