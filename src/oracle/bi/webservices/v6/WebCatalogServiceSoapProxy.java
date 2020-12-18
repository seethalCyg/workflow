package oracle.bi.webservices.v6;

public class WebCatalogServiceSoapProxy implements oracle.bi.webservices.v6.WebCatalogServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.WebCatalogServiceSoap webCatalogServiceSoap = null;
  
  public WebCatalogServiceSoapProxy() {
    _initWebCatalogServiceSoapProxy();
  }
  
  public WebCatalogServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebCatalogServiceSoapProxy();
  }
  
  private void _initWebCatalogServiceSoapProxy() {
    try {
      webCatalogServiceSoap = (new oracle.bi.webservices.v6.WebCatalogServiceLocator()).getWebCatalogServiceSoap();
      if (webCatalogServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webCatalogServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webCatalogServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webCatalogServiceSoap != null)
      ((javax.xml.rpc.Stub)webCatalogServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.WebCatalogServiceSoap getWebCatalogServiceSoap() {
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    return webCatalogServiceSoap;
  }
  
  public void deleteItem(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.deleteItem(path, sessionID);
  }
  
  public void removeFolder(java.lang.String path, boolean recursive, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.removeFolder(path, recursive, sessionID);
  }
  
  public void createFolder(java.lang.String path, boolean createIfNotExists, boolean createIntermediateDirs, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.createFolder(path, createIfNotExists, createIntermediateDirs, sessionID);
  }
  
  public void createLink(java.lang.String path, java.lang.String pathTarget, boolean overwriteIfExists, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.createLink(path, pathTarget, overwriteIfExists, sessionID);
  }
  
  public void moveItem(java.lang.String pathSrc, java.lang.String pathDest, int flagACL, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.moveItem(pathSrc, pathDest, flagACL, sessionID);
  }
  
  public void copyItem(java.lang.String pathSrc, java.lang.String pathDest, int flagACL, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.copyItem(pathSrc, pathDest, flagACL, sessionID);
  }
  
  public byte[] copyItem2(java.lang.String[] path, boolean recursive, boolean permissions, boolean timestamps, boolean useMtom, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    return webCatalogServiceSoap.copyItem2(path, recursive, permissions, timestamps, useMtom, sessionID);
  }
  
  public void pasteItem2(byte[] archive, java.lang.String replacePath, int flagACL, int flagOverwrite, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.pasteItem2(archive, replacePath, flagACL, flagOverwrite, sessionID);
  }
  
  public oracle.bi.webservices.v6.ItemInfo[] getSubItems(java.lang.String path, java.lang.String mask, boolean resolveLinks, oracle.bi.webservices.v6.GetSubItemsParams options, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    return webCatalogServiceSoap.getSubItems(path, mask, resolveLinks, options, sessionID);
  }
  
  public oracle.bi.webservices.v6.ItemInfo getItemInfo(java.lang.String path, boolean resolveLinks, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    return webCatalogServiceSoap.getItemInfo(path, resolveLinks, sessionID);
  }
  
  public void setItemProperty(java.lang.String[] path, java.lang.String[] name, java.lang.String[] value, boolean recursive, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.setItemProperty(path, name, value, recursive, sessionID);
  }
  
  public void maintenanceMode(boolean flag, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.maintenanceMode(flag, sessionID);
  }
  
  public oracle.bi.webservices.v6.CatalogObject[] readObjects(java.lang.String[] paths, boolean resolveLinks, oracle.bi.webservices.v6.ErrorDetailsLevel errorMode, oracle.bi.webservices.v6.ReadObjectsReturnOptions returnOptions, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    return webCatalogServiceSoap.readObjects(paths, resolveLinks, errorMode, returnOptions, sessionID);
  }
  
  public oracle.bi.webservices.v6.ErrorInfo[] writeObjects(oracle.bi.webservices.v6.CatalogObject[] catalogObjects, boolean allowOverwrite, boolean createIntermediateDirs, oracle.bi.webservices.v6.ErrorDetailsLevel errorMode, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    return webCatalogServiceSoap.writeObjects(catalogObjects, allowOverwrite, createIntermediateDirs, errorMode, sessionID);
  }
  
  public void updateCatalogItemACL(java.lang.String[] path, oracle.bi.webservices.v6.ACL acl, oracle.bi.webservices.v6.UpdateCatalogItemACLParams options, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.updateCatalogItemACL(path, acl, options, sessionID);
  }
  
  public void setOwnership(java.lang.String[] path, oracle.bi.webservices.v6.Account owner, boolean recursive, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.setOwnership(path, owner, recursive, sessionID);
  }
  
  public void setItemAttributes(java.lang.String[] path, int value, int valueOff, boolean recursive, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (webCatalogServiceSoap == null)
      _initWebCatalogServiceSoapProxy();
    webCatalogServiceSoap.setItemAttributes(path, value, valueOff, recursive, sessionID);
  }
  
  
}