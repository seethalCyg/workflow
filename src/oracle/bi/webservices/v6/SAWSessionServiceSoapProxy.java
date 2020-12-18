package oracle.bi.webservices.v6;

public class SAWSessionServiceSoapProxy implements oracle.bi.webservices.v6.SAWSessionServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.SAWSessionServiceSoap sAWSessionServiceSoap = null;
  
  public SAWSessionServiceSoapProxy() {
    _initSAWSessionServiceSoapProxy();
  }
  
  public SAWSessionServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSAWSessionServiceSoapProxy();
  }
  
  private void _initSAWSessionServiceSoapProxy() {
    try {
      sAWSessionServiceSoap = (new oracle.bi.webservices.v6.SAWSessionServiceLocator()).getSAWSessionServiceSoap();
      if (sAWSessionServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sAWSessionServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sAWSessionServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sAWSessionServiceSoap != null)
      ((javax.xml.rpc.Stub)sAWSessionServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.SAWSessionServiceSoap getSAWSessionServiceSoap() {
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap;
  }
  
  public java.lang.String logon(java.lang.String name, java.lang.String password) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.logon(name, password);
  }
  
  public oracle.bi.webservices.v6.AuthResult logonex(java.lang.String name, java.lang.String password, oracle.bi.webservices.v6.SAWSessionParameters sessionparams) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.logonex(name, password, sessionparams);
  }
  
  public void logoff(java.lang.String sessionID) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    sAWSessionServiceSoap.logoff(sessionID);
  }
  
  public void keepAlive(java.lang.String[] sessionID) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    sAWSessionServiceSoap.keepAlive(sessionID);
  }
  
  public java.lang.String getCurUser(java.lang.String sessionID) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.getCurUser(sessionID);
  }
  
  public oracle.bi.webservices.v6.SessionEnvironment getSessionEnvironment(java.lang.String sessionID) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.getSessionEnvironment(sessionID);
  }
  
  public java.lang.String[] getSessionVariables(oracle.bi.webservices.v6.GetSessionVariables parameters) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.getSessionVariables(parameters);
  }
  
  public java.lang.String impersonate(java.lang.String name, java.lang.String password, java.lang.String impersonateID) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.impersonate(name, password, impersonateID);
  }
  
  public oracle.bi.webservices.v6.AuthResult impersonateex(java.lang.String name, java.lang.String password, java.lang.String impersonateID, oracle.bi.webservices.v6.SAWSessionParameters sessionparams) throws java.rmi.RemoteException{
    if (sAWSessionServiceSoap == null)
      _initSAWSessionServiceSoapProxy();
    return sAWSessionServiceSoap.impersonateex(name, password, impersonateID, sessionparams);
  }
  
  
}