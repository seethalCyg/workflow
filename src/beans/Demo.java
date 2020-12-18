package beans;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="demo")
@SessionScoped
public class Demo {
	private String userName = "";
	private String password = "";
	private String statusMsg = "";
	
	
	private String signupUserName = "";
	private String signupPassword = "";
	private String signupName = "";
	
	private ArrayList<DemoModal> userAL = new ArrayList<>();
	
	private ArrayList<String> stageNameAL = new ArrayList<>();
	
	private Hashtable<String, String> stageNameHT = new Hashtable<>();
	
	private String currStageName = "";
	
	
	public Demo() {
		stageNameAL.add("Stage 1");
		stageNameAL.add("Stage 2");
		stageNameAL.add("Stage 3");
		stageNameAL.add("Stage 4");
		stageNameAL.add("Stage 5");
		
		
		stageNameHT.put("1", "Stage 1");
		stageNameHT.put("2", "Stage 2");
		stageNameHT.put("3", "Stage 3");
		stageNameHT.put("4", "Stage 4");
		stageNameHT.put("5", "Stage 5");
		
	}
	
	public void signInAction() {
		System.out.println("currStageName: "+currStageName);
		System.out.println("Username: "+userName);
		System.out.println("Password: "+password);
		if(userName.equals("rbid") && password.equals("demo")) {
			statusMsg= "Signed in successful";
		}else {
			statusMsg= "Signed in failed";
		}
	}
	
	public void saveUser(String signupName, String signupUsername, String signupPassword) {
		System.out.println(userAL.size()+"Signup Name: "+signupName);
		System.out.println("Signup UserName: "+signupUsername);
		System.out.println("Signup Password: "+signupPassword);
		double id = Math.random();
		System.out.println("Signup id: "+id);
		DemoModal modal = new DemoModal(signupName, signupUsername, signupPassword, String.valueOf(id));
		userAL.add(modal);
	}
	
	public void EditSignUpAction(String id) {
		System.out.println("id: "+id);
		for(DemoModal dm : userAL) {
			if(dm.getEmpid().equalsIgnoreCase(id)) {
				signupName = dm.getName();
				signupUserName = dm.getUsername();
				signupPassword = dm.getPassword();
			}
		}
	}
	
	public void EditSignUpAction(DemoModal dm) {
		System.out.println("id: "+dm.getEmpid());
				signupName = dm.getName();
				signupUserName = dm.getUsername();
				signupPassword = dm.getPassword();
	}
	
	public void signUpAction() {
		signupName = "";
		signupUserName = "";
		signupPassword = "";
		System.out.println("signupName: "+signupName);
		System.out.println("signupUsername: "+signupUserName);
		System.out.println("signupPassword: "+signupPassword);
		System.out.println(userAL.toString()+": Size: "+userAL.size());
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getSignupUserName() {
		return signupUserName;
	}

	public void setSignupUserName(String signupUserName) {
		this.signupUserName = signupUserName;
	}

	public String getSignupPassword() {
		return signupPassword;
	}

	public void setSignupPassword(String signupPassword) {
		this.signupPassword = signupPassword;
	}

	public String getSignupName() {
		return signupName;
	}

	public void setSignupName(String signupName) {
		this.signupName = signupName;
	}

	public ArrayList<DemoModal> getUserAL() {
		System.out.println("Getter: "+userAL);
		return userAL;
	}

	public void setUserAL(ArrayList<DemoModal> userAL) {
		System.out.println("Setter: "+userAL);
		this.userAL = userAL;
	}

	public ArrayList<String> getStageNameAL() {
		return stageNameAL;
	}

	public void setStageNameAL(ArrayList<String> stageNameAL) {
		this.stageNameAL = stageNameAL;
	}

	public String getCurrStageName() {
		return currStageName;
	}

	public void setCurrStageName(String currStageName) {
		this.currStageName = currStageName;
	}

	public Hashtable<String, String> getStageNameHT() {
		return stageNameHT;
	}

	public void setStageNameHT(Hashtable<String, String> stageNameHT) {
		this.stageNameHT = stageNameHT;
	}

	
	
}
