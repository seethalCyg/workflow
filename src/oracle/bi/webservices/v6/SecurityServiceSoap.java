/**
 * SecurityServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public interface SecurityServiceSoap extends java.rmi.Remote {
    public oracle.bi.webservices.v6.Privilege[] getGlobalPrivileges(java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.ACL getGlobalPrivilegeACL(java.lang.String privilegeName, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void updateGlobalPrivilegeACL(java.lang.String privilegeName, oracle.bi.webservices.v6.ACL acl, oracle.bi.webservices.v6.UpdateACLParams updateACLParams, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void forgetAccounts(oracle.bi.webservices.v6.Account[] account, int cleanuplevel, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void renameAccounts(oracle.bi.webservices.v6.Account[] from, oracle.bi.webservices.v6.Account[] to, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void joinGroups(oracle.bi.webservices.v6.Account[] group, oracle.bi.webservices.v6.Account[] member, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void leaveGroups(oracle.bi.webservices.v6.Account[] group, oracle.bi.webservices.v6.Account[] member, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.Account[] getGroups(oracle.bi.webservices.v6.Account[] member, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.Account[] getMembers(oracle.bi.webservices.v6.Account[] group, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException;
    public boolean isMember(oracle.bi.webservices.v6.Account[] group, oracle.bi.webservices.v6.Account[] member, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException;
    public int[] getPermissions(oracle.bi.webservices.v6.ACL[] acls, oracle.bi.webservices.v6.Account account, java.lang.String sessionID) throws java.rmi.RemoteException;
    public boolean[] getPrivilegesStatus(java.lang.String[] privileges, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.Account[] getAccounts(oracle.bi.webservices.v6.Account[] account, java.lang.String sessionID) throws java.rmi.RemoteException;
}
