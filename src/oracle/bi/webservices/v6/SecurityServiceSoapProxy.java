package oracle.bi.webservices.v6;

public class SecurityServiceSoapProxy implements oracle.bi.webservices.v6.SecurityServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.SecurityServiceSoap securityServiceSoap = null;
  
  public SecurityServiceSoapProxy() {
    _initSecurityServiceSoapProxy();
  }
  
  public SecurityServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSecurityServiceSoapProxy();
  }
  
  private void _initSecurityServiceSoapProxy() {
    try {
      securityServiceSoap = (new oracle.bi.webservices.v6.SecurityServiceLocator()).getSecurityServiceSoap();
      if (securityServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)securityServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)securityServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (securityServiceSoap != null)
      ((javax.xml.rpc.Stub)securityServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.SecurityServiceSoap getSecurityServiceSoap() {
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap;
  }
  
  public oracle.bi.webservices.v6.Privilege[] getGlobalPrivileges(java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getGlobalPrivileges(sessionID);
  }
  
  public oracle.bi.webservices.v6.ACL getGlobalPrivilegeACL(java.lang.String privilegeName, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getGlobalPrivilegeACL(privilegeName, sessionID);
  }
  
  public void updateGlobalPrivilegeACL(java.lang.String privilegeName, oracle.bi.webservices.v6.ACL acl, oracle.bi.webservices.v6.UpdateACLParams updateACLParams, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    securityServiceSoap.updateGlobalPrivilegeACL(privilegeName, acl, updateACLParams, sessionID);
  }
  
  public void forgetAccounts(oracle.bi.webservices.v6.Account[] account, int cleanuplevel, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    securityServiceSoap.forgetAccounts(account, cleanuplevel, sessionID);
  }
  
  public void renameAccounts(oracle.bi.webservices.v6.Account[] from, oracle.bi.webservices.v6.Account[] to, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    securityServiceSoap.renameAccounts(from, to, sessionID);
  }
  
  public void joinGroups(oracle.bi.webservices.v6.Account[] group, oracle.bi.webservices.v6.Account[] member, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    securityServiceSoap.joinGroups(group, member, sessionID);
  }
  
  public void leaveGroups(oracle.bi.webservices.v6.Account[] group, oracle.bi.webservices.v6.Account[] member, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    securityServiceSoap.leaveGroups(group, member, sessionID);
  }
  
  public oracle.bi.webservices.v6.Account[] getGroups(oracle.bi.webservices.v6.Account[] member, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getGroups(member, expandGroups, sessionID);
  }
  
  public oracle.bi.webservices.v6.Account[] getMembers(oracle.bi.webservices.v6.Account[] group, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getMembers(group, expandGroups, sessionID);
  }
  
  public boolean isMember(oracle.bi.webservices.v6.Account[] group, oracle.bi.webservices.v6.Account[] member, java.lang.Boolean expandGroups, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.isMember(group, member, expandGroups, sessionID);
  }
  
  public int[] getPermissions(oracle.bi.webservices.v6.ACL[] acls, oracle.bi.webservices.v6.Account account, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getPermissions(acls, account, sessionID);
  }
  
  public boolean[] getPrivilegesStatus(java.lang.String[] privileges, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getPrivilegesStatus(privileges, sessionID);
  }
  
  public oracle.bi.webservices.v6.Account[] getAccounts(oracle.bi.webservices.v6.Account[] account, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (securityServiceSoap == null)
      _initSecurityServiceSoapProxy();
    return securityServiceSoap.getAccounts(account, sessionID);
  }
  
  
}