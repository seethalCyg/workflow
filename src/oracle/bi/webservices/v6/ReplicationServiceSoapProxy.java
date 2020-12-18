package oracle.bi.webservices.v6;

public class ReplicationServiceSoapProxy implements oracle.bi.webservices.v6.ReplicationServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.ReplicationServiceSoap replicationServiceSoap = null;
  
  public ReplicationServiceSoapProxy() {
    _initReplicationServiceSoapProxy();
  }
  
  public ReplicationServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initReplicationServiceSoapProxy();
  }
  
  private void _initReplicationServiceSoapProxy() {
    try {
      replicationServiceSoap = (new oracle.bi.webservices.v6.ReplicationServiceLocator()).getReplicationServiceSoap();
      if (replicationServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)replicationServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)replicationServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (replicationServiceSoap != null)
      ((javax.xml.rpc.Stub)replicationServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.ReplicationServiceSoap getReplicationServiceSoap() {
    if (replicationServiceSoap == null)
      _initReplicationServiceSoapProxy();
    return replicationServiceSoap;
  }
  
  public void export(java.lang.String filename, oracle.bi.webservices.v6.CatalogItemsFilter filter, oracle.bi.webservices.v6.ExportFlags flag, boolean exportSecurity, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (replicationServiceSoap == null)
      _initReplicationServiceSoapProxy();
    replicationServiceSoap.export(filename, filter, flag, exportSecurity, sessionID);
  }
  
  public oracle.bi.webservices.v6.ImportError[] _import(java.lang.String filename, oracle.bi.webservices.v6.ImportFlags flag, java.util.Calendar lastPurgedLog, boolean updateReplicationLog, boolean returnErrors, oracle.bi.webservices.v6.CatalogItemsFilter filter, oracle.bi.webservices.v6.PathMapEntry[] pathMap, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (replicationServiceSoap == null)
      _initReplicationServiceSoapProxy();
    return replicationServiceSoap._import(filename, flag, lastPurgedLog, updateReplicationLog, returnErrors, filter, pathMap, sessionID);
  }
  
  public void markForReplication(java.lang.String item, boolean replicate, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (replicationServiceSoap == null)
      _initReplicationServiceSoapProxy();
    replicationServiceSoap.markForReplication(item, replicate, sessionID);
  }
  
  public void purgeLog(java.lang.String[] items, java.util.Calendar timestamp, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (replicationServiceSoap == null)
      _initReplicationServiceSoapProxy();
    replicationServiceSoap.purgeLog(items, timestamp, sessionID);
  }
  
  
}