package beans;

/**
 * @author Java Developer
 *
 */
public class DemoModal {
	private String name = "";
	private String username = "";
	private String password = "";
	private String empid = "";
	public DemoModal(String name, String username, String password, String empid) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.empid = empid;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmpid() {
		return empid;
	}


	public void setEmpid(String empid) {
		this.empid = empid;
	}
	
	
	
	
	
	
}
