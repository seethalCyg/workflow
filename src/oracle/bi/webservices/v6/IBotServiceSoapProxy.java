package oracle.bi.webservices.v6;

public class IBotServiceSoapProxy implements oracle.bi.webservices.v6.IBotServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.IBotServiceSoap iBotServiceSoap = null;
  
  public IBotServiceSoapProxy() {
    _initIBotServiceSoapProxy();
  }
  
  public IBotServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIBotServiceSoapProxy();
  }
  
  private void _initIBotServiceSoapProxy() {
    try {
      iBotServiceSoap = (new oracle.bi.webservices.v6.IBotServiceLocator()).getIBotServiceSoap();
      if (iBotServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iBotServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iBotServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iBotServiceSoap != null)
      ((javax.xml.rpc.Stub)iBotServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.IBotServiceSoap getIBotServiceSoap() {
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    return iBotServiceSoap;
  }
  
  public void executeIBotNow(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    iBotServiceSoap.executeIBotNow(path, sessionID);
  }
  
  public int writeIBot(oracle.bi.webservices.v6.CatalogObject obj, java.lang.String path, boolean resolveLinks, boolean allowOverwrite, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    return iBotServiceSoap.writeIBot(obj, path, resolveLinks, allowOverwrite, sessionID);
  }
  
  public void subscribe(java.lang.String path, java.lang.String customizationXml, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    iBotServiceSoap.subscribe(path, customizationXml, sessionID);
  }
  
  public void unsubscribe(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    iBotServiceSoap.unsubscribe(path, sessionID);
  }
  
  public void moveIBot(java.lang.String fromPath, java.lang.String toPath, boolean resolveLinks, boolean allowOverwrite, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    iBotServiceSoap.moveIBot(fromPath, toPath, resolveLinks, allowOverwrite, sessionID);
  }
  
  public void deleteIBot(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    iBotServiceSoap.deleteIBot(path, sessionID);
  }
  
  public java.lang.String sendMessage(java.lang.String[] recipient, java.lang.String[] group, java.lang.String subject, java.lang.String body, java.lang.String priority, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (iBotServiceSoap == null)
      _initIBotServiceSoapProxy();
    return iBotServiceSoap.sendMessage(recipient, group, subject, body, priority, sessionID);
  }
  
  
}