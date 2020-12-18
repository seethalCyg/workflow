
package beans;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class UltraDataBean {
	
String emailID;
String username;
boolean superprvilage ;
String customerKey;
String accUniqueID;

String createUseremailID;
String createusername;
boolean createUsersuperprvilage ;
String createUsercustomerKey;
String createUseraccUniqueID;

public UltraDataBean(Boolean superprvilage, String customerKey, String accUniqueID,String emailID, String username) {
	
	this.emailID = emailID;
	this.username = username;
	this.superprvilage = superprvilage;
	this.customerKey = customerKey;
	this.accUniqueID = accUniqueID;
	
	
}




public UltraDataBean( String createUseraccUniqueID,String createUseremailID, String createUserusername) {
	
	this.createUseremailID = createUseremailID;
	this.createusername = createUserusername;
	this.createUseraccUniqueID = createUseraccUniqueID;
	
	
}







































































public String getCustomerKey() {
	return customerKey;
}


public void setCustomerKey(String customerKey) {
	this.customerKey = customerKey;
}
public String getAccUniqueID() {
	return accUniqueID;
}

public void setAccUniqueID(String accUniqueID) {
	this.accUniqueID = accUniqueID;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getEmailID() {
	return emailID;
}
public void setEmailID(String emailID) {
	this.emailID = emailID;
}

public boolean isSuperprvilage() {
	return superprvilage;
}
public void setSuperprvilage(boolean superprvilage) {
	this.superprvilage = superprvilage;
}

public String getCreateUseremailID() {
	return createUseremailID;
}




public void setCreateUseremailID(String createUseremailID) {
	this.createUseremailID = createUseremailID;
}




public String getCreateusername() {
	return createusername;
}




public void setCreateusername(String createusername) {
	this.createusername = createusername;
}




public boolean isCreateUsersuperprvilage() {
	return createUsersuperprvilage;
}




public void setCreateUsersuperprvilage(boolean createUsersuperprvilage) {
	this.createUsersuperprvilage = createUsersuperprvilage;
}




public String getCreateUsercustomerKey() {
	return createUsercustomerKey;
}




public void setCreateUsercustomerKey(String createUsercustomerKey) {
	this.createUsercustomerKey = createUsercustomerKey;
}




public String getCreateUseraccUniqueID() {
	return createUseraccUniqueID;
}




public void setCreateUseraccUniqueID(String createUseraccUniqueID) {
	this.createUseraccUniqueID = createUseraccUniqueID;
}


}