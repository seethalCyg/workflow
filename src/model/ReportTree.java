package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import com.google.common.collect.Iterators;

public class ReportTree extends NamedNode implements TreeNode {

	private List<ReportTree> children = new ArrayList<ReportTree>();

	private static int messagesCreated;
	
	private boolean riConsVisible;
	

	public boolean isRiConsVisible() {
		return riConsVisible;
	}

	public void setRiConsVisible(boolean riConsVisible) {
		this.riConsVisible = riConsVisible;
	}

	private Object id = "message" + messagesCreated++;

	
	public ReportTree() {
//		this.setType("fact");
	}
	
	private String levelName;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	private String ID;
	private String hierlevel;
	private String levelNode;

	public String getLevelNode() {
		return levelNode;
	}

	public void setLevelNode(String levelNode) {
		this.levelNode = levelNode;
	}

	public String getHierlevel() {
		return hierlevel;
	}

	public void setHierlevel(String hierlevel) {
		this.hierlevel = hierlevel;
	}

	public String getHierType() {
		return hierType;
	}

	public void setHierType(String hierType) {
		this.hierType = hierType;
	}

	private String hierType;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	private String RIFailureMsg = "";
	
	public String getRIFailureMsg() {
		return RIFailureMsg;
	}

	public void setRIFailureMsg(String rIFailureMsg) {
		RIFailureMsg = rIFailureMsg;
	}

	public ReportTree(String hierType, String levelNode, String ID, String levelValue,boolean riConsVisible,String RIFailureMsg, List<ReportTree> children) {

		System.out.println("Entering: " + new Exception().getStackTrace()[0].getClassName() + "."
												+ new Exception().getStackTrace()[0].getMethodName());

		this.hierType = hierType;
		this.levelNode = levelNode;
		this.ID = ID;
		this.levelName = levelValue;
		
		this.riConsVisible=riConsVisible;

		this.RIFailureMsg = RIFailureMsg;
		this.children = children != null ? new ArrayList<ReportTree>(children) : null;
		System.out.println("Exiting: " + new Exception().getStackTrace()[0].getClassName() + "."
												+ new Exception().getStackTrace()[0].getMethodName());
	}



	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public TreeNode getParent() { // ///////////////// ?? Review this
									// ///////////////////
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public boolean isLeaf() {
		return children.isEmpty();

	}

	@Override
	public Enumeration<ReportTree> children() {
		return Iterators.asEnumeration(children.iterator());
	}

	public List<ReportTree> getChildren() {
		return children;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final ReportTree that = (ReportTree) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

}
