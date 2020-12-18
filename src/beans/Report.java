package beans;

import javax.xml.bind.annotation.XmlElement;

public class Report implements java.io.Serializable {	

	private String LEVEL_2;
	private String ID;
	private String RootLevel_Name;
	
	private String hierType;
	private String levelNode;
	private String heriIDs;
	private String levelName;

	public String getHierType() {
		return hierType;
	}

	public void setHierType(String hierType) {
		this.hierType = hierType;
	}

	public String getLevelNode() {
		return levelNode;
	}

	public void setLevelNode(String levelNode) {
		this.levelNode = levelNode;
	}

	public String getHeriIDs() {
		return heriIDs;
	}

	public void setHeriIDs(String heriIDs) {
		this.heriIDs = heriIDs;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	

	@XmlElement(name = "LEVEL_2")
	public String getLEVEL_2() {
		return LEVEL_2;
	}

	public void setLEVEL_2(String lEVEL_2) {
		this.LEVEL_2 = lEVEL_2;
	}

	@XmlElement(name = "ID")
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	@XmlElement(name = "RootLevel_Name")
	public String getRootLevel_Name() {
		return RootLevel_Name;
	}

	public void setRootLevel_Name(String rootLevel_Name) {
		this.RootLevel_Name = rootLevel_Name;
	}
	
	public static Report createReport() {
		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "." + new Exception().getStackTrace()[0].getMethodName());
		return new Report();
	}
}
